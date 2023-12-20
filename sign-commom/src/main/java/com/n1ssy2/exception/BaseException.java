package com.n1ssy2.exception;

/**
 * ClassName: BaseException
 * Package: com.n1ssy2.exception
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 0:23
 * @Version: 1.0
 */
public class BaseException extends RuntimeException{
    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
