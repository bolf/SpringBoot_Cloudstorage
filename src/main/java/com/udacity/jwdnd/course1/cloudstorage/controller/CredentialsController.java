package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CredentialsController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    private Map<String,String> encrypt(String strForEncryption){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("key",encodedKey);
        returnMap.put("pass",encryptionService.encryptValue(strForEncryption, encodedKey));
        return returnMap;
    }

    @PostMapping("/credentials/write")
    public String handleCredentialWrite(@ModelAttribute("credentialForm") CredentialForm credentialForm, RedirectAttributes redirectAttrs) {
        Map<String, String> passMap = encrypt(credentialForm.getPassword());
        Credential credential = new Credential(credentialForm.getCredentialId(),
                credentialForm.getUrl(),
                credentialForm.getUsername(),
                passMap.get("key"),
                passMap.get("pass"),
                userService.getCurrentLoggedInUser().getUserId());

        if (credentialForm.getCredentialId() == null) {
            credentialService.createCredential(credential);
        } else
            credentialService.updateCredential(credential);

        redirectAttrs.addFlashAttribute("activeTab", "#nav-credentials");
        redirectAttrs.addFlashAttribute("toastMsg", "Credential written successfully");
        redirectAttrs.addFlashAttribute("toastType", "success");
        return "redirect:/home";
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttrs){
        credentialService.deleteCredentialById(credentialId,userService.getCurrentLoggedInUser().getUserId());

        redirectAttrs.addFlashAttribute("activeTab", "#nav-credentials");
        redirectAttrs.addFlashAttribute("toastMsg", "Credential deleted");
        redirectAttrs.addFlashAttribute("toastType", "warning");
        return "redirect:/home";
    }
}
