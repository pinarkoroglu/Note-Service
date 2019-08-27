package com.example.BasicNotesApi.Repository;

import com.example.BasicNotesApi.Model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note,Long> {
}
