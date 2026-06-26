package com.julia.guestbook.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.boot.jackson.autoconfigure.JacksonProperties.Json;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julia.guestbook.entity.Assignee;
import com.julia.guestbook.entity.Comment;
import com.julia.guestbook.entity.Note;
import com.julia.guestbook.service.NoteService;
import com.julia.guestbook.service.WeatherService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/api")
@RestController
public class GuestBookController {

    private Logger logger = Logger.getLogger(GuestBookController.class.getName());

    private final NoteService noteService;
    private final WeatherService weatherService;

    GuestBookController(WeatherService weatherService, NoteService noteService) {
        this.weatherService = weatherService;
        this.noteService = noteService;
    }

    @GetMapping("/")
    public String home(Model model,
            @RequestParam(required = false) Integer sortedNoteId,
            @RequestParam(defaultValue = "false") boolean ascending) {
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("sortedNoteId", sortedNoteId);
        model.addAttribute("ascending", ascending);

        Map<Integer, List<Comment>> commentsByNote = new LinkedHashMap<>();
        for (Note note : noteService.findAllNotes()) {
            List<Comment> comments = noteService.findCommentsByNoteId(note.getId());
            if (sortedNoteId != null && sortedNoteId == note.getId() && ascending) {
                comments = comments.reversed();
            }
            commentsByNote.put(note.getId(), comments);
        }
        model.addAttribute("commentsByNote", commentsByNote);

        return "index";
    }

    @GetMapping("/entries")
    public List<Note> getEntries(Model model) {
        
        return noteService.findAllNotes();
    }

