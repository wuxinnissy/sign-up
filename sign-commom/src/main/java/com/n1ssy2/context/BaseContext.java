package com.n1ssy2.context;

/**
 * ClassName: BaseContext
 * Package: com.n1ssy2.context
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:27
 * @Version: 1.0
 */
public class BaseContext {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(String id) {
        threadLocal.set(id);
    }

    public static String getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
