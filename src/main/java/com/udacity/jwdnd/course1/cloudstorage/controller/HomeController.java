package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class HomeController {
    FileService fileService;
    UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("uploadedFiles", fileService.getLoggedInUserFiles(userService.getCurrentLoggedInUser().getUserId()));
        return "home";
    }

    @PostMapping("/home")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        User currentUser = userService.getCurrentLoggedInUser();

        fileService.createFile(new File(null,file.getOriginalFilename(),file.getContentType(),file.getSize(),currentUser.getUserId(),file.getBytes()));

        model.addAttribute("uploadedFiles", fileService.getLoggedInUserFiles(currentUser.getUserId()));

        return "home";
    }

    @GetMapping("/home/getFile/{fileId}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Integer fileId){
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/home/deleteFile/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){
        fileService.deleteFileById(fileId);

        model.addAttribute("uploadedFiles", fileService.getLoggedInUserFiles(userService.getCurrentLoggedInUser().getUserId()));
        return "redirect:/home";
    }
}
