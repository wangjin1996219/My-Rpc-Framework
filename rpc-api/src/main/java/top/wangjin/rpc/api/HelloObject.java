package top.wangjin.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//这个对象需要实现Serializable接口，因为它需要在调用过程中从客户端传递给服务端
public class HelloObject implements Serializable {

    private Integer id;
    private String message;

}