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
public class RandomStr implements Serializable {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String generateRandomStr(){
        SecureRandom random = new SecureRandom();
        Integer length = 8;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
