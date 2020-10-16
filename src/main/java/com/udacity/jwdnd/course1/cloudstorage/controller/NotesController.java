package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {
    private final NoteService noteService;
    private final UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/notes/write")
    public String handleNoteCreation(@ModelAttribute("noteForm") NoteForm noteForm, RedirectAttributes redirectAttrs) {
        if (noteForm.getNoteId() == null)
            noteService.createNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userService.getCurrentLoggedInUser().getUserId()));
        else
            noteService.updateNote(new Note(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription(), userService.getCurrentLoggedInUser().getUserId()));

        redirectAttrs.addFlashAttribute("activeTab", "#nav-notes");
        return "redirect:/home";
    }

    @GetMapping ("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttrs){
        noteService.deleteNoteById(noteId);

        redirectAttrs.addFlashAttribute("activeTab", "#nav-notes");
        return "redirect:/home";
    }

}
