package top.wangjin.test;

import top.wangjin.rpc.api.ByeService;
import top.wangjin.rpc.serializer.CommonSerializer;
import top.wangjin.rpc.serializer.ProtobufSerializer;
import top.wangjin.rpc.transport.RpcClient;
import top.wangjin.rpc.transport.RpcClientProxy;
import top.wangjin.rpc.api.HelloObject;
import top.wangjin.rpc.api.HelloService;
import top.wangjin.rpc.transport.netty.client.NettyClient;

/**
 * 测试用Netty消费者
 * @author wangjin
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }
}

