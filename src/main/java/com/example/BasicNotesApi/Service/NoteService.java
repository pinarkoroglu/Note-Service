package com.example.BasicNotesApi.Service;

import com.example.BasicNotesApi.Model.Note;
import com.example.BasicNotesApi.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(long id) {
        return noteRepository.findById(id);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    public void updateNote(Note note) {
        noteRepository.save(note);
    }

    public void deleteAllNote() {
        noteRepository.deleteAll();
    }

}
