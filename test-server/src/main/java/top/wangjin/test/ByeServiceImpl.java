package top.wangjin.test;

import top.wangjin.rpc.annotation.Service;
import top.wangjin.rpc.api.ByeService;

/**
 * @author wangjin
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
