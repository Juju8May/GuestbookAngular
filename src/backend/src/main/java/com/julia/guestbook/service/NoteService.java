package com.julia.guestbook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.julia.guestbook.entity.Assignee;
import com.julia.guestbook.entity.Comment;
import com.julia.guestbook.entity.Note;
import com.julia.guestbook.repository.AssigneeRepository;
import com.julia.guestbook.repository.CommentRepository;
import com.julia.guestbook.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AssigneeRepository assigneeRepository;



    public List<Note> findAllNotes() {
        List<Note> notes = noteRepository.findAll();
        notes.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));
        return notes;
    }

    public List<Assignee> findAllAssignees() {
        List<Assignee> assignees = assigneeRepository.findAll();
        assignees.sort((a1, a2) -> a1.getLastName().compareTo(a2.getLastName()));
        return assignees;
    }

    public Assignee findAssigneeById(int id) {
        Assignee optionalAssignee = assigneeRepository.getReferenceById(id);
        return optionalAssignee;
    }

    public Note findNoteById(int id) {
        Note optionalNote = noteRepository.getReferenceById(id);
        return optionalNote;
    }

    public void save(Note note) {
        noteRepository.save(note);
    }

    public void deleteNoteById(int id) {
        noteRepository.deleteById(id);
    }

    public void save(Assignee assignee) {
        assigneeRepository.save(assignee);
    }

    public void deleteAssigneeById(int id) {
        assigneeRepository.deleteById(id);
    }

    public List<Comment> findCommentsByNoteId(int noteId) {
        List<Comment> commentList = commentRepository.findByNoteId(noteId);
        commentList.sort((c1, c2) -> c2.getDate().compareTo(c1.getDate()));
        return commentList;

    }

    public Optional<Comment> findCommentById(int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment;
        } else {
            return Optional.empty();
        }
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteCommentById(int id) {
        commentRepository.deleteById(id);
    }
}
