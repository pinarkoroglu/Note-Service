package com.example.BasicNotesApi;

import com.example.BasicNotesApi.Model.DatabaseSequence;
import com.example.BasicNotesApi.Model.Note;
import com.example.BasicNotesApi.Repository.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteServiceControllerTest extends AbstractTest{
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private MongoOperations mongoOperation;

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
        Note note = new Note("Test","test",date,"somewhere");
        note.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("26/08/2019"));
        String inputJson = super.mapToJson(note);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
    @Test
    public void updateNote() throws Exception {
        Date date = new Date();
        long id = maxId();
        Note note = new Note("UpdateTest","test",date,"somewhere");
        note.setID(id);
        noteRepository.save(note);
        String uri = "/notes/"+id;
        note.setName("test-name");
        note.setContent("test-content");
        String inputJson = super.mapToJson(note);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
    @Test
    public void deleteNote() throws Exception {
        Date date = new Date();
        long id = maxId();
        Note note = new Note("DeleteTest","test",date,"somewhere");
        note.setID(id);
        noteRepository.save(note);
        String uri = "/notes/"+id;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    private long maxId(){
        List<DatabaseSequence> note = mongoOperation.find(query(where("_id").is(Note.SEQUENCE_NAME)), DatabaseSequence.class);
        return note.get(0).getSeq();
    }
}
