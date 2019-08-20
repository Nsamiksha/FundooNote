package com.bridgeit.fundoo.notes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@Entity
@Table(name = "collaborator_data")
public class Collaborator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "NOTE_ID")
	private long noteId;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long collabId;
	private long collabUserId;
	@Column(name = "Owner")
	private long ownerId;
}
