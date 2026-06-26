package com.julia.guestbook.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "assignees")
public class Assignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "Der Vorname darf nicht leer sein.")
    @Size(min = 3, max = 50, message = "Der Vorname sollte zwischen 3 und 50 Zeichen lang sein.")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Der Nachname darf nicht leer sein.")
    @Size(min = 3, max = 50, message = "Der Nachname sollte zwischen 3 und 50 Zeichen lang sein.")
    private String lastName;

    @Column(name = "email")
    @Size(min = 7, max = 30, message = "Die E-Mail sollte 7 bis 30 Zeichen lang sein.")
    @Email
    private String email;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Assignee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
