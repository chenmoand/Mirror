package com.brageast.mirror;

import com.brageast.mirror.loader.MirrorClassLoader;
import com.brageast.mirror.util.FileUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class MirrorFile {


    private static final MirrorClassLoader mirrorClassLoader = new MirrorClassLoader();
    private File file;

    public static MirrorClassLoader getMirrorClassLoader() {
        return mirrorClassLoader;
    }

    private MirrorFile(File file) {
        this.file = file;
        if (file.isDirectory()) {
            FileUtil.doFilesURL(file)
                    .forEach(mirrorClassLoader::addURL);
        } else {
            URL url = FileUtil.fileToURL(file);
            mirrorClassLoader.addURL(url);

        }
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
