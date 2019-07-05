package com.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Page;
import com.tmall.service.CategoryService;
import com.tmall.util.ImageUtil;
import com.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author tanquan
 * @description <p>
 * 分类管理功能Controller类
 * </p>
 * @date 2019/6/27 20:17
 */
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    // 查询 + 分页
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        // 通过分页插件指定分页参数
        PageHelper.offsetPage(page.getStart(), page.getCount());
        // 获取当前页的分类集合
        List<Category> categoryList = categoryService.list();
        // 通过PageInfo获取总数
        int total = (int) new PageInfo<>(categoryList).getTotal();
        page.setTotal(total);
        // 把分类集合放在"categoryList"中
        model.addAttribute("categoryList", categoryList);
        // 把分页对象放在 "page“ 中
        model.addAttribute("page", page);
        // 跳转到listCategory.jsp页面显示
        return "admin/listCategory";
    }

    // 添加
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        // 保存category对象到categoryService
        categoryService.add(category);
        // 通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        // System.out.println(imageFolder);
        // 根据分类id创建文件名,保存到/img/category目录
        File file = new File(imageFolder, category.getId() + ".jpg");
        // 如果/img/category目录不存在，则创建该目录，否则后续保存浏览器传过来图片，会提示无法保存
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            // 拿到上传的图片，转为文件
            uploadedImageFile.getImage().transferTo(file);
            // 将文件处理后放入缓存域
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

    // 删除，根据id删除分类数据
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) {
        // 通过categoryService删除数据
        categoryService.delete(id);
        // 通过session获取ControllerContext然后获取分类图片位置，接着删除分类图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    // 编辑
    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "admin/editCategory";
    }

    // 修改
    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);
        // 判断是否有上传图片，如果有上传，那么通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径。
        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }
}
