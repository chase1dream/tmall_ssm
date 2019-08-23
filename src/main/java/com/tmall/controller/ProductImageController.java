package com.tmall.controller;

import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import com.tmall.util.ImageUtil;
import com.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * </p>
 * @date 2019/7/10 上午 10:10
 */
@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product product = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        model.addAttribute("product", product);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile) {
        // 将产品图片对象插入数据库
        productImageService.add(productImage);
        // 根据图片id和后缀定义图片名称
        String fileName = productImage.getId() + ".jpg";
        // 定义三个变量，作为图片的存储路径
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        // 不同的图片类型有不同的存储路径
        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        }else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }
        // 创建要上传的图片对象
        File file = new File(imageFolder, fileName);
        // 创建父目录
        file.getParentFile().mkdirs();
        // 拿到上传的图片，并对图片进行处理
        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage image = ImageUtil.change2jpg(file);
            ImageIO.write(image, "jpg", file);
            // 若图片类型是产品单个图片，则还要进行相关处理
            if (ProductImageService.type_single.equals(productImage.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);
                // 重置图片大小
                ImageUtil.resizeImage(file, 56, 56, f_small);
                ImageUtil.resizeImage(file, 217, 190, f_middle);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) {
        // 先删除项目中img目录里对应的图片
        ProductImage productImage = productImageService.get(id);
        // 根据图片id和后缀定义图片名称
        String fileName = productImage.getId() + ".jpg";
        // 定义三个变量，作为图片的存储路径
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        // 不同的图片类型有不同的存储路径
        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
            // 找到要删除的图片
            File imageFile = new File(imageFolder, fileName);
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            // 删除图片
            imageFile.delete();
            f_small.delete();
            f_middle.delete();
        }else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder, fileName);
            imageFile.delete();
        }
        // 之后根据产品图片id把表中的产品图片数据删除
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }


}
