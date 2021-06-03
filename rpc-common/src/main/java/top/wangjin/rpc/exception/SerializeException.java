package top.wangjin.rpc.exception;

/**
 * 序列化异常
 *
 * @author wangjin
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}
