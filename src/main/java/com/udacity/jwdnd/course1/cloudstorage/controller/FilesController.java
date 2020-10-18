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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class FilesController {
    private final FileService fileService;
    private final UserService userService;

    public FilesController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/files/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("activeTab", "#nav-files");

        if(file.getOriginalFilename().isEmpty()){
            redirectAttrs.addFlashAttribute("toastMsg", "Select a file to upload");
            redirectAttrs.addFlashAttribute("toastType", "error");
            return "redirect:/home";
        }

        User currentUser = userService.getCurrentLoggedInUser();
        if(fileService.fileExists(file.getOriginalFilename(),currentUser.getUserId())){
            redirectAttrs.addFlashAttribute("toastMsg", "File \"".concat(Objects.requireNonNull(file.getOriginalFilename())).concat("\" has already been uploaded earlier. \n"));
            redirectAttrs.addFlashAttribute("toastType", "error");
            return "redirect:/home";
        }

        try {
            fileService.createFile(new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), currentUser.getUserId(), file.getBytes()));
        }catch (IOException e){
            redirectAttrs.addFlashAttribute("toastMsg", "Could not upload file =(");
            redirectAttrs.addFlashAttribute("toastType", "error");
        }

        redirectAttrs.addFlashAttribute("toastMsg", "File uploaded successfully");
        redirectAttrs.addFlashAttribute("toastType", "success");
        return "redirect:/home";
    }

    @GetMapping("/files/get/{fileId}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Integer fileId){
        User currentUser = userService.getCurrentLoggedInUser();
        File file = fileService.getFileById(fileId,currentUser.getUserId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, RedirectAttributes redirectAttrs){
        fileService.deleteFileById(fileId,userService.getCurrentLoggedInUser().getUserId());

        redirectAttrs.addFlashAttribute("activeTab", "#nav-files");
        redirectAttrs.addFlashAttribute("toastMsg", "File deleted");
        redirectAttrs.addFlashAttribute("toastType", "warning");
        return "redirect:/home";
    }
}
