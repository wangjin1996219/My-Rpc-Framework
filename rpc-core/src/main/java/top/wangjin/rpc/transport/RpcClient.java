package top.wangjin.rpc.transport;

import top.wangjin.rpc.entity.RpcRequest;
import top.wangjin.rpc.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 * @author wangjin
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);
}
