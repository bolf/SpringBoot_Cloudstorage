package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
class FileSizeExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeException(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("toastMsg", "File is too big");
        redirectAttributes.addFlashAttribute("toastType", "error");
        return "redirect:/home";
    }
}