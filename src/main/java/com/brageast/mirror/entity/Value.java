package com.brageast.mirror.entity;

public class Value<E> {
    private Class<E> typeClass;
    private E typeValue;

    public Value(Class<E> typeClass, E typeValue) {
        this.typeClass = typeClass;
        this.typeValue = typeValue;
    }

    public Value() {
    }

    public Class<E> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Class<E> typeClass) {
        this.typeClass = typeClass;
    }

    public E getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(E typeValue) {
        this.typeValue = typeValue;
    }

    @Override
    public String toString() {
        return "Value{" +
                "typeClass=" + typeClass +
                ", typeValue=" + typeValue +
                '}';
    }
}
