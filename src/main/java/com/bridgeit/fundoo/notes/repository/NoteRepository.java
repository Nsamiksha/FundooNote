package com.bridgeit.fundoo.notes.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bridgeit.fundoo.notes.model.Note;


@Repository
public interface NoteRepository extends JpaRepository< Note, Long> {

	List<Note> findAllById(long userId);



	




}
