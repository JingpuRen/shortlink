package org.gopher.shortlink.project.common.convention.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
@Accessors(chain = true)
/**
 * 这里其实本质上就是一个类，叫做Result类，他有一些属性，比如说返回码/返回消息等
 * 不过这里和普通类不同的是我们这里定义了泛型，其中的一些方法可以返回各种类型的结果
 * 泛型T取决于返回数据的类型，如果没有返回数据，那么T就是void
 */
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5679018624309023727L;

    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "200";

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求ID
     */
    private String requestId;

    // 像这种is打头的，最后反序列化时会当成一个字段，也就是说这里相当于一个success的字段
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }
}
