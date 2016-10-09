package info.puton.product.smartsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * Created by Pauline on 16/10/8.
 */


@Controller
@RequestMapping(value="/file")
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request, ModelMap model) {
        System.out.println("开始");
        //创建你要保存的文件的路径
        String path = request.getSession().getServletContext().getRealPath("uploadfile");
        //获取该文件的文件名
        String fileName = file.getOriginalFilename();

        System.out.println(path);
        System.out.println(fileName);
        File targetFile = new File(path, fileName);
        System.out.println(targetFile);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将该文件的路径给客户端，让其可以请求该文件
        model.addAttribute("fileUrl", request.getContextPath() + "/uploadfile/"+ fileName);
        return "redirect:/result.html";
    }
}
