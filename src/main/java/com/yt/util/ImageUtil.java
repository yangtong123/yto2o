package com.yt.util;

import com.yt.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/24 23:09
 */
public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is: " + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current completeAddr is: " + dest);
        logger.debug("BasePath is: " + basePath);
        try {
            Thumbnails.of(thumbnail.getImage()).size(2000, 2000)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("水印错误！");
        }

        return relativeAddr;
    }

    public static String generateNormalThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is: " + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current completeAddr is: " + dest);
        logger.debug("BasePath is: " + basePath);
        try {
            Thumbnails.of(thumbnail.getImage()).size(327, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }

        return relativeAddr;

    }

    /**
     * 创建目标路径所涉及的目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String getRandomFileName() {
        // 获取随机的五位数
        int ranNum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());

        return nowTimeStr + ranNum;
    }

    /**
     * 删除文件以及目录
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }


    public static void main(String[] args) throws IOException {

        System.out.println(basePath);
        Thumbnails.of(new File("E:\\IdeaProject\\yto2o\\image\\ironman.jpg")).size(2000, 2000)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                .outputQuality(0.8f).toFile("E:\\IdeaProject\\yto2o\\image\\ironmannew.jpg");
    }
}
