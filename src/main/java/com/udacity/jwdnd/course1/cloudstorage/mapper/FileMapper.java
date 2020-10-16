package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename,contenttype,filesize,userid,filedata) VALUES(#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT fileid,filename,contenttype,filesize,userid, null AS filedata FROM FILES WHERE userid=#{userId} ORDER BY fileid DESC")
    List<File> getUsersFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId}")
    File getFileById(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileId}")
    void deleteFileById(Integer fileId);
}

