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
@RequestMapping(value= "/api/mongo/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value= "/create")
    public String create(@RequestBody Note note) {
        logger.debug("Saving employees.");
        noteService.save(note);
        return "Note records created.";
    }

    @GetMapping(value= "/getall")
    public List<Note> getAll() {
        logger.debug("Getting all notes.");
        return noteService.findAll();
    }
    
    @GetMapping(value= "/getbyid/{note-id}")
    public Optional<Note> getById(@PathVariable(value= "note-id") int id) {
        logger.debug("Getting note with note-id= {}.", id);
        return noteService.findById(id);
    }

    @PutMapping(value= "/update/{employee-id}")
    public String update(@PathVariable(value= "note-id") int id, @RequestBody Note note) {
        logger.debug("Updating note with note-id= {}.", id);
        note.setID(id);
        noteService.updateNote(note);
        return "Note record for note-id= " + id + " updated.";
    }

    @DeleteMapping(value= "/delete/{note-id}")
    public String delete(@PathVariable(value= "employee-id") int id) {
        logger.debug("Deleting employee with employee-id= {}.", id);
        noteService.deleteById(id);
        return "Note record for note-id= " + id + " deleted.";
    }

    @DeleteMapping(value= "/deleteall")
    public String deleteAll() {
        logger.debug("Deleting all notes.");
        noteService.deleteAllNote();
        return "All notes records deleted.";
    }

}
