package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    FileService fileService;
    UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("uploadedFiles", fileService.getLoggedInUserFiles(userService.getCurrentLoggedInUser().getUserId()));
        return "home";
    }

    @PostMapping
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        User currentUser = userService.getCurrentLoggedInUser();

        fileService.createFile(new File(null,file.getOriginalFilename(),file.getContentType(),file.getSize(),currentUser.getUserId(),file.getBytes()));

        model.addAttribute("uploadedFiles", fileService.getLoggedInUserFiles(currentUser.getUserId()));

        return "home";
    }
}
