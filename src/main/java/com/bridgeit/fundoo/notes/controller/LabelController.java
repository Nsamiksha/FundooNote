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

import com.bridgeit.fundoo.notes.dto.LabelDto;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.service.ILabelService;

/**
 * @author samiksha NOTE APIs
 */
@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private ILabelService iLabelService;

	/**
	 * @param labelDto
	 * @param token
	 * @return
	 */
	@PostMapping()
	public Response create(@RequestBody LabelDto labelDto, @RequestHeader String token) {

		return iLabelService.create(labelDto, token);

	}

	/**
	 * @param noteId
	 * @param labelDto
	 * @param token
	 * @return
	 */
	@PostMapping("{noteId}")
	public Response labelToNote(@PathVariable Long noteId, @RequestBody LabelDto labelDto,
			@RequestHeader String token) {

		return iLabelService.labelsToNote(labelDto, token, noteId);

	}

	/**
	 * @param labelId
	 * @param token
	 * @return
	 */
	@DeleteMapping("/delete/{labelId}")
	public Response deleteLabel(@PathVariable Long labelId, @RequestHeader String token) {

		return iLabelService.deleteLabel(token, labelId);

	}

	/**
	 * @param labelId
	 * @param labelDto
	 * @param token
	 * @return
	 */
	@PutMapping("{labelId}")
	public Response updateLabel(@PathVariable Long labelId, @RequestBody LabelDto labelDto,
			@RequestHeader String token) {

		return iLabelService.updateLabel(token, labelId, labelDto);

	}

	// getall
	/**
	 * @param token
	 * @return
	 */
	@GetMapping
	public Response getAll(@RequestHeader String token) {

		return iLabelService.getAll(token);

	}

}
