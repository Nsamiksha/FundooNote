 package com.bridgeit.fundoo.notes.controller;

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

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private INoteService iservice;

	// Create a new Note
	@PostMapping("/create")
	public Response createNote(@RequestBody NoteDto noteDto, @RequestHeader String token) {
//		System.out.println(noteDto);

		return iservice.create(noteDto, token);

	}

	// trash untrash
	@PutMapping("/trash/{noteId}")
	public Response trashUntrash(@PathVariable Long noteId, @RequestHeader String token) {
//		System.out.println(noteId);

		return iservice.trashUntrash(noteId, token);

	}

	// archive
	@PutMapping("/archive/{noteId}")
	public Response archiveUnarchive(@PathVariable Long noteId, @RequestHeader String token) {
//		System.out.println(noteId);

		return iservice.archiveUnarchive(noteId, token);

	}

	// pin unpin
	@PutMapping("/pin/{noteId}")
	public Response pinUnpin(@PathVariable Long noteId, @RequestHeader String token) {
//		System.out.println(noteId);

		return iservice.pinUnpin(noteId, token);

	}

	// delete
	@DeleteMapping("/delete/{noteId}")
	public Response delete(@PathVariable Long noteId, @RequestHeader String token) {
//		System.out.println(noteId);

		return iservice.delete(noteId, token);

	}


	// getall
	@GetMapping("/getall")
	public Response getAll(@RequestHeader String token) {
		

		return iservice.getAll(token);

	}
}
