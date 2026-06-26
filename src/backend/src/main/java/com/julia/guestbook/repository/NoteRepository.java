package com.julia.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.julia.guestbook.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

}
