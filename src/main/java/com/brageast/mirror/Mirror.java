package com.brageast.mirror;

import com.brageast.mirror.function.FilterFunction;
import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.reflect.MirrorConstructor;
import com.brageast.mirror.reflect.MirrorField;
import com.brageast.mirror.reflect.MirrorMethod;
import com.brageast.mirror.reflect.MirrorType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @param <T> 记录传入类得类型
 */
public final class Mirror<T> {

    /**
     * 实例的对象
     */
    private T type;

    /**
     * 实例对象的类
     */
    private Class<T> typeClass;

    /**
     * 权限
     */
    private boolean accessible = false;


    /**
     * 获得默认权限
     *
     * @return 默认权限
     */
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

    /**
     * 对一个构造器操作
     *
     * @param parameters 构造器参数
     * @return
     */
    public MirrorConstructor<T> doConstructor(Object... parameters) {
        return new MirrorConstructor<>(this.typeClass, this, parameters);
    }

    private Mirror() {

    }


    /**
     * 返回一个Mirror类
     *
     * @param aclass
     * @param type
     */
    public Mirror(Class<T> aclass, T type) {
        this.typeClass = aclass;
        this.type = type;
    }

    /**
     * 对于一个class进行操作
     *
     * @param eClass
     * @param <E>
     * @return
     */
    public static <E> Mirror<E> just(Class<E> eClass) {
        Mirror<E> mirror = null;
        try {
            mirror = new Mirror<>(
                    eClass, null
            );
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
     * @return Mirror<?>
     */
    public static Mirror<?> just(String url, ThrowableFunction throwableFunction) {
        Mirror<?> mirror = null;
        try {
            Class<?> aClass = Class.forName(url);
            mirror = new Mirror(aClass, null);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return mirror;
    }

    /**
     * 对于一个已经实例化得实体操作
     *
     * @param entity 实体
     * @param <E>
     * @return
     */
    public static <E> Mirror<E> just(E entity) {
        return new Mirror(entity.getClass(), entity);
    }


    /**
     * 获得所有属性
     *
     * @return
     */
    public List<MirrorField<T, Object>> allField() {
        return allField(null);
    }


    /**
     * 获得所有属性,并通过过滤
     *
     * @param filter 过滤器
     * @return
     */
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

    /**
     * 获得所有方法
     *
     * @return
     */
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

    public MirrorType<T> doType() {
        return new MirrorType<>(this, this.typeClass);
    }

    /**
     * 对一个类的方法进行操作
     *
     * @param name              方法名
     * @param throwableFunction 异常处理
     * @param parameters        参数
     * @return
     */
    public MirrorMethod<T, Object> doOneMethod(String name, ThrowableFunction throwableFunction, Object... parameters) {
        Objects.requireNonNull(name, "方法名称不能为空");
        MirrorMethod<T, Object> mirrorMethod = null;
        try {
            mirrorMethod = MirrorMethod.of(type, this, name, parameters);
        } catch (Exception e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return mirrorMethod;
    }

    /**
     * 操作一个方法
     *
     * @param name       方法名
     * @param parameters 参数
     * @return
     */
    public MirrorMethod<T, Object> doOneMethod(String name, Object... parameters) {
        return this.doOneMethod(name, null, parameters);
    }

    /**
     * 操作一个属性
     *
     * @param name      方法名称
     * @param parameter 参数
     * @param <C>
     * @return
     */
    public <C> MirrorField<T, C> doOneField(String name, C parameter) {
        return this.doOneField(name, null, parameter);
    }


    /**
     * 对一个属性所操作
     *
     * @param name              属性名称
     * @param throwableFunction 异常处理
     * @param parameter         参数
     * @param <C>               属性类型
     * @return 本身
     */
    public <C> MirrorField<T, C> doOneField(String name, ThrowableFunction throwableFunction, C parameter) {
        Objects.requireNonNull(name, "方法名称不能为空");
        MirrorField<T, C> mirrorField = null;
        try {
            mirrorField = MirrorField.of(this.type, this, name, parameter);
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
     * @return 实例
     */
    public T getInstance() {
        return type;
    }

}
