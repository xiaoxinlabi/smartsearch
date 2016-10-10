package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.constant.FilePath;
import info.puton.product.smartsearch.service.FileStorage;
import info.puton.product.smartsearch.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Pauline on 16/10/8.
 */


@Controller
@RequestMapping(value="/file")
public class FileController {

    @Autowired
    FileStorage fileStorage;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request, ModelMap model) {
        System.out.println("开始");
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // new Date()为获取当前系统时间
        String timestamp = df.format(new Date());
//        System.out.println(timestamp);
        String filePath = FilePath.CACHE.INITIAL+timestamp;
        //创建你要保存的文件的路径
//        String path = request.getSession().getServletContext().getRealPath("uploadfile");
        String path = request.getSession().getServletContext().getRealPath(filePath);
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
//        model.addAttribute("fileUrl", request.getContextPath() + "/uploadfile/"+ fileName);
        model.addAttribute("fileUrl", request.getContextPath() + filePath + fileName);
        return "redirect:/list.html?status=OK";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(value = "id") String id,
                         @RequestParam(value = "type") String type){

        String iniCacheDir = request.getSession().getServletContext().getRealPath(FilePath.CACHE.INITIAL);

        File iniCacheFile = new File(iniCacheDir);
        if(!iniCacheFile.exists()) {
            iniCacheFile.mkdirs();
        }else{
                System.out.println(iniCacheDir+" 目录已存在");
        }

        String fileName = id + "." + type;
        String fileLocation = iniCacheDir + fileName;
        File iniFile = new File(fileLocation);

        if(!iniFile.exists()) {
            try {
                fileStorage.get(iniCacheDir, id, id);//rename by id.type
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println(fileLocation+" 文件已存在");
        }

        BufferedInputStream bis;
        BufferedOutputStream bos;
        OutputStream fos;
        InputStream fis;

        try {
            fis = new FileInputStream(iniFile);
            bis = new BufferedInputStream(fis);
            fos = response.getOutputStream();
            bos = new BufferedOutputStream(fos);
            ServletUtils.setFileDownloadHeader(request, response, fileName);
            int byteRead = 0;
            byte[] buffer = new byte[8192];
            while((byteRead=bis.read(buffer,0,8192))!=-1){
                bos.write(buffer,0,byteRead);
            }

            bos.flush();
            fis.close();
            bis.close();
            fos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
