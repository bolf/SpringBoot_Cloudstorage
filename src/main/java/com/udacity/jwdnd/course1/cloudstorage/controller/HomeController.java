package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping(value={"/", "/home"})
    public String getHome(Model model){
        Integer currUserId = null;
        User currUser = userService.getCurrentLoggedInUser();
        if(currUser != null) {
            currUserId = userService.getCurrentLoggedInUser().getUserId();
        }else{
            return "redirect:/login";
        }

        model.addAttribute("files", fileService.getLoggedInUserFiles(currUserId));
        model.addAttribute("notes", noteService.getLoggedInUserNotes(currUserId));
        model.addAttribute("credentials", credentialService.getLoggedInUserCredentials(currUserId));
        if(model.asMap().get("activeTab") == null)
            model.addAttribute("activeTab","#nav-files");
        return "home";
    }
}
