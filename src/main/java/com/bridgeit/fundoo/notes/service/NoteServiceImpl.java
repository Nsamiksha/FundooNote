package com.bridgeit.fundoo.notes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgeit.fundoo.notes.dto.NoteDto;
import com.bridgeit.fundoo.notes.elasticsearch.ISearch;
import com.bridgeit.fundoo.notes.exception.RecordNotFoundException;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.exception.NoteException;
import com.bridgeit.fundoo.notes.model.Collaborator;
import com.bridgeit.fundoo.notes.model.Note;
import com.bridgeit.fundoo.notes.repository.CollaboratorRepository;
import com.bridgeit.fundoo.notes.repository.NoteRepository;
import com.bridgeit.fundoo.notes.utility.Utility;

import com.bridgeit.fundoo.notes.utility.TokenGenerator;

@PropertySource("classpath:message.properties")
@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private Utility utility;

	@Autowired
	private Environment environment;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	@Autowired
	private Collaborator collaborator;

	@Autowired
	private ISearch search;

	/**
	 * create note
	 */
	@Override
	public Response create(NoteDto noteDto, String token) {

		Note note;

		if (noteDto.getTitle() == "" || noteDto.getDescription() == "") {

			throw new NoteException(environment.getProperty("status.note.emptyfield"));

		}

		if (noteDto.getTitle() != "" || noteDto.getDescription() != "") {

			long id = tokenGenerator.decryptToken(token);

			note = modelMapper.map(noteDto, Note.class);

			note.setCreateDate(utility.date());
			note.setId(id);
			note.setColor("white");
			noteRepository.save(note);
			try {
				search.createNote(note);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return utility.responseGenerator(200, environment.getProperty("status.notes.createdSuccessfull"), note);

		} else {

			throw new NoteException(environment.getProperty("status.note.emptyfield"));
		}

	}

	/**
	 * trash untrash status
	 */
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

				return utility.responseGenerator(200, environment.getProperty("status.note.untrashed"),
						note.get().isTrash());

			} else {
				note.get().setTrash(true);
				noteRepository.save(note.get());

				return utility.responseGenerator(200, environment.getProperty("status.note.trashed"),
						note.get().isTrash());

			}
		} else {
			throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));
		}

	}

	/**
	 * archive unarchieve note
	 */
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

				return utility.responseGenerator(200, environment.getProperty("status.note.unarchive"),
						note.get().isArchive());
			} else {
				note.get().setArchive(true);
				noteRepository.save(note.get());

				return utility.responseGenerator(200, environment.getProperty("status.note.archive"),
						note.get().isArchive());
			}
		} else {
			throw new NoteException(environment.getProperty("status.note.archievederror"));
		}

	}

	/**
	 * pin unpin note
	 */
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

				return utility.responseGenerator(200, environment.getProperty("status.note.unpined"),
						note.get().isPin());
			} else {
				note.get().setPin(true);

				noteRepository.save(note.get());

				return utility.responseGenerator(200, environment.getProperty("status.note.pined"), note.get().isPin());
			}
		} else {
			throw new NoteException(environment.getProperty("status.note.pinerror"));
		}

	}

	/**
	 * permanat delete note
	 */
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
				try {
					search.deleteNote(noteId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return utility.responseGenerator(200, environment.getProperty("status.note.deleted"), noteId);

			} else {

				return utility.responseGenerator(501, environment.getProperty("status.note.noteDeleted"), noteId);
			}
		} else {
			throw new NoteException(environment.getProperty("status.note.noteDeleted"));
		}

	}

	/**
	 * get all notes
	 */
	@Override
	public Response getAll(String token) {

		long userId = tokenGenerator.decryptToken(token);
		System.out.println(userId);

		List<Note> note = noteRepository.findAllById(userId);

		if (note.isEmpty()) {

			throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));

		}

		List<Note> list = noteRepository.findAllById(userId);
		List<Collaborator> collab = collaboratorRepository.findByCollabUserId(userId);
		for (Collaborator collaborator : collab) {
			long id = collaborator.getNoteId();
			list.add(noteRepository.findByNoteid(id));
		}

		return utility.responseGenerator(200, "ALL NOTES", list);

	}

	/**
	 * color to note
	 */
	@Override
	public Response colorNote(long noteId, String token,String color) {

		long userId = tokenGenerator.decryptToken(token);
		List<Note> note = noteRepository.findAllById(userId);
		if (note.isEmpty()) {

			throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));

		}
		for (Note note2 : note) {
			if (note2.getNoteid() == noteId) {
				note2.setColor(color);
				noteRepository.save(note2);
				return utility.responseGenerator(200, environment.getProperty("status.note.color"), noteId);

			}

		}
		return utility.responseGenerator(200, environment.getProperty("status.saveError"), noteId);

	}

	/**
	 * remainder
	 */
	@Override
	public Response remainderToNote(long noteId, String token, String remainder) {
		long userId = tokenGenerator.decryptToken(token);
		long hours = 7;
		Note note1 = null;
		LocalDateTime today = LocalDateTime.now();
		List<Note> note = noteRepository.findAllById(userId);
		if (note.isEmpty()) {

			throw new RecordNotFoundException(environment.getProperty("status.note.recordnotfound"));

		}
		for (Note note2 : note) {
			if (note2.getNoteid() == noteId) {
				switch (remainder) {
				case "today":
					LocalDateTime todayLater = LocalDateTime.now().plusHours(hours);
					System.out.println(todayLater);
					note2.setRemainder(todayLater);
					noteRepository.save(note2);
					return utility.responseGenerator(200, environment.getProperty("status.note.remainder.today"),
							todayLater);
				case "tomorrow":
					LocalDateTime tomorrow = today.plusDays(1);
					System.out.println(tomorrow);
					note2.setRemainder(tomorrow);
					noteRepository.save(note2);
					return utility.responseGenerator(200, environment.getProperty("status.note.remainder.tomorrow"),
							tomorrow);
				case "weekely":
					LocalDateTime week = today.plusWeeks(1);
					System.out.println(week);
					note2.setRemainder(week);
					noteRepository.save(note2);
					return utility.responseGenerator(200, environment.getProperty("status.note.remainder.weekly"),
							week);
				case "monthly":
					LocalDateTime monthly = today.plusMonths(1);
					System.out.println(monthly);
					note2.setRemainder(monthly);
					noteRepository.save(note2);
					return utility.responseGenerator(200, environment.getProperty("status.note.remainder.monthly"),
							monthly);
				case "yearly":
					LocalDateTime yearly = today.plusYears(1);
					System.out.println(yearly);
					note2.setRemainder(yearly);
					noteRepository.save(note2);
					return utility.responseGenerator(200, environment.getProperty("status.note.remainder.yearly"),
							yearly);
				}

			}
		}
		return null;
	}

	/**
	 * collaborator
	 */
	@Override
	public Response collaborateNote(String token, long noteId, String email) {

		long userId = tokenGenerator.decryptToken(token);

		String url = "http://localhost:9090/api/v1/users/byEmail/" + email;
		long collabUserId = restTemplate.getForObject(url, long.class);
		if (collabUserId == 0) {
			throw new NoteException("user not found");
		}
		Optional<Note> note = noteRepository.findById(noteId);
		if (!note.isPresent()) {
			throw new NoteException("note not present");
		}

//		Optional<Collaborator> collablist = collaboratorRepository.findByCollabUserIdAndnoteId(collabUserId, noteId);
//		if (!collablist.isEmpty()) {
//			throw new NoteException("note already collaborated");
//		}
		collaborator.setCollabUserId(collabUserId);
		collaborator.setNoteId(noteId);
		collaborator.setOwnerId(userId);
		collaboratorRepository.save(collaborator);

		return utility.responseGenerator(200, "note collaborated successfully", collaborator);
	}

	/**
	 * update note
	 */
	@Override
	public Response updateNote(String token, NoteDto noteDto, long noteId) {
		long userId = tokenGenerator.decryptToken(token);
		Note note = noteRepository.findByIdAndNoteid(userId, noteId);
		if (!noteDto.getTitle().isEmpty()) {
			note.setTitle(noteDto.getTitle());
			noteRepository.save(note);
			try {
				search.updateNote(note);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return utility.responseGenerator(200, "note updated successfully", note);

		} else {
			throw new NoteException("add title");
		}
	}

}
