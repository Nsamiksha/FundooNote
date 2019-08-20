package com.bridgeit.fundoo.notes.service;

import com.bridgeit.fundoo.notes.dto.NoteDto;
import com.bridgeit.fundoo.notes.exception.Response;

public interface INoteService {

	Response create(NoteDto noteDto, String token);

	Response trashUntrash(Long noteId, String token);

	Response archiveUnarchive(Long noteId, String token);

	Response pinUnpin(Long noteId, String token);

	Response delete(Long noteId, String token);

	Response getAll(String token);

	Response colorNote(long noteId, String token, String color);

	Response remainderToNote(long noteId, String token, String remainder);

	Response collaborateNote(String token,  long noteId, String email);

	Response updateNote(String token, NoteDto noteDto, long noteId);

}
