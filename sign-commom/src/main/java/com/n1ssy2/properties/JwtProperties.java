package com.n1ssy2.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: JwtProperties
 * Package: com.n1ssy2.properties
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:26
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "n1ssy2.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
