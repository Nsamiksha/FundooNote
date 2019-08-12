package com.bridgeit.fundoo.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundoo.notes.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

	List<Label> findAllByUserId(long userId);

	Optional<Label> findByLabelTitle(String labelTitle);
}
