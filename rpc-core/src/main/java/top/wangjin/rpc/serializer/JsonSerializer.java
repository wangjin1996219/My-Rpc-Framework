package top.wangjin.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.wangjin.rpc.entity.RpcRequest;
import top.wangjin.rpc.enumeration.SerializerCode;

import java.io.IOException;

/**
 * 使用JSON格式的序列化器
 * @author wangjin
 */
public class JsonSerializer implements CommonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("序列化时有错误发生: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            //如果对RpcRequest类型反序列化，要调用handleRequest方法
            if(obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            logger.error("反序列化时有错误发生: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /*
        这里由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类型
        需要重新判断处理
     */
    private Object handleRequest(Object obj) throws IOException {
        //将要反序列化的对象obj传入
        RpcRequest rpcRequest = (RpcRequest) obj;
        //遍历参数的类型数组
        for(int i = 0; i < rpcRequest.getParamTypes().length; i ++) {
            //获取参数的类信息
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            //isAssignableFrom判断参数类型和反序列化后的参数类型是否匹配（不匹配说明反序列化有问题）
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())) {
                //把rpcRequest.getParameters()[i]转成json序列，并把结果输出成字节数组
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                //将不正确的参数，转换成 clazz 类的 Java 类型
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }

}
