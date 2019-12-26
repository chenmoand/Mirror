package com.brageast.mirror.entity;

/**
 * 用于转换一个基本类型
 * 例如:
 *      Mirror.just(User.Class)
 *          .doOneMethod("setAge", 12);
 * 如果不使用Convert 会把12 解析成 Integer.class类型
 * 而方法是普通得int.class ,会导致找不到方法
 * 正确方法:
 *      Mirror.just(User.Class)
 *          .doOneMethod("setAge", Convert.cover(12));
 * 提示:
 *      可以使用import static com.brageast.mirror.util.Covert.cover;
 *      方式更加方便哦!
 */
public class Convert {

    public static final Class<?>[] BASE_TYPES = {
            int.class, byte.class, short.class,
            long.class, double.class, boolean.class,
            float.class, char.class,
            int[].class, byte[].class, short[].class,
            long[].class, double[].class, boolean[].class,
            float[].class, char[].class,
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
    public static Convert conver(int[] is) {
        return new Convert(is, 8);
    }

    public static Convert conver(byte[] bs) {
        return new Convert(bs, 9);
    }

    public static Convert conver(short[] ss) {
        return new Convert(ss, 10);
    }

    public static Convert conver(long[] ls) {
        return new Convert(ls, 11);
    }

    public static Convert conver(double[] ds) {
        return new Convert(ds, 12);
    }

    public static Convert conver(boolean[] bols) {
        return new Convert(bols, 13);
    }

    public static Convert conver(float[] fs) {
        return new Convert(fs, 14);
    }

    public static Convert conver(char[] cs) {
        return new Convert(cs, 15);
    }

    /**
     * 得到基础类型的class
     *
     * @return
     */
    public Class<?> getTypeClass() {
        return BASE_TYPES[index];
    }

    /**
     * 得到基础类型的值
     *
     * @return
     */
    public Object getTypeValue() {
        return this.value;
    }


}
