package top.wangjin.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author wangjin
 */
public interface LoadBalancer {

    Instance select(List<Instance> instances);

}
