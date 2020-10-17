package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId} ORDER BY noteid DESC")
    List<Note> getUsersNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId} AND userid=#{userId}")
    void deleteNoteById(Integer noteId, Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid=#{noteId} AND userid=#{userId}")
    Note getNoteById(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId} AND userid=#{userId}")
    int updateNote(Note note);
}