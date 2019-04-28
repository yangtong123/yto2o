package com.yt.util;

import javax.sound.midi.Soundbank;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/24 23:01
 */
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/IdeaProject/yto2o/image/";
        } else {
            basePath = "/home/yt/image/";
        }
        basePath = basePath.replace("/", seperator);

        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "upload/shop/" + shopId + "/";

        return imagePath.replace("/", seperator);
    }

    public static void main(String[] args) {
        System.out.println(seperator);
        System.out.println(System.getProperty("os.name"));
        System.out.println(getImgBasePath());
    }
}
