package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential){
        return credentialMapper.insertCredential(credential);
    }

    public List<Credential> getLoggedInUserCredentials(Integer currUserId) {
        List<Credential> credentialList = credentialMapper.getUsersCredential(currUserId);
        for (Credential cred: credentialList) {
            cred.setPassword(encryptionService.decryptValue(cred.getPassword(),cred.getKey()));
        }
        return credentialList;
    }

    public int updateCredential(Credential credential){
        return credentialMapper.updateCredential(credential);
    }

    public void deleteCredentialById(Integer credentialId, Integer userId) {
        credentialMapper.deleteCredentialById(credentialId, userId);
    }
}
