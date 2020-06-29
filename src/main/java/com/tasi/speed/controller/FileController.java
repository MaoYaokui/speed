package com.tasi.speed.controller;

import com.tasi.speed.common.CommonFunction;
import com.tasi.speed.common.query.R;
import com.tasi.speed.config.FileProperties;
import com.tasi.speed.controller.common.PublicTools;
import com.tasi.speed.dto.product.ProductImageForm;
import com.tasi.speed.model.ProductImage;
import com.tasi.speed.service.ProductImageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName FileController
 * @Description TODO
 * @Author myk
 * @Date 2020/2/28 0:10
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
public class FileController extends PublicTools {
    @Autowired
    FileProperties fileProperties;
    @Autowired
    private ProductImageServer productImageServer;
    /**
     * 商品缩略图
     */
    String abbreviation = "abbreviation";

    @Value("${web.path}")
    private String webPath;

    @ResponseBody
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, String type) {
        if (file.isEmpty() && type == null) {
            return R.error("上传失败，请选择文件");
        }
        // 文件类型 图标 icon  商品预览图 original 商品缩略图 abbreviation
        String filePath = type + "/" + file.getOriginalFilename();
        File dest = new File(fileProperties.getUploadDir() + filePath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 若文件为商品缩略图
        if (abbreviation.equalsIgnoreCase(type)) {
            ProductImage productImage = new ProductImage();
            productImage.setOriginalImg(filePath);
            productImage.setCreateAt(new Date());
            productImageServer.save(productImage);

            ProductImageForm productImageForm = new ProductImageForm();
            productImageForm.setId(productImage.getId());
            productImageForm.setUrl(webPath + productImage.getOriginalImg());
            return R.ok().put("image", productImageForm);
        }
        return R.ok().put("filePath", filePath);
    }
}
