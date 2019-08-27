package com.example.BasicNotesApi.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection="notes")
public class Note {
    @Id
    private long ID;
    private String name;
    private String content;
    private Date date;
    private String location;

    @Transient
    public static final String SEQUENCE_NAME = "notes_sequence";

    public Note(String name, String content, Date date, String location) {
        this.name = name;
        this.content = content;
        this.date = date;
        this.location = location;
    }

    public Note() {
    }


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
