package com.holylala.springmvcpro.controller;

import com.holylala.springmvcpro.service.ThumbnailService;
import com.holylala.springmvcpro.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author zhenqionghuang@creditease.cn
 * @version V1.0
 * @Package com.holylala.springmvcpro.controller
 * @Description:
 * @date 2016/7/16 23:57
 * @Company: creditease.cn
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private UploadService uploadService;
    private ThumbnailService thumbnailService;

    @Autowired
    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }
    @Autowired
    public void setThumbnailService(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }


    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String showUploadPage(){

        return "uploadfile";
    }

    @RequestMapping(value = "/thumbnail",method = RequestMethod.POST)
    public ModelAndView thumbnail(@RequestParam("image") CommonsMultipartFile file, HttpSession session) throws Exception {

        //
        String uploadPath = "/resources/imgs";
        //绝对路径
        String realUploadPath = session.getServletContext().getRealPath(uploadPath);

        System.out.println("realUploadPath:>>>"+realUploadPath);

        String imageUrl = uploadService.uploadImage(file,uploadPath,realUploadPath); //原图
        String thumImageUrl = thumbnailService.thumbnail(file,uploadPath,realUploadPath);//缩略图

        ModelAndView ret = new ModelAndView();
        ret.addObject("imageUrl",imageUrl);
        ret.addObject("thumImageUrl",thumImageUrl);

        ret.setViewName("thumbnail"); //找到thumbnail.jsp

        return  ret;

    }

}
