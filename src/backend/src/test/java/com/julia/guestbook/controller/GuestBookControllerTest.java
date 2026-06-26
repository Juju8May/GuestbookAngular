package com.julia.guestbook.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.julia.guestbook.entity.Assignee;
import com.julia.guestbook.entity.Note;
import com.julia.guestbook.service.NoteService;

@SpringBootTest
public class GuestBookControllerTest {

    @Autowired
    private GuestBookController controller;

    @MockitoBean
    NoteService service;

    @Test
    void contextLoads_Successfully() {
        assertThat(controller).isNotNull();
    }

    // unit test for home method
    @Test
    void indexHtml_addedTimestampToModel_Successfully() {
        // arrange
        Model model = Mockito.mock(Model.class);

        // act
        String viewName = controller.home(model, 1, false);

        // assert
        verify(model).addAttribute(eq("timestamp"), any(LocalDateTime.class));
        verify(model).addAttribute(eq("ascending"), eq(false));    
        assertThat(viewName).isEqualTo("index");
    }

    @Test
    void newNoteTest_addingNoteToModel() {
        // arrange
        Model model = Mockito.mock(Model.class);

        // act
        String viewName = controller.newNote(model);

        // assert
        verify(model).addAttribute(eq("note"), any(Note.class));
        assertEquals(viewName, "new");
    }

    @Test
    void saveNoteTest_Successfully() {
        // arrange
        Model model = Mockito.mock(Model.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Note note = new Note();
        Assignee assignee = new Assignee();
        assignee.setFirstName("Test First Name");
        assignee.setLastName("Test Last Name");
        assignee.setId(1);
        note.setAssignee(assignee);
        note.setDate(LocalDateTime.now());
        note.setContent("Test message");
        note.setId(1);

        // act
        controller.saveNote(model, note, bindingResult);

        // assert
        verify(service, times(1)).save(note.getAssignee());
        verify(service, times(1)).save(note);
        
    }

    @Test
    void saveNoteTest_Failed_WhenNoteIsNull() {
        // act
        Model model = Mockito.mock(Model.class);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        String viewName = controller.saveNote(model, null, bindingResult);

        // assert
        assertEquals("redirect:/status/404", viewName);
    }

    @Test
    void savedTest_ReturnsSavedView() {
        // arrange
        Model model = Mockito.mock(Model.class);
        Note note = new Note();
        Assignee assignee = new Assignee();
        assignee.setFirstName("Test First Name");
        assignee.setLastName("Test Last Name");
        assignee.setId(1);
        note.setContent("Test content");
        note.setDate(LocalDateTime.now());
        note.setAssignee(new Assignee());
        note.setId(1);
        note.setAssignee(assignee);

        // act
        String viewName = controller.saveNote(model, note, Mockito.mock(BindingResult.class));

        // assert
        assertEquals("redirect:/saved/200", viewName);
    }

    @Test
    void getAllNotesTest_ReturnsListOfNotes() {
        // arrange
        when(service.findAllNotes()).thenReturn(List.of(new Note(), new Note()));
        
        // act
        List<Note> notes = controller.getAllNotes();

        // assert
        assertEquals(2, notes.size());
        assertNotNull(notes);
    }
    
    @Test
    void getTimeStampTest_NotNull() {
        // act
        LocalDateTime timestamp = controller.timestamp();

        // assert
        assertNotNull(timestamp);
    }

    @Test
    void deleteNoteTest_Successfully() {
        // arrange
        int noteId = 1;
        when(service.findNoteById(noteId)).thenReturn(new Note());
        Model model = Mockito.mock(Model.class);
        
        // act
        controller.deleteNote(noteId, model);
        // assert
        verify(service, times(1)).deleteNoteById(noteId);
    }

    @Test
    void deleteNoteTest_Failed() {
        // arrange
        int noteId = 99;
        Model model = Mockito.mock(Model.class);

        // act
        controller.deleteNote(noteId, model);

        // assert
        assertNull(service.findNoteById(noteId));
    }

    @Test
    void editNoteTest_findNoteById_Successfully() {
        // arrange
        Model model = Mockito.mock(Model.class);
        int noteId = 1;
        Note note = new Note();
        Assignee assignee = new Assignee();
        assignee.setFirstName("Test First Name");
        assignee.setLastName("Test Last Name");
        assignee.setId(1);
        note.setContent("Test content");
        note.setDate(LocalDateTime.now());
        note.setAssignee(new Assignee());
        note.setId(1);
        note.setAssignee(assignee);

        // act
        when(service.findNoteById(noteId)).thenReturn(note);
        String viewName = controller.editNote(noteId, model);

        // assert
        assertEquals("edit", viewName);
        verify(model, times(1)).addAttribute("note", note);
    }

    @Test
    void editNoteTest_findNoteById_Failes() {
        // arrange
        Model model = Mockito.mock(Model.class);
        int noteId = 99;

        // act
        when(service.findNoteById(noteId)).thenReturn(null);
        String viewName = controller.editNote(noteId,model);

        // assert
        assertEquals("redirect:/status/404", viewName);
    }

    @Test
    void editGivenNoteTest_ReturnsEdit_WhenContentValidationFails() {
        // arrange
        Model model = Mockito.mock(Model.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        Note existingNote = new Note();
        existingNote.setId(1);
        Assignee assignee = new Assignee();
        assignee.setFirstName("Test First Name");
        assignee.setLastName("Test Last Name");
        existingNote.setAssignee(assignee);
        existingNote.setContent("kurz");

        when(service.findNoteById(1)).thenReturn(existingNote);

        // act
        String viewName = controller.editGivenNote(existingNote, bindingResult, model);

        // assert
        assertEquals("edit", viewName);
        assertEquals("kurz", existingNote.getContent());
        assertEquals(assignee, existingNote.getAssignee());
        verify(service, times(0)).save(any(Note.class));
    }

    @Test
    void editGivenNoteTest_RedirectsSaved_WhenContentValidationSucceeds() {
        // arrange
        Model model = Mockito.mock(Model.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        Note formNote = new Note();
        formNote.setId(1);
        formNote.setContent("Dies ist ein ausreichend langer Inhalt.");

        Note existingNote = new Note();
        existingNote.setId(1);
        Assignee assignee = new Assignee();
        assignee.setFirstName("Test First Name");
        assignee.setLastName("Test Last Name");
        existingNote.setAssignee(assignee);

        when(service.findNoteById(1)).thenReturn(existingNote);

        // act
        String viewName = controller.editGivenNote(formNote, bindingResult, model);

        // assert
        assertEquals("redirect:/status/200", viewName);
        assertEquals("Dies ist ein ausreichend langer Inhalt.", existingNote.getContent());
        verify(service, times(1)).save(existingNote);
    }

    @Test
    void showErrorPageTest_ReturnsErrorView() {
        // arrange
        Model model = Mockito.mock(Model.class);
        int code = 400;

        // act
        String viewName = controller.showErrorMessage(model, code);

        // assert
        assertEquals("status", viewName);
    }

}
