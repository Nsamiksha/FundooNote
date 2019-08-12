
package com.bridgeit.fundoo.notes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Notes_Labels")
	private List<Label> label = new ArrayList<>();;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public boolean isTrash() {
		return trash;
	}

	public boolean isPin() {
		return pin;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public long getNoteid() {
		return noteid;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setNoteid(long noteid) {
		this.noteid = noteid;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Note [noteid=" + noteid + ", id=" + id + ", title=" + title + ", description=" + description
				+ ", createDate=" + createDate + ", trash=" + trash + ", archive=" + archive + ", pin=" + pin
				+ ", label=" + label + "]";
	}

}
