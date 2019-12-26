package com.brageast.mirror.entity;

/**
 * 用于转换一个Null
 * 例如:
 *      Mirror.just(User.Class)
 *          .doOneMethod("setAge", null);
 * 如果不用会导致他会匹配一个setAge()的方法
 * 而不会找setAge(int i)这个方法
 * 正确方法:
 *      Mirror.just(User.Class)
 *          .doOneMethod("setAge", Null.isNull(int.class));
 * 提示:
 *      可以使用import static com.brageast.mirror.entity.Null.isNull;
 *      方式更加方便哦!
 *
 */
public class Null {
    private Class<?> aClass;
    private Null(Class<?> aClass) {
        this.aClass = aClass;
    }
    public static Null isNull(Class<?> aClass) {
        return new Null(aClass);
    }

    public Class<?> getTypeClass() {
        return aClass;
    }

}
