package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note){
        return noteMapper.insertNote(note);
    }

    public int updateNote(Note note){
        return noteMapper.updateNote(note);
    }

    public List<Note> getLoggedInUserNotes(Integer userId){
        return noteMapper.getUsersNotes(userId);
    }

    public void deleteNoteById(Integer noteId){
        noteMapper.deleteNoteById(noteId);
    }
}
