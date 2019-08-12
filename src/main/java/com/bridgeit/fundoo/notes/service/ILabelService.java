package com.bridgeit.fundoo.notes.service;

import com.bridgeit.fundoo.notes.dto.LabelDto;
import com.bridgeit.fundoo.notes.exception.Response;

public interface ILabelService {

	

	Response labelsToNote(LabelDto labelDto, String token, Long noteId);

	Response create(LabelDto labelDto, String token);

	Response deleteLabel(String token, Long labelId);

	Response updateLabel(String token, Long labelId, LabelDto labelDto);

	Response getAll(String token);

}
