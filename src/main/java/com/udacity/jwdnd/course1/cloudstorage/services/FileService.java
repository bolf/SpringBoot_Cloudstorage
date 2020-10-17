package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
    }

    public int createFile(File file){
        return fileMapper.insert(file);
    }

    public List<File> getLoggedInUserFiles(Integer userId){
        return fileMapper.getUsersFiles(userId);
    }

    public File getFileById(Integer fileId, Integer userId) {
        return fileMapper.getFileById(fileId, userId);
    }

    public void deleteFileById(Integer fileId, Integer userId) {
        fileMapper.deleteFileById(fileId, userId);
    }

    public boolean fileExists(String filename, Integer userId){
        File file = fileMapper.getFileByName(filename, userId);
        return file != null;
    }

}
