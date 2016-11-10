package info.puton.product.smartsearch.controller;

import info.puton.product.smartsearch.constant.FilePath;
import info.puton.product.smartsearch.model.ActionResult;
import info.puton.product.smartsearch.service.FileHandler;
import info.puton.product.smartsearch.service.FileStorage;
import info.puton.product.smartsearch.service.IConvertService;
import info.puton.product.smartsearch.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Pauline on 16/10/8.
 */


@Controller
@RequestMapping(value="/file")
public class FileController {

    @Autowired
    FileHandler fileHandler;

    @Autowired
    FileStorage fileStorage;

    @Autowired
    IConvertService convertService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "file") MultipartFile file,
            HttpServletRequest request) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        // new Date()为获取当前系统时间
        String timestamp = df.format(new Date());
//        System.out.println(timestamp);
        String filePath = FilePath.UPLOAD+timestamp;
        //创建你要保存的文件的路径
//        String path = request.getSession().getServletContext().getRealPath("uploadfile");
        String path = request.getSession().getServletContext().getRealPath(filePath);
        //获取该文件的文件名
        String fileName = file.getOriginalFilename();

//        System.out.println(path);
//        System.out.println(fileName);
        File targetFile = new File(path, fileName);
        System.out.println(targetFile);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 临时保存
        try {
            file.transferTo(targetFile);
            //全文处理
            Map additional = new HashMap();
            fileHandler.handleFile(targetFile.getPath(), additional);
            targetFile.delete();
            return "redirect:/admin.html?status=ok";
        } catch (Exception e) {
            e.printStackTrace();
            targetFile.delete();
            return "redirect:/admin.html?status=error";
        }

    }

    public File prepareIniCache(String iniCacheDir, String id, String type){
        File iniCacheFile = new File(iniCacheDir);
        if(!iniCacheFile.exists()) {
            iniCacheFile.mkdirs();
        }else{
            System.out.println(iniCacheDir+" 目录已存在");
        }

        String iniFileName = id + "." + type;
        String iniFileLocation = iniCacheDir + iniFileName;
        File iniFile = new File(iniFileLocation);

        if(!iniFile.exists()) {
            try {
                fileStorage.getFile(iniCacheDir, id, id);//rename by id.type
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println(iniFileLocation+" 文件已存在");
        }

        return iniFile;

    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(value = "id") String id,
                         @RequestParam(value = "type") String type){

        String iniCacheDir = request.getSession().getServletContext().getRealPath(FilePath.CACHE.INITIAL);

        File iniFile = prepareIniCache(iniCacheDir, id, type);

        BufferedInputStream bis;
        BufferedOutputStream bos;
        OutputStream fos;
        InputStream fis;

        try {
            fis = new FileInputStream(iniFile);
            bis = new BufferedInputStream(fis);
            fos = response.getOutputStream();
            bos = new BufferedOutputStream(fos);
            String fileName = id + "." + type;
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

    @ResponseBody
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public Map preview(HttpServletRequest request,
                         @RequestParam(value = "id") String id,
                         @RequestParam(value = "type") String type){

        Map result = new HashMap();

        String pdfCacheDir = request.getSession().getServletContext().getRealPath(FilePath.CACHE.PDF);
        File pdfCacheFile = new File(pdfCacheDir);
        if(!pdfCacheFile.exists()) {
            pdfCacheFile.mkdirs();
        }else{
            System.out.println(pdfCacheDir+" 目录已存在");
        }


        String pdfFileName = id + "." + "pdf";
        String pdfFileLocation = pdfCacheDir + pdfFileName;
        File pdfFile = new File(pdfFileLocation);

        if(!pdfFile.exists()) {
            String iniCacheDir = request.getSession().getServletContext().getRealPath(FilePath.CACHE.INITIAL);
            File iniFile = prepareIniCache(iniCacheDir, id, type);
            try {
                convertService.doc2pdf(iniFile.getPath(), pdfFileLocation);
            } catch (ConnectException e) {
                result.put("status","error");
                result.put("description","openoffice连接失败，请检查服务是否启动");
                e.printStackTrace();
            } catch (Exception e) {
                result.put("status","error");
                result.put("description","文件类型不兼容");
            }
        }else {
            System.out.println(pdfFileLocation+" 文件已存在");
        }

        result.put("status","ok");
        String pdfPath = FilePath.CACHE.PDF + id + ".pdf";
        result.put("pdfPath", pdfPath);

        return result;

    }

    @ResponseBody
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ActionResult init(){
        try {
            fileHandler.initFile();
            return new ActionResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ActionResult(false);
        }
    }
}
