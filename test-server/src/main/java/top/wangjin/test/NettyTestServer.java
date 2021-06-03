package top.wangjin.test;

import top.wangjin.rpc.annotation.ServiceScan;
import top.wangjin.rpc.serializer.CommonSerializer;
import top.wangjin.rpc.transport.RpcServer;
import top.wangjin.rpc.transport.netty.server.NettyServer;

/**
 * 测试用Netty服务提供者（服务端）
 *
 * @author ziyang
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
