package com.bridgeit.fundoo.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundoo.notes.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

	List<Collaborator> findByCollabUserId(long userId);

//	Optional<Collaborator> findByCollabUserIdAndnoteId(long collabUserId, long noteId);

//	List<Collaborator> findByCollabUserIdAndnoteId(long response, long noteId);

}
