package com.holylala.springmvcpro.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author zhenqionghuang@creditease.cn
 * @version V1.0
 * @Package com.holylala.springmvcpro.service
 * @Description:
 * @date 2016/7/18 22:47
 * @Company: creditease.cn
 */
@Service
public class ThumbnailService {

    public static final int WIDTH = 100;
    public static final int HEIGH = 100;

    public String thumbnail(CommonsMultipartFile file,String uploadPath,String realUploadPath) {
        try  {

            String des = realUploadPath + "/thum_" +file.getOriginalFilename();

            Thumbnails.of(file.getInputStream()).size(WIDTH,HEIGH).toFile(des);//压缩图片 并且存入某个文件

        } catch (Exception e) {
            e.printStackTrace();
        }

        return uploadPath + "/thum_" +file.getOriginalFilename();

    }

}
