package com.brageast.mirror.util;

public class Convert {

    public static final Class<?>[] BASE_TYPES = {
            int.class, byte.class, short.class,
            long.class, double.class, boolean.class,
            float.class, char.class
    };

    private int index;
    private Object value;

    private Convert(Object value, int index) {
        this.value = value;
        this.index = index;
    }


    public static Convert conver(int i) {
        return new Convert(i, 0);
    }

    public static Convert conver(byte b) {
        return new Convert(b, 1);
    }

    public static Convert conver(short s) {
        return new Convert(s, 2);
    }

    public static Convert conver(long l) {
        return new Convert(l, 3);
    }

    public static Convert conver(double d) {
        return new Convert(d, 4);
    }

    public static Convert conver(boolean bol) {
        return new Convert(bol, 5);
    }

    public static Convert conver(float f) {
        return new Convert(f, 6);
    }

    public static Convert conver(char c) {
        return new Convert(c, 7);
    }


    public Class<?> getTypeClass() {
        return BASE_TYPES[index];
    }

    public Object getTypeValue() {
        return this.value;
    }


}
