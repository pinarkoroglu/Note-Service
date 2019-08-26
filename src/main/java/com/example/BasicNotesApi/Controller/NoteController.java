package com.example.BasicNotesApi.Controller;

import com.example.BasicNotesApi.Model.Note;
import com.example.BasicNotesApi.Service.NoteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value= "/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value= "/")
    public void save(@RequestBody Note note) {
        logger.debug("Saving note.");
        noteService.save(note);
    }

    @GetMapping(value= "/")
    public List<Note> findAll() {
        logger.debug("Getting all notes.");
        return noteService.findAll();
    }

    @GetMapping(value= "/{id}")
    public Optional<Note> findById(@PathVariable(value= "id") int id) {
        logger.debug("Getting note with note-id= {}.", id);
        return noteService.findById(id);
    }

    @PutMapping(value= "/{id}")
    public void update(@PathVariable(value= "id") int id, @RequestBody Note note) {
        logger.debug("Updating note with note-id= {}.", id);
        note.setID(id);
        noteService.updateNote(note);
    }

    @DeleteMapping(value= "/{id}")
    public void delete(@PathVariable(value= "id") int id) {
        logger.debug("Deleting note with note-id= {}.", id);
        noteService.deleteById(id);
    }

    @DeleteMapping(value= "/deleteall")
    public void deleteAll() {
        logger.debug("Deleting all notes.");
        noteService.deleteAllNote();
    }

}
