package top.newpointer.farm.utils;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import top.newpointer.farm.GetBeanUtil;

import java.io.File;
import java.util.UUID;

public class ImageUtil {

    private static String headPortraitPath = GetBeanUtil.getPropertiesValue("linux_head_portrait_path");

    public static String savePortraitImg(MultipartFile img) {
        //获取原文件名
        String originalFilename = img.getOriginalFilename();

        //获取使用uuid生成的十位数字文件名
        Integer randNumber = UUID.randomUUID().toString().hashCode();
        randNumber = randNumber < 0 ? -randNumber : randNumber;
        String fileName = String.format("%010d", randNumber);

        //获取文件的后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());

        //拼接成新文件名
        String newFileName = fileName + "." + suffix;

        //linux下存储头像目录
        String filePath = headPortraitPath + "/" + newFileName;
        System.err.println(headPortraitPath);
        System.err.println(filePath);
        try {
            img.transferTo(new File(filePath));
        } catch (Exception e) {
            System.err.println("文件上传异常！");
        }
        return newFileName;
    }
}
