package com.bridgeit.fundoo.notes.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.bridgeit.fundoo.notes.dto.NoteDto;
import com.bridgeit.fundoo.notes.exception.RecordNotFoundException;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.exception.NoteException;
import com.bridgeit.fundoo.notes.model.Note;
import com.bridgeit.fundoo.notes.repository.NoteRepository;
import com.bridgeit.fundoo.notes.utility.Function;
import com.bridgeit.fundoo.notes.utility.TokenGenerator;

@PropertySource("classpath:message.properties")
@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private Function function;

	@Autowired
	private Environment environment;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Response create(NoteDto noteDto, String token) {

		Note note;

		if (noteDto.getTitle() == null || noteDto.getDescription() == null) {

			throw new NoteException(environment.getProperty("status.note.emptyfield"));

		}

		if (noteDto.getTitle() != "" || noteDto.getDescription() != "") {

			long id = tokenGenerator.decryptToken(token);

			note = modelMapper.map(noteDto, Note.class);

			note.setCreateDate(function.date());
			note.setId(id);
			note = noteRepository.save(note);
			return function.responseGenerator(200, environment.getProperty("status.notes.createdSuccessfull"), note);

		}  else {

			throw new NoteException(environment.getProperty("status.note.trashError"));
		}

	}

	@Override
	public Response trashUntrash(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);

		if (note.get().getId() == id) {

			if (note.get().getNoteid() != noteId) {

				throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));
			}

			if (note.get().isTrash()) {

				note.get().setTrash(false);

				noteRepository.save(note.get());

				return function.responseGenerator(200, environment.getProperty("status.note.untrashed"),
						note.get().isTrash());

			} else {
				note.get().setTrash(true);
				noteRepository.save(note.get());

				return function.responseGenerator(200, environment.getProperty("status.note.trashed"),
						note.get().isTrash());

			}
		} else {
			throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));
		}

	}

	@Override
	public Response archiveUnarchive(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);

		if (note.get().getId() == id) {

			if (note.get().getNoteid() != noteId) {

				throw new RecordNotFoundException("Record Not Found");

			}
			if (note.get().isArchive()) {
				note.get().setArchive(false);
				noteRepository.save(note.get());

				return function.responseGenerator(200, environment.getProperty("status.note.unarchive"),
						note.get().isArchive());
			} else {
				note.get().setArchive(true);
				noteRepository.save(note.get());

				return function.responseGenerator(200, environment.getProperty("status.note.archive"),
						note.get().isArchive());
			}
		} else {
			throw new NoteException(environment.getProperty("status.note.archievederror"));
		}

	}

	@Override
	public Response pinUnpin(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);
		if (note.get().getId() == id) {

			if (note.get().getNoteid() != noteId) {

				throw new RecordNotFoundException("Record Not Found");

			}

			if (note.get().isPin()) {

				note.get().setPin(false);

				noteRepository.save(note.get());

				return function.responseGenerator(200, environment.getProperty("status.note.unpined"),
						note.get().isPin());
			} else {
				note.get().setPin(true);

				noteRepository.save(note.get());

				return function.responseGenerator(200, environment.getProperty("status.note.pined"),
						note.get().isPin());
			}
		} else {
			throw new NoteException(environment.getProperty("status.note.pinerror"));
		}

	}

	@Override
	public Response delete(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);

		if (note.get().getId() == id) {

			if (note.get().getNoteid() != noteId) {

				throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));

			}
			if (note.get().isTrash()) {
				noteRepository.deleteById(noteId);

				return function.responseGenerator(200, environment.getProperty("status.note.deleted"), noteId);

			} else {

				return function.responseGenerator(200, environment.getProperty("status.note.noteDeleted "), noteId);
			}
		} else {
			throw new NoteException(environment.getProperty("status.note.noteDeleted"));
		}

	}

	@Override
	public Response getAll(String token) {

		long userId = tokenGenerator.decryptToken(token);
		System.out.println(userId);

		List<Note> note = noteRepository.findAllById(userId);

		if (note.isEmpty()) {

			throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));

		}

		List<Note> list = noteRepository.findAllById(userId);

		return function.responseGenerator(200, "ALL NOTES", list);

	}

}
