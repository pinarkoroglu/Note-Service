package com.example.BasicNotesApi;

import com.example.BasicNotesApi.Model.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class NoteServiceControllerTest extends AbstractTest {


    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class BasicNotesApiApplicationTests extends AbstractTest{
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
            String uri = "/note";
            Note note = new Note();
            note.setID(3);
            note.setName("Test");
            note.setContent("this is a test note.");
            note.setLocation("Istanbul");
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
            String uri = "/note/2";
            Note note = new Note();
            note.setName("Test2");
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
            String uri = "/note/2";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertEquals(content, "Note is deleted successsfully");
        }
    }

}
