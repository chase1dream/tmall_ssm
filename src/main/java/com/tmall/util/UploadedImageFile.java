package com.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author tanquan
 * @description <p>
 * 图片上传工具类
 * </p>
 * @date 2019/7/2 16:00
 */
public class UploadedImageFile {
    // 声明一个 MultipartFile 类型的属性，用于接受上传文件的注入
    // 属性名称image必须和页面中的增加分类部分中的type="file"的name值保持一致
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
