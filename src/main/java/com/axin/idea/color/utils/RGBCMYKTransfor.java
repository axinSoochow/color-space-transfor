package com.axin.idea.color.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Axin
 * @summary 图片模式转换基础工具
 * @since 2020-12-29
 */
public class RGBCMYKTransfor {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RGBCMYKTransfor.class);

    /**
     * 将图片为rgb转化为cmyk 图片文件
     *
     * 生成tif格式图片 为海报打印用
     *
     * TIFF格式在业界得到了广泛的支持，
     * 如Adobe公司的Photoshop、The GIMP Team的GIMP、Ulead PhotoImpact和Paint Shop Pro等图像处理应用、QuarkXPress和Adobe
     * InDesign这样的桌面印刷和页面排版应用，扫描、传真、文字处理、光学字符识别和其它一些应用等都支持这种格式。
     *
     * 有可能返回 null
     */
    public static File rgbToCmykFile(InputStream inputStream, String fileName) {
        if (inputStream == null || StringUtils.isEmpty(fileName)) {
            return null;
        }

        File tempFile;

        try {
            // 1. 获取工业标准文件
            ClassPathResource resource = ColorStaticResources.getInstance();
            BufferedImage rgbImage = ImageIO.read(inputStream);
            ColorSpace cpace = new ICC_ColorSpace(ICC_Profile.getInstance(resource.getInputStream()));
            ColorConvertOp op = new ColorConvertOp(rgbImage.getColorModel().getColorSpace(), cpace, null);
            BufferedImage cmykImage = op.filter(rgbImage, null);

            // 2. 生成图片
            tempFile = File.createTempFile(fileName, ".tif");

            log.debug("图片路径：{}", tempFile.getPath());
            JAI.create("filestore", cmykImage, tempFile.getPath(), "TIFF");
            cmykImage.flush();
            return tempFile;
        } catch (Exception e) {
            log.error("RGB图片转码为CMYK失败！", e);
        }
        return null;
    }

    /**
     * RGB转换CMYK 同时修改dpi
     * @param inputStream
     * @param fileName
     * @param dpi 图片dpi
     * @return 有可能返回空
     */
    public static File rgbToCmykFile(InputStream inputStream, String fileName, int dpi) {
        File file = rgbToCmykFile(inputStream, fileName);
        if (file == null) {
            return null;
        }

        try {
            File dpiFile = File.createTempFile(String.format("%s-%sdpi", fileName, dpi), ".tif");
            PicDpiUtils.upDpi(file, dpiFile, dpi);
            return dpiFile;
        } catch (Exception e) {
            log.error("图片提高dpi失败！");
        }finally {
            if (file != null) {
                file.delete();
            }
        }
        return null;
    }

    /**
     * 将图片为rgb转化为cmyk 图片流 临时文件会被自动删除
     * 有可能返回 null
     */
    public static BufferedImage rgbToCmykStream(InputStream inputStream, String fileName) {
        File file = rgbToCmykFile(inputStream, fileName);
        if (file == null) {
            return null;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedImage read = ImageIO.read(fileInputStream);
            return read;
        } catch (Exception e) {
            log.error("CMYK图片读取失败！");
        }finally {
            if (file != null) {
                file.delete();
            }
        }
        return null;
    }

    /**
     * 将图片为rgb转化为cmyk 图片流 临时文件会被自动删除
     * 有可能返回 null
     */
    public static BufferedImage rgbToCmykStream(InputStream inputStream, String fileName, int dpi) {
        File file = rgbToCmykFile(inputStream, fileName);
        if (file == null) {
            return null;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedImage read = ImageIO.read(fileInputStream);
            return read;
        } catch (Exception e) {
            log.error("CMYK图片读取失败！");
        } finally {
            if (file != null) {
                file.delete();
            }
        }
        return null;
    }


}
