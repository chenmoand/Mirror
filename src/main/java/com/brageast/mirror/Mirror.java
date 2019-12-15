package com.brageast.mirror;

import com.brageast.mirror.function.FilterFunction;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.reflect.MirrorConstructor;
import com.brageast.mirror.reflect.MirrorField;
import com.brageast.mirror.reflect.MirrorMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Mirror<T> {

    // 实例的对象
    private T type;
    // 实例对象的类
    private Class<T> typeClass;

    // 权限
    private boolean accessible = false;

    public boolean defaultAccessible() {
        return accessible;
    }

    /**
     * 默认所有关闭操作权限检查
     *
     * @return
     */
    public Mirror<T> defaultOffAll() {
        accessible = true;
        return this;
    }

    public MirrorConstructor<T> doConstructor(T t, Object[] objects) {
        return new MirrorConstructor<>(t, this, objects);
    }

    private Mirror() {

    }

    public Mirror(Class<T> aclass, T type) {
        this.typeClass = aclass;
        this.type = type;
    }

    public static <E> Mirror<E> just(Class<E> eClass) {
        Mirror<E> mirror = null;
        try {
            mirror = new Mirror<>(eClass, eClass.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mirror;
    }

    /**
     * 实例化一个Mirror反射类
     *
     * @param url 反射类的地址
     * @return Mirror<?>类
     */
    public static Mirror<?> just(String url) {
        return just(url, null);
    }

    /**
     * 实例化一个Mirror反射类
     *
     * @param url               反射类的地址
     * @param throwableFunction 遇到异常的解决方式
     * @return Mirror<?>类
     */
    public static Mirror<?> just(String url, ThrowableFunction throwableFunction) {
        Mirror<?> mirror = null;
        try {
            Class<?> aClass = Class.forName(url);
            mirror = new Mirror(aClass, aClass.getConstructor().newInstance());
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return mirror;
    }

    public static <E> Mirror<E> just(E entity) {
        return new Mirror(entity.getClass(), entity);
    }

    public Mirror<T> doOneConstructor(Object... objects) {
        return this;
    }

    public List<MirrorField<T, Object>> allField() {
        return allField(null);
    }

    public List<MirrorField<T, Object>> allField(FilterFunction<MirrorField<T, Object>> filter) {
        final Field[] declaredFields = typeClass.getDeclaredFields();
        List<MirrorField<T, Object>> mirrorFields = new ArrayList<>();
        for (Field field : declaredFields) {
            MirrorField<T, Object> mirrorField = new MirrorField<>(type, this, field);
            if (filter == null || filter.doFilter(mirrorField)) {
                mirrorFields.add(mirrorField);
            }
        }
        return mirrorFields;
    }

    public List<MirrorMethod<T, Object>> allMethod() {
        return allMethod(Objects::nonNull);
    }

    /**
     * 查询所有类方法
     *
     * @param filter 过滤器
     * @return
     */
    public List<MirrorMethod<T, Object>> allMethod(FilterFunction<MirrorMethod<T, Object>> filter) {
        final Method[] declaredMethods = typeClass.getDeclaredMethods();
        List<MirrorMethod<T, Object>> mirrorMethods = new ArrayList<>();
        for (Method method : declaredMethods) {
            MirrorMethod<T, Object> mirrorMethod = new MirrorMethod<>(type, this, method);
            if (filter == null || filter.doFilter(mirrorMethod)) {
                mirrorMethods.add(mirrorMethod);
            }
        }
        return mirrorMethods;
    }

    /**
     * 对一个类的方法进行操作
     *
     * @param name              方法名
     * @param throwableFunction 异常处理
     * @param objects           方法所需字段
     * @return
     */
    public MirrorMethod<T, Object> doOneMethod(String name, ThrowableFunction throwableFunction, Object... objects) {
        Objects.requireNonNull(name, "方法名称不能为空");
        MirrorMethod<T, Object> mirrorMethod = null;
        try {
            mirrorMethod = MirrorMethod.of(type, this, name, objects);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return mirrorMethod;
    }

    public MirrorMethod<T, Object> doOneMethod(String name, Object... objects) {
        return this.doOneMethod(name, null, objects);
    }

    public <C> MirrorField<T, C> doOneField(String name, C objValue) {
        return this.doOneField(name, null, objValue);
    }


    /**
     * 对一个属性所操作
     *
     * @param name              属性名称
     * @param throwableFunction 异常处理
     * @param objValue          属性值
     * @param <C>               属性类型
     * @return
     */
    public <C> MirrorField<T, C> doOneField(String name, ThrowableFunction throwableFunction, C objValue) {
        Objects.requireNonNull(name, "方法名称不能为空");
        MirrorField<T, C> mirrorField = null;
        try {
            mirrorField = MirrorField.of(this.type, this, name, objValue);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return mirrorField;
    }


    /**
     * 找到所有对于一个返回类的集合
     *
     * @param returnType 判断的方法返回的class是否一致
     * @param <C>        class的类型
     * @return 一个包装MirrorMethod的集合
     */
    public <C> List<MirrorMethod<T, C>> withReturnTypeMethod(Class<C> returnType) {
        final Method[] declaredMethods = typeClass.getDeclaredMethods();
        List<MirrorMethod<T, C>> mirrorMethods = new ArrayList<>();
        for (Method method : declaredMethods) {
            MirrorMethod<T, C> mirrorMethod = new MirrorMethod<>(this.type, this, method);
            if (mirrorMethod.eqReturnType(returnType)) {
                mirrorMethods.add(mirrorMethod);
            }
        }
        return mirrorMethods;
    }

    /**
     * 找到所有是这个类的属性集合
     *
     * @param fieldType 要判断的符合属性的类
     * @param <C>       class的类型
     * @return 一个包装MirrorField的集合
     */
    public <C> List<MirrorField<T, C>> withTypeField(Class<C> fieldType) {
        final Field[] fields = typeClass.getDeclaredFields();
        List<MirrorField<T, C>> mirrorFields = new ArrayList<>();
        for (Field field : fields) {
            MirrorField<T, C> mirrorField = new MirrorField<>(this.type, this, field);
            if (mirrorField.eqFieldType(fieldType)) {
                mirrorFields.add(mirrorField);
            }
        }
        return mirrorFields;
    }

    /**
     * 得到操作的实例
     *
     * @return
     */
    public T getInstance() {
        return type;
    }

}
