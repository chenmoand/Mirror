package com.brageast.mirror.test;

import com.brageast.mirror.MirrorFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileTest {

    private static File file = new File("d:/Downloads/dom4j-2.1.1.jar");

    private static JarFile jarFile;

    private static JarClassLoader jarClassLoader = new JarClassLoader(new URL[]{});

    static {
        try {
            jarFile = new JarFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        /*jarClassLoader.addURL(file.toURI().toURL());
        Class<?> aClass = Class.forName("org.dom4j.bean.BeanAttribute", false, jarClassLoader);

        System.out.println(aClass);*/
        MirrorFile scan = MirrorFile.scan("D:\\Downloads\\dom4j-2.1.1.jar");
        scan.loadClassWhithMirror("org.dom4j.util.NonLazyElement")
                .doConstructor("哈哈").off()
                .newInstance()
                .doOneMethod("toString")
                .notUseDeclared()
                .invoke(System.out::print);
    }


    public static void doJarEntry(JarEntry jarEntry) {
        try (
                InputStream inputStream = jarFile.getInputStream(jarEntry)
        ) {

            String name = jarEntry.getName();
            String replace = name.replace("/", ".").replace(".class", "");

            System.out.println(replace);


            int size = (int) jarEntry.getSize();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);


            Class<?> aClass = jarClassLoader.loadClass(replace, bytes, 0, size);
            System.out.println(aClass);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    static class JarClassLoader extends URLClassLoader {

        public JarClassLoader(URL[] urls) {
            super(urls);
        }

        @Override
        public void addURL(URL url) {
            super.addURL(url);
        }

        public Class<?> loadClass(String name, byte[] b, int off, int len) {
            return this.defineClass(name, b, off, len);
        }

    }
}
