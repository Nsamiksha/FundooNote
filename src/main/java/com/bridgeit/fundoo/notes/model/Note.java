
package com.bridgeit.fundoo.notes.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "fundoo_notes_data")
@Entity
public class Note implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NOTE_ID")
	private long noteid;

	@Column(name = "USERID")
	private long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DATE")
	private String createDate;

	@Column(name = "TRASH")
	private boolean trash;

	@Column(name = "ARCHIVE")
	private boolean archive;

	@Column(name = "PINUNPIN")
	private boolean pin;

	@Column(name = "Color")
	private String color;

	@Column(name = "Remainder")
	private LocalDateTime remainder;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Notes_Labels")
	private List<Label> label = new ArrayList<>();

	public Note() {

	}


}
