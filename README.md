# color-space-transfor

本Java工具包提供图片色彩空间的转换、图片dpi刷新的能力。

## 什么是色彩空间/色域？
![image](http://axin-soochow.oss-cn-hangzhou.aliyuncs.com/21-10/color_trans.jpg)

“色彩空间”一词源于西方的“Color Space”，又称作“[色域](https://baike.baidu.com/item/%E8%89%B2%E5%9F%9F/5857970)”，色彩学中，人们建立了多种色彩模型，以一维、二维、三维甚至四维空间坐标来表示某一色彩，这种坐标系统所能定义的色彩范围即色彩空间。

人眼可见的色彩包含数百万种颜色，但扫描仪、显示器和彩色打印机等显色设备只能产生其中的一部分颜色，这个"子集"称为色域。因此，人们为不同的领域制定了不同的色域标准，可以从上图中看到不同色域的划分。

我们经常用到的色彩空间主要有[RGB](https://baike.baidu.com/item/RGB/342517)、[CMYK](https://baike.baidu.com/item/CMYK/888085)、Lab等。


## 什么场景下需要转换色彩空间/色域？

![image](http://axin-soochow.oss-cn-hangzhou.aliyuncs.com/21-10/seyu.jpg)

每个场景对图片的色域要求也是不一样的，比如在互联网上传输的图片，色域基本都是sRGB的，而真实打印图片时，往往要求图片是CMYK模式的。这个时候就需要对图片进行色彩空间转换。

## CMYK色彩空间转换工具使用
目前Jar包只支持将图片由**RGB模式转换为CMYK模式**，转换后的图片为TIFF格式，后缀为'.tif'。

要转码为CMYK的图片，基本都是用于打印的，而打印需要图片的dpi达到300（行业内的一般约定），故工具包也提供了刷新图片dpi的功能。

要注意的是，转码后的图片的格式是TIFF格式，通常会比较大。

## 工具方法

RGBCMYKTransfor


```Java
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
   public static File rgbToCmykFile(InputStream inputStream, String fileName);
     
   /**
    * RGB转换CMYK 同时修改dpi
    * @param inputStream
    * @param fileName
    * @param dpi 图片dpi
    * @return 有可能返回空
    */
   public static File rgbToCmykFile(InputStream inputStream, String fileName, int dpi);
 
   /**
    * 将图片为rgb转化为cmyk 图片流 临时文件会被自动删除
    * 有可能返回 null
    */
   public static BufferedImage rgbToCmykStream(InputStream inputStream, String fileName);
 
   /**
    * 将图片为rgb转化为cmyk 图片流 临时文件会被自动删除
    * 有可能返回 null
    */
   public static BufferedImage rgbToCmykStream(InputStream inputStream, String fileName, int dpi);
```

## 性能测试

测试方法：


```Java
public static File rgbToCmykFile(InputStream inputStream, String fileName);
```

本机环境：Mac pro 17款 CPU：双核心 2.3GHz 

测试方法：

```Java
public static void main(String[] args) {
 
        StopWatch watch = new StopWatch();
        //静态资源预热
        try (FileInputStream in = new FileInputStream("/Users/axin/IdeaProjects/axin-framework/world/src/main/java/com/axin/world/picTest/200kb.jpg")) {
            RGBCMYKTransfor.rgbToCmykFile(in, "200kb-cmyk");
        } catch (Exception e) {
 
        }
 
        watch.start("图片色彩空间转换-200kb");
        try (FileInputStream in = new FileInputStream("/Users/axin/IdeaProjects/axin-framework/world/src/main/java/com/axin/world/picTest/200kb.jpg")) {
            RGBCMYKTransfor.rgbToCmykFile(in, "200kb-cmyk");
        } catch (Exception e) {
 
        }
        watch.stop();
 
        watch.start("图片色彩空间转换-500kb");
        try (FileInputStream in = new FileInputStream("/Users/axin/IdeaProjects/axin-framework/world/src/main/java/com/axin/world/picTest/500kb.jpg")) {
 
            RGBCMYKTransfor.rgbToCmykFile(in, "500kb-cmyk");
        } catch (Exception e) {
 
        }
        watch.stop();
 
        watch.start("图片色彩空间转换-1.4mb");
        try (FileInputStream in = new FileInputStream("/Users/axin/IdeaProjects/axin-framework/world/src/main/java/com/axin/world/picTest/1.4MB.png")) {
 
            RGBCMYKTransfor.rgbToCmykFile(in, "1.5mb-cmyk");
        } catch (Exception e) {
 
        }
        watch.stop();
 
        watch.start("图片色彩空间转换-2mb");
        try (FileInputStream in = new FileInputStream("/Users/axin/IdeaProjects/axin-framework/world/src/main/java/com/axin/world/picTest/2MB.jpg")) {
 
            RGBCMYKTransfor.rgbToCmykFile(in, "2mb-cmyk");
        } catch (Exception e) {
 
        }
        watch.stop();
 
        watch.start("图片色彩空间转换-4mb");
        try (FileInputStream in = new FileInputStream("/Users/axin/IdeaProjects/axin-framework/world/src/main/java/com/axin/world/picTest/4MB.png")) {
 
            RGBCMYKTransfor.rgbToCmykFile(in, "4mb-cmyk");
        } catch (Exception e) {
 
        }
        watch.stop();
 
        System.out.println(watch.prettyPrint());
    }
```

执行结果：


```
-----------------------------------------
ms % Task name
-----------------------------------------
00195 003% 图片色彩空间转换-200kb
00713 011% 图片色彩空间转换-500kb
01192 019% 图片色彩空间转换-1.4mb
01765 028% 图片色彩空间转换-2mb
02359 038% 图片色彩空间转换-4mb
```


测试方法：

```
public static File rgbToCmykFile(InputStream inputStream, String fileName, int dpi);
```


```
-----------------------------------------
ms % Task name
-----------------------------------------
00287 005% 图片色彩空间转换-200kb
00608 010% 图片色彩空间转换-500kb
00992 016% 图片色彩空间转换-1.5mb
01662 026% 图片色彩空间转换-2mb
02759 044% 图片色彩空间转换-4mb
```
