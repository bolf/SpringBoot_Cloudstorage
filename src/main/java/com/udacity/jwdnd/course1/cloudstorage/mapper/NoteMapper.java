package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid=#{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId} ORDER BY noteid DESC")
    List<Note> getUsersNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    void deleteNoteById(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE noteid=#{noteId}")
    public Note getNoteById(Integer noteId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId} AND userid=#{userId}")
    public int updateNote(Note note);
}