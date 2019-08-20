package com.bridgeit.fundoo.notes.elasticsearch;

import java.util.List;

import com.bridgeit.fundoo.notes.model.Note;

public interface ISearch {

	String createNote(Note note) throws Exception;

	String updateNote(Note note) throws Exception;

	List<Note> findAll() throws Exception;

	String deleteNote(long noteId) throws Exception;

	

}
