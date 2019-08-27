package com.example.BasicNotesApi;

import com.example.BasicNotesApi.Model.Note;
import com.example.BasicNotesApi.Repository.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class NoteServiceControllerTest extends AbstractTest {


    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class BasicNotesApiApplicationTests extends AbstractTest{
        @Autowired
        private NoteRepository noteRepository;

        @Override
        @Before
        public void setUp() {
            super.setUp();
        }
        @Test
        public void getNoteList() throws Exception {
            String uri = "/notes";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            Note[] notelist = super.mapFromJson(content, Note[].class);
            assertTrue(notelist.length > 0);
        }
        @Test
        public void createNote() throws Exception {
            String uri = "/notes";
            Date date = new Date();
            Note note = new Note("UpdateTest","lorem ipsum",date,"somewhere");
            note.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("26/08/2019"));
            String inputJson = super.mapToJson(note);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(201, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertEquals(content, "Note is created successfully");
        }
        @Test
        public void updateNote() throws Exception {
            Date date = new Date();
            int id = noteRepository.getNextId();
            Note note = new Note("UpdateTest","lorem ipsum",date,"somewhere");
            note.setID(id);
            noteRepository.save(note);
            String uri = "/note/"+id;
            note.setName("test-name");
            note.setContent("test-content");
            String inputJson = super.mapToJson(note);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertEquals(content, "Note is updated successsfully");
        }
        @Test
        public void deleteNote() throws Exception {
            Date date = new Date();
            int id = noteRepository.getNextId();
            Note note = new Note("DeleteTest","lorem ipsum",date,"somewhere");
            note.setID(id);
            noteRepository.save(note);
            String uri = "/note/"+id;
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertEquals(content, "Note is deleted successsfully");
        }
    }

}
