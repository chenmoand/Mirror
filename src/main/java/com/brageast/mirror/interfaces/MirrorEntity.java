package com.brageast.mirror.interfaces;

/**
 * 用于处理注解的类
 *
 */
public abstract class MirrorEntity {
    /**
     * 对于属性的修改请重写这个
     *
     * @param entity
     * @return
     */
    public Object onFieldModify(Object entity) {
        return null;
    }

    /**
     * 对于方法构造器的修改
     * 请重写这个方法
     *
     * @param parameters 参数
     * @return
     */
    public Object[] onMethodModify(Object[] parameters) {
        return parameters;
    }

    /**
     * 修改的结果的值调用这个方法
     *
     * @param entity
     */
    public void onModifyResult(Object entity) {

    }
}