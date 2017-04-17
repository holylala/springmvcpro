package com.holylala.springmvcpro.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhenqionghuang@creditease.cn
 * @version V1.0
 * @Package com.holylala.springmvcpro.service
 * @Description: 图片上传
 * @date 2016/7/17 0:11
 * @Company: creditease.cn
 */
@Service
public class UploadService {

    /**
     *
     * @param file 上传的图片
     * @param uploadPath 相对路径
     * @param realUploadPath 绝对路径
     * @return
     */
    public String uploadImage(CommonsMultipartFile file,String uploadPath,String realUploadPath) {

        InputStream is = null; //文件流的读取
        OutputStream os = null; //把文件上传

        //Java7的try-with-resources语句，会自动释放资源

        try {

            is = file.getInputStream();
            String des = realUploadPath + "/" + file.getOriginalFilename();
            os = new FileOutputStream(des);

            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = is.read(buffer)) > 0) {
                os.write(buffer);
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        return uploadPath + "/" + file.getOriginalFilename();



    }

}
