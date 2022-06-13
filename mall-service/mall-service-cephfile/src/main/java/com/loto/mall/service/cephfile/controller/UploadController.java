package com.loto.mall.service.cephfile.controller;

import com.loto.mall.service.cephfile.ceph.FileHandler;
import com.loto.mall.util.common.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
public class UploadController {
    @Autowired
    private FileHandler fileHandler;

    /**
     * 文件上传
     */
    @PostMapping(value = "/upload")
    public RespResult upload(MultipartFile file) throws IOException {
        // 调用
        fileHandler.upload(file.getOriginalFilename(), file.getBytes());
        return RespResult.ok();
    }

    /**
     * 文件下载
     */
    @GetMapping(value = "/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response) throws IOException {
        // 调用
        byte[] buffer = fileHandler.download(filename);
        ServletOutputStream os = response.getOutputStream();

        os.write(buffer);
        os.flush();
        os.close();
    }
}
