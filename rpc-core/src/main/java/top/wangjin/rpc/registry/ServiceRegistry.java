package top.wangjin.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册中心通用接口
 * @author wangjin
 */
public interface ServiceRegistry {

    /**
     * 将服务的名称和地址注册进服务注册中心
     *
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

}
