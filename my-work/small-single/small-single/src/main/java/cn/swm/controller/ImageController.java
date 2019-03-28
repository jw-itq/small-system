package cn.swm.controller;

import cn.swm.common.exception.SmallUploadException;
import cn.swm.pojo.common.Result;
import cn.swm.utils.QiniuUtil;
import cn.swm.utils.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class ImageController {

    @RequestMapping(value = "/image/imageUpload",method = RequestMethod.POST)
    public Result<Object> uploadFile(@RequestParam("file")MultipartFile files,
                                     HttpServletRequest request){
        String imagePath = null;
        // 文件保存路径
        String filePath = request.getSession().getServletContext().getRealPath("/upload")+"\\"
                + QiniuUtil.renamePic(files.getOriginalFilename());
        // 转存文件
        try {
            //保存至服务器
            File file=new File((filePath));
            files.transferTo(file);
            //上传七牛云服务器
            imagePath= QiniuUtil.qiniuUpload(filePath);
            if(imagePath.contains("error")){
                throw new SmallUploadException("上传失败");
            }
            // 路径为文件且不为空则进行删除
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultUtil<Object>().setData(imagePath);
    }
}
