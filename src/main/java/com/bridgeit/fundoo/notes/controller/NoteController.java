package com.bridgeit.fundoo.notes.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundoo.notes.dto.NoteDto;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.service.INoteService;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	private INoteService iNoteService;

	/**
	 * Create a new Note
	 * 
	 * @param noteDto
	 * @param token
	 * @return
	 */
	@PostMapping
	@ApiOperation("CREATE A NOTE")
	public Response createNote(@RequestBody NoteDto noteDto, @RequestHeader String token) {

		return iNoteService.create(noteDto, token);

	}

	/**
	 * trash untrash
	 * 
	 * @param noteId
	 * @param token
	 * @return
	 */
	@PutMapping("/trash/{noteId}")
	@ApiOperation("TRASH UNTRASH")
	public Response trashUntrash(@PathVariable Long noteId, @RequestHeader String token) {

		return iNoteService.trashUntrash(noteId, token);

	}

	/**
	 * archive
	 * 
	 * @param noteId
	 * @param token
	 * @return
	 */
	@PutMapping("/archive/{noteId}")
	@ApiOperation("ARCHIEVE UNARCHIEVE")
	public Response archiveUnarchive(@PathVariable Long noteId, @RequestHeader String token) {

		return iNoteService.archiveUnarchive(noteId, token);

	}

	/**
	 * pin unpin
	 * 
	 * @param noteId
	 * @param token
	 * @return
	 */
	@PutMapping("/pin/{noteId}")
	@ApiOperation("PIN UNPIN")

	public Response pinUnpin(@PathVariable Long noteId, @RequestHeader String token) {

		return iNoteService.pinUnpin(noteId, token);

	}

	/**
	 * delete
	 * 
	 * @param noteId
	 * @param token
	 * @return
	 */
	@DeleteMapping("{noteId}")
	@ApiOperation("PERMANAT DELETE")

	public Response delete(@PathVariable Long noteId, @RequestHeader String token) {

		return iNoteService.delete(noteId, token);

	}

	/**
	 * getall
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping(value = "{token}")
	@ApiOperation("GET ALL NOTES")

	public Response getAll(@PathVariable String token) {

		return iNoteService.getAll(token);

	}

	/**
	 * @param noteId
	 * @param token
	 * @param noteDto
	 * @return
	 */
	@PutMapping("{noteId}/{color}")
	@ApiOperation("COLOR TO NOTE")

	public Response colorNote(@PathVariable long noteId, String color ,@RequestHeader String token, @RequestBody NoteDto noteDto) {
		return iNoteService.colorNote(noteId, token, color);

	}

	@PutMapping("/remainder/{noteId}/{remainder}")
	@ApiOperation("REMAINDER TO NOTE")

	public Response remainderToNote(@PathVariable long noteId, String remainder, @RequestHeader String token) {
		return iNoteService.remainderToNote(noteId, token, remainder);

	}
	
	@PutMapping("/collabNote/{noteId}/{email}")
	@ApiOperation("COLLABORATOR")

	public Response collaborateNote(@RequestHeader String token,@PathVariable long noteId,String email) {
		
		
		
		return iNoteService.collaborateNote(token,noteId,email);
		
		
	}
	
	@PutMapping("/update/{noteId}")
	@ApiOperation("UPDATE NOTE")

	public Response updateNote(@RequestHeader String token,@RequestBody NoteDto noteDto ,@PathVariable long noteId) {
		return iNoteService.updateNote(token,noteDto,noteId);
		
	}
	
	

}
