package com.axin.idea.color.utils;

import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;
import com.sun.media.jai.codecimpl.TIFFImageEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
* @author Axin
* @since 2021-01-08
* @summary 图片DPI处理工具类
*/
public class PicDpiUtils {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PicDpiUtils.class);

    /**
     * 改变图片DPI 格式要求 tif
     *
     * @param file 源图片
     * @param dpiFile 提高dpi后的图片
     */
    public static void upDpi(File file, File dpiFile, int dpi) {
        upDpi(file, dpiFile, dpi, dpi);
    }

    /**
     * 改变图片DPI 格式要求 tif
     *
     * @param file    源图片
     * @param dpiFile 提高dpi后的图片
     * @param dpi1 x
     * @param dpi2 y
     */
    public static void upDpi(File file, File dpiFile, int dpi1, int dpi2) {
        try (FileOutputStream outputStream = new FileOutputStream(dpiFile)) {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                log.warn("源文件:{}不是图片", file.getPath());
                return;
            }

            TIFFEncodeParam param = new TIFFEncodeParam();
            param.setCompression(TIFFEncodeParam.COMPRESSION_NONE);
            TIFFField[] extras = new TIFFField[2];
            extras[0] = new TIFFField(282, 5, 1, new long[][]{{(long) dpi1, 1}, {0, 0}});
            extras[1] = new TIFFField(283, 5, 1, new long[][]{{(long) dpi2, 1}, {0, 0}});
            param.setExtraFields(extras);
            TIFFImageEncoder encoder = new TIFFImageEncoder(outputStream, param);
            encoder.encode(image);
            image.flush();
        } catch (Exception e) {
            log.error("图片提高DPI失败!", e);
        }
    }

}