    @PostMapping("/entries/new")
    public String saveNote(Model model, @Valid @ModelAttribute Note note, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.log(java.util.logging.Level.WARNING, "Validierungsfehler: " + bindingResult.getAllErrors());
            return "/entries/new";
        }
        if (note != null && note.getAssignee() != null) {
            Note noteToSave = new Note();
            noteToSave = note;
            Assignee assigneeToSave = new Assignee();
            assigneeToSave = note.getAssignee();
            noteToSave.setAssignee(assigneeToSave);
            model.addAttribute("note", noteToSave);
            noteToSave.setDate(LocalDateTime.now());
            model.addAttribute("note", noteToSave);
            noteService.save(noteToSave.getAssignee());
            noteService.save(noteToSave);
            return "redirect:/saved/200";
        } else {
            return "redirect:/status/404";
        }
    }

    @ModelAttribute("currentWeatherIcon")
    public String currentWeatherIcon() {
        return weatherService.getCurrentWeatherIcon("Berlin");
    }

    @ModelAttribute("currentWeather")
    public String currentWeather() {
        return weatherService.getCurrentWeather("Berlin");
    }

    @ModelAttribute("notes")
    public List<Note> getAllNotes() {
        return noteService.findAllNotes();
    }

    @ModelAttribute("timestamp")
    public LocalDateTime timestamp() {
        return LocalDateTime.now();
    }

    @DeleteMapping("/entries/{id}/delete")
    public String deleteNote(@PathVariable int id, Model model) {
        if (noteService.findNoteById(id) != null) {
            noteService.deleteNoteById(id);

            return "redirect:/status/200";
        } else {

            return "redirect:/status/404";
        }
    }

    @GetMapping("/entries/edit/{id}")
    public String editNote(@PathVariable int id, Model model) {
        Optional<Note> optionalNote = Optional.ofNullable(noteService.findNoteById(id));
        if (optionalNote.isPresent() && optionalNote.get().getAssignee() != null) {
            model.addAttribute("note", optionalNote.get());
            return "edit";
        } else {

            return "redirect:/status/404";
        }
    }

    @PostMapping("/entries/edit/{id}")
    public String editGivenNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {
        if (note == null || note.getId() <= 0) {
            return "redirect:/status/400";
        }

        Note existingNote = noteService.findNoteById(note.getId());
        if (existingNote == null || existingNote.getAssignee() == null) {
            return "redirect:/status/404";
        }

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        existingNote.setContent(note.getContent());
        existingNote.setDate(LocalDateTime.now());
        noteService.save(existingNote);
        return "redirect:/status/200";
    }

    @GetMapping("/status/{code}")
    public String showErrorMessage(Model model,
            @PathVariable Integer code) {
        model.addAttribute("code", code);
        return "status";
    }

    @GetMapping("/entries/{id}")
    public String oneNote(@PathVariable int id, Model model) {
        Optional<Note> optionalNote = Optional.ofNullable(noteService.findNoteById(id));
        if (optionalNote.isPresent()) {
            model.addAttribute("note", optionalNote.get());
            return "one-note";
        } else {
            return "redirect:/status/404";
        }
    }

    @GetMapping("/entries/{id}/new-comment")
    public String writeComment(Model model, @PathVariable Integer id) {
        Optional<Note> note = Optional.ofNullable(noteService.findNoteById(id));
        if (note.isPresent()) {
            Note existingNote = note.get();
            Assignee assignee = new Assignee();
            Comment comment = new Comment();
            comment.setAssignee(assignee);
            comment.setNote(existingNote);
            model.addAttribute("assignee", assignee);
            model.addAttribute("comment", comment);
            model.addAttribute("note", existingNote);
            model.addAttribute("noteId", existingNote.getId());
            return "new-comment";
        } else {
            return "redirect:/status/404";
        }
    }

    @PostMapping("/entries/{id}/new-comment")
    public String saveComment(Model model, @Valid @ModelAttribute("comment") Comment comment,
            BindingResult bindingResult, @RequestParam("noteId") Integer noteId) {
        if (bindingResult.hasErrors()) {
            return "new-comment";
        }
        Optional<Note> noteOptional = Optional.ofNullable(noteService.findNoteById(noteId));
        // TO DO
        // Setting the comment to an given note
        if (comment == null || comment.getAssignee() == null) {
            return "new-comment";
        }
        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            Comment commentToAdd = comment;
            Assignee assignee = commentToAdd.getAssignee();
            noteService.save(assignee);
            commentToAdd.setAssignee(assignee);
            commentToAdd.setDate(LocalDateTime.now());
            commentToAdd.setNote(note);
            note.addComment(commentToAdd);
            noteService.save(commentToAdd);
            return "redirect:/one-note/" + noteId;
        } else {
            return "redirect:/status/404";
        }
    }

    @GetMapping("/entries/{id}/delete-comment/{commentId}")
    public String deleteComment(Model model, @PathVariable Integer id, @PathVariable Integer commentId) {
        if (noteService.findCommentById(commentId) != null) {
            noteService.deleteCommentById(commentId);
            return "redirect:/status/200";
        } else {
            return "redirect:/status/404";

        }
    }

    @GetMapping("/entries/{id}/edit-comment/{commentId}")
    public String editComment(Model model, @PathVariable Integer id, @PathVariable Integer commentId) {
        Optional<Comment> commentOptional = noteService.findCommentById(commentId);
        if (commentOptional.isPresent()) {
            model.addAttribute("comment", commentOptional.get());
            return "edit-comment";
        } else {
            return "redirect:/status/404";
        }
    }

    @PostMapping("/entries/{id}/edit-comment/{commentId}")
    public String editCommentPostForm(@Valid @ModelAttribute("comment") Comment comment, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-comment";
        }
        Optional<Comment> existingOptional = noteService.findCommentById(comment.getId());
        if (existingOptional.isPresent()) {
            Comment existingComment = existingOptional.get();
            existingComment.setContent(comment.getContent());
            existingComment.setDate(LocalDateTime.now());
            noteService.save(existingComment);
            return "redirect:/status/200";
        } else {
            return "redirect:/status/404";
        }
    }

}
