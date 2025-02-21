package org.gopher.shortlink.admin.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.gopher.shortlink.admin.common.serialize.PhoneDesensitizationSerializer;

/**
 * 当你从控制器方法返回一个实体类对象时，通常会使用 Spring MVC 等框架将对象转换为 JSON 或其他格式返回给客户端。
 * 这个过程中，框架会调用对象的 Getter 方法来获取字段的值。
 * 而@Data注解可以帮助我们自动填充Getter/Setter/toString这三个方法！！！
 */
@Data
public class UserRespDTO {
    private Long	id;
    private String	username;
    private String	realName;
    // 在默认情况下序列化的时候，都是序列化成JSON，但由于电话是比较重要的隐私
    // 因此我们在这里改用封装好的序列化器
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String	phone;
    private String	mail;
}
