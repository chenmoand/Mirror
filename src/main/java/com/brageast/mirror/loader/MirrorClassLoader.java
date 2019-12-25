package com.brageast.mirror.loader;

import java.net.URL;
import java.net.URLClassLoader;

public class MirrorClassLoader extends URLClassLoader {

    public MirrorClassLoader() {
        super(new URL[]{});
    }

    public MirrorClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public Class<?> loadClass(String name, byte[] b) {
        return this.defineClass(name, b, 0, 0);
    }

    public Class<?> loadClass(String name, byte[] b, int off, int len) {
        return this.defineClass(name, b, off, len);
    }
}
