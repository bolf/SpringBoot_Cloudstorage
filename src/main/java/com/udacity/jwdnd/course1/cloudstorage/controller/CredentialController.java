package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
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
    public String handleCredentialCreation(@ModelAttribute("credentialForm") CredentialForm credentialForm, RedirectAttributes redirectAttrs){
        if(credentialForm.getCredentialId() == null) {
            Map<String,String> passMap = encrypt(credentialForm.getPassword());
            credentialService.createCredential(new Credential(null,
                    credentialForm.getUrl(),
                    credentialForm.getUsername(),
                    passMap.get("key"),
                    passMap.get("pass"),
                    userService.getCurrentLoggedInUser().getUserId()));
        }
//        else
//            credentialService.createCredential(new Credential());

        redirectAttrs.addFlashAttribute("activeTab", "#nav-credentials");
        return "redirect:/home";
    }
}
