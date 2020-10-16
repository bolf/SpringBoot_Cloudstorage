package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    UserService userService;
    FileService fileService;
    NoteService noteService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String getHome(Model model){
        Integer currUserId = userService.getCurrentLoggedInUser().getUserId();
        model.addAttribute("files", fileService.getLoggedInUserFiles(currUserId));
        model.addAttribute("notes", noteService.getLoggedInUserNotes(currUserId));
        if(model.asMap().get("activeTab") == null)
            model.addAttribute("activeTab","#nav-files");
        return "home";
    }
}
