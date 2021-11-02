package com.axin.idea.color.utils;

import org.springframework.core.io.ClassPathResource;

/**
* @author Axin
* @since 2021-01-11
* @summary 图片转换需要的静态资源
*/
public class ColorStaticResources {

    private ColorStaticResources(){}
    private static volatile ClassPathResource resources = null;

    public static ClassPathResource getInstance() {

        if (resources == null) {
            synchronized (ColorStaticResources.class) {
                if (resources == null) {
                    resources = new ClassPathResource("ISOcoated_v2_300_eci.icc");
                }
            }
        }
        return resources;
    }

}
