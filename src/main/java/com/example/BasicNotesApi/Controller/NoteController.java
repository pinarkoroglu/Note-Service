package com.example.BasicNotesApi.Controller;

import com.example.BasicNotesApi.Model.Note;
import com.example.BasicNotesApi.Service.NoteService;

import com.example.BasicNotesApi.Service.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value="/notes")
    public ResponseEntity save(@RequestBody Note note) {
        logger.debug("Saving note.");
        note.setID(sequenceGenerator.generateSequence(Note.SEQUENCE_NAME));
        noteService.save(note);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value="/notes")
    public List<Note> findAll() {
        logger.debug("Getting all notes.");
        return noteService.findAll();
    }

    @GetMapping(value= "/notes/{id}")
    public Optional<Note> findById(@PathVariable(value= "id") long id) {
        logger.debug("Getting note with note-id= {}.", id);
        return noteService.findById(id);
    }

    @PutMapping(value= "/notes/{id}")
    public void update(@PathVariable(value= "id") long id, @RequestBody Note note) {
        logger.debug("Updating note with note-id= {}.", id);
        note.setID(id);
        noteService.updateNote(note);
    }

    @DeleteMapping(value= "/notes/{id}")
    public void delete(@PathVariable(value= "id") int id) {
        logger.debug("Deleting note with note-id= {}.", id);
        noteService.deleteById(id);
    }


}
