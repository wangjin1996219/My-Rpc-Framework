package top.wangjin.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wangjin.rpc.entity.RpcRequest;
import top.wangjin.rpc.entity.RpcResponse;
import top.wangjin.rpc.enumeration.SerializerCode;
import top.wangjin.rpc.exception.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements CommonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);
    /*
    通过ThreadLocal来保证Kryo的线程安全
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        //初始化Kryo对象
        Kryo kryo = new Kryo();
        //Kryo对象中注册RpcResponse类和RpcRequest类
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        //开启循环引用
        kryo.setReferences(true);
        //关闭注册行为
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    /**
     * 序列化对象
     * @param obj
     * @return
     */
    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             //创建output对象，序列化的目标数组为byteArrayOutputStream字节数组
             Output output = new Output(byteArrayOutputStream)){
            //得到线程对应的kryo对象
            Kryo kryo = kryoThreadLocal.get();
            //将对象序写入output对象，完成序列化
            kryo.writeObject(output, obj);
            //防止内存泄漏（因为ThreadLocal中的key是弱引用，value是强引用）
            kryoThreadLocal.remove();
            //调用output的toByte()方法，返回一个字符数组
            return output.toBytes();
        } catch (Exception e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             //创建input对象，反序列化的目标数组为byteArrayOutputStream字节数组
             Input input = new Input(byteArrayInputStream)) {
            //得到线程对应的kryo对象
            Kryo kryo = kryoThreadLocal.get();
            //将字节数组的数据转换成对应的实体类对象
            Object o = kryo.readObject(input, clazz);
            //防止内存泄漏
            kryoThreadLocal.remove();
            return o;
        } catch (Exception e) {
            logger.error("反序列化时有错误发生:", e);
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}

