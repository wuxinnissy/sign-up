package com.n1ssy2.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * ClassName: RandomStr
 * Package: com.n1ssy2.utils
 * Description: 获取随机8位字符串
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 23:51
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomStr implements Serializable {
    public static String generateRandomStr(){
        SecureRandom secureRandom = new SecureRandom();
        String randomString = new BigInteger(32, secureRandom).toString(32);
        return randomString.substring(0, 8); // 获取前8位作为随机字符串
    }
}
