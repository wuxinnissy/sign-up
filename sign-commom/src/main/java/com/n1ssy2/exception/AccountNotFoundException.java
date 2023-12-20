package com.n1ssy2.exception;

/**
 * ClassName: AccountNotFoundException
 * Package: com.n1ssy2.exception
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 0:26
 * @Version: 1.0
 */
public class AccountNotFoundException extends BaseException{
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
