package top.wangjin.test;

import top.wangjin.rpc.annotation.ServiceScan;
import top.wangjin.rpc.serializer.CommonSerializer;
import top.wangjin.rpc.transport.RpcServer;
import top.wangjin.rpc.transport.socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 *
 * @author ziyang
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}

