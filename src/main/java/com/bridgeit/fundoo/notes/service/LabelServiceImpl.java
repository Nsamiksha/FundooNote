package com.bridgeit.fundoo.notes.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.bridgeit.fundoo.notes.dto.LabelDto;
import com.bridgeit.fundoo.notes.exception.NoteException;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.model.Label;
import com.bridgeit.fundoo.notes.model.Note;
import com.bridgeit.fundoo.notes.repository.LabelRepository;
import com.bridgeit.fundoo.notes.repository.NoteRepository;
import com.bridgeit.fundoo.notes.utility.Function;
import com.bridgeit.fundoo.notes.utility.TokenGenerator;

@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private Function function;

	@Autowired
	private Environment environment;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Response create(LabelDto labelDto, String token) {

		if (function.validation(labelDto, token)) {

			throw new NoteException(environment.getProperty("status.label.exception"));
		}
		Label label;
		long userId = tokenGenerator.decryptToken(token);
		label = modelMapper.map(labelDto, Label.class);
		label.setUserId(userId);
		labelRepository.save(label);
		return function.responseGenerator(200, environment.getProperty("status.label.createdSuccessfull"), label);
	}

	@Override
	public Response labelsToNote(LabelDto labelDto, String token, Long noteId) {

		if (function.validation(labelDto, token)) {
			Optional<Label> label = labelRepository.findByLabelTitle(labelDto.getLabelTitle());
			System.out.println(label);
			Optional<Note> note1 = noteRepository.findById(noteId);
			System.out.println(note1);
			note1.get().getLabel().add(label.get());
			noteRepository.save(note1.get());
		}
		long userId = tokenGenerator.decryptToken(token);
		Label label;
		label = modelMapper.map(labelDto, Label.class);
		label.setUserId(userId);
		labelRepository.save(label);
		Optional<Note> note = noteRepository.findById(noteId);
		note.get().getLabel().add(label);
		noteRepository.save(note.get());

		Optional<Note> note1 = noteRepository.findById(noteId);

		return function.responseGenerator(200, environment.getProperty("status.label.createdSuccessfull"),
				note1.get().getLabel());
	}

	@Override
	public Response deleteLabel(String token, Long labelId) {
		long userId = tokenGenerator.decryptToken(token);

		List<Label> list = labelRepository.findAllByUserId(userId);

		for (Label label : list) {
			if (label.getLabelId() == labelId) {
				labelRepository.deleteById(labelId);
			}
		}

		List<Label> list1 = labelRepository.findAllByUserId(userId);

		return function.responseGenerator(200, environment.getProperty("status.label.DeletedSuccessfull"), list1);

	}

	@Override
	public Response updateLabel(String token, Long labelId, LabelDto labelDto) {
		long userId = tokenGenerator.decryptToken(token);
		List<Label> list = labelRepository.findAllByUserId(userId);
		for (Label label : list) {
			if (label.getLabelId() == labelId) {
				label.setLabelTitle(labelDto.getLabelTitle());
				labelRepository.save(label);
			}
		}
		return function.responseGenerator(200, environment.getProperty("status.label.UpdatedSuccessfull"), list);
	}

	@Override
	public Response getAll(String token) {

		long userId = tokenGenerator.decryptToken(token);

		List<Label> list = labelRepository.findAllByUserId(userId);

		return function.responseGenerator(200, environment.getProperty("status.label.DisplayedSuccessfull"), list);
	}

}
