package com.julia.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.julia.guestbook.entity.Assignee;

@Repository
public interface AssigneeRepository extends JpaRepository<Assignee, Integer>{

}
