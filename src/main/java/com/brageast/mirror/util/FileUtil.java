package com.brageast.mirror.util;

import com.brageast.mirror.function.ThrowableFunction;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class FileUtil {

    public static Set<URL> doFilesURL(File file) {
        return Arrays.stream(file.listFiles())
                .filter(f -> f.getName().endsWith(".jar"))
                .map(FileUtil::fileToURL)
                .collect(Collectors.toSet());
    }



    public static URL fileToURL(File file) {
        return fileToURL(file, null);
    }

    public static URL fileToURL(File file, ThrowableFunction throwableFunction) {
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            ThrowableFunction.isNull(e, throwableFunction);
        }
        return url;
    }


}
