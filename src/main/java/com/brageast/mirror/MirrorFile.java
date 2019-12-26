package com.brageast.mirror;

import com.brageast.mirror.function.ThrowableFunction;
import com.brageast.mirror.loader.MirrorClassLoader;
import com.brageast.mirror.util.FileUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * 对于jar包的操作
 *
 */
public class MirrorFile {
    private static final MirrorClassLoader mirrorClassLoader = new MirrorClassLoader();

    /**
     * 一个文件或者目录
     */
    private File file;

    public static MirrorClassLoader getClassLoader() {
        return mirrorClassLoader;
    }

    private MirrorFile(File file) {
        this.file = file;
        if (file.isDirectory()) {
            FileUtil.doFilesURL(file)
                    .stream()
                    .filter(url -> url.getPath().endsWith("jar") ||
                            url.getPath().endsWith(".class")
                    )
                    .forEach(mirrorClassLoader::addURL);
        } else {
            URL url = FileUtil.fileToURL(file);
            mirrorClassLoader.addURL(url);

        }
    }
    public Class<?> loadClass(String str) {
        return loadClass(str, null);
    }

    public Class<?> loadClass(String str, ThrowableFunction throwableFunction) {
        Class<?> cls = null;
        try {
            cls = Class.forName(str, false, mirrorClassLoader);
        } catch (ClassNotFoundException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return cls;
    }

    public Mirror<?> loadClassWhithMirror(String str) {
        return Mirror.just(loadClass(str));
    }

    public Mirror<?> loadClassWhithMirror(String str, ThrowableFunction throwableFunction) {
        return Mirror.just(loadClass(str, throwableFunction));
    }

    /**
     * 通过一个目录或者文件加载jar文件
     *
     * @param file jar文件
     * @return 本身
     */
    public static MirrorFile scan(File file) {
        Objects.requireNonNull(file);
        return new MirrorFile(file);
    }

    public static MirrorFile scan(String string) {
        final File _f = new File(string);
        return new MirrorFile(_f);
    }
}
