package com.bridgeit.fundoo.notes.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.bridgeit.fundoo.notes.dto.NoteDto;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.exception.ResponseHelper;
import com.bridgeit.fundoo.notes.exception.UserException;
import com.bridgeit.fundoo.notes.model.Note;
import com.bridgeit.fundoo.notes.repository.NoteRepository;
import com.bridgeit.fundoo.notes.utility.TokenGenerator;

@PropertySource("classpath:message.properties")
@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private Environment environment;

	@Autowired
	private Response statusResponse;

	@Override
	public Response create(NoteDto noteDto, String token) {

		if (noteDto.getDescription() == "" && noteDto.getTitle() == "") {
			
			throw new UserException(environment.getProperty("status.note.emptyfield"));
		}

		long id = tokenGenerator.decryptToken(token);

		ModelMapper modelMapper = new ModelMapper();
		Note note = modelMapper.map(noteDto, Note.class);

		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);

		note.setCreateDate(formattedDate);
		note.setId(id);
		noteRepository.save(note);
		statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.notes.createdSuccessfull"),
				note);
		return statusResponse;
	}

	@Override
	public Response trashUntrash(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);
		if (note.get().getId() == id) {
			if (note.get().isTrash()) {
				note.get().setTrash(false);
				noteRepository.save(note.get());
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.untrashed"),
						note.get().isTrash());
				return statusResponse;

			} else {
				note.get().setTrash(true);
				noteRepository.save(note.get());
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.trashed"),
						note.get().isTrash());
				return statusResponse;

			}
		} else {
			throw new UserException(environment.getProperty("status.note.trashError"));
		}

	}

	@Override
	public Response archiveUnarchive(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);
		if (note.get().getId() == id) {
			if (note.get().isArchive()) {
				note.get().setArchive(false);
				noteRepository.save(note.get());
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.unarchive"),
						note.get().isArchive());
				return statusResponse;

			} else {
				note.get().setArchive(true);
				noteRepository.save(note.get());
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.archive"),
						note.get().isArchive());
				return statusResponse;

			}
		} else {
			throw new UserException(environment.getProperty("status.note.archievederror"));
		}

	}

	@Override
	public Response pinUnpin(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);
		if (note.get().getId() == id) {
			if (note.get().isPin()) {
				note.get().setPin(false);
				noteRepository.save(note.get());
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.unpined"),
						note.get().isPin());

				return statusResponse;

			} else {
				note.get().setPin(true);
				noteRepository.save(note.get());
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.pined"),
						note.get().isPin());

				return statusResponse;

			}
		} else {
			throw new UserException(environment.getProperty("status.note.pinerror"));
		}

	}

	@Override
	public Response delete(Long noteId, String token) {

		long id = tokenGenerator.decryptToken(token);

		Optional<Note> note = noteRepository.findById(noteId);

		if (note.get().getId() == id) {
			if (note.get().isTrash()) {
				noteRepository.deleteById(noteId);
				statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("status.note.deleted"),
						note.get().isPin());

				return statusResponse;

			} else {
				statusResponse = ResponseHelper.statusResponse(500, environment.getProperty("status.note.noteDeleted "),
						note.get().isPin());

				return statusResponse;
			}
		} else {
			throw new UserException(environment.getProperty("status.note.noteDeleted"));
		}

	}

	@Override
	public Response getAll(String token) {

		long userId = tokenGenerator.decryptToken(token);

		List<Note> note = noteRepository.findAllById(userId);

		statusResponse = ResponseHelper.statusResponse(500, "ALL NOTES", note);

		return statusResponse;
	}

}
