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

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId} AND userid=#{userId}")
    File getFileById(Integer fileId, Integer userId);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileId} AND userid=#{userId}")
    void deleteFileById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    File getFileByName(String fileName, Integer userId);
}

