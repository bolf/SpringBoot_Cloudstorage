package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES(#{url},#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId} ORDER BY credentialId DESC")
    List<Credential> getUsersCredential(Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    void deleteCredentialById(Integer noteId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    Credential getCredentialById(Integer noteId);

    @Update("UPDATE CREDENTIALS SET url=#{url},username=#{username},password=#{password},key=#{key} WHERE credentialid=#{credentialId} AND userid=#{userId}")
    int updateCredential(Credential credential);
}
