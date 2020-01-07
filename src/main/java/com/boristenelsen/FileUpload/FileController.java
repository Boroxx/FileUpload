package com.boristenelsen.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
/*Um bilder anzeigen zu lassen einfach mit src link das upgeloadete Bild referenzieren*/
@Controller
public class FileController {

    @Autowired
    FileStorageService fileStorageService;

    private List<FileModel> files;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("imageList",fileStorageService.produceBase64EncodedFileList());
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("uploadfile")MultipartFile multipartFile){
        fileStorageService.storeFile(multipartFile);
        return "redirect:/";

    }

}
