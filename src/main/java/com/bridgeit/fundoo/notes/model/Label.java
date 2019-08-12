package com.bridgeit.fundoo.notes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "fundoo_label")
public class Label implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LABEL_ID")
	private long labelId;
	
	@Column(name = "USER_ID")
	private long userId;

	@Column(name = "LABEL_TITLE")
	private String labelTitle;

//	@JsonIgnore
	@ManyToMany(mappedBy = "label")
	private List<Note> notes = new ArrayList();

	public long getUserId() {
		return userId;
	}

	public String getLabelTitle() {
		return labelTitle;
	}

	public long getLabelId() {
		return labelId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Label [userId=" + userId + ", labelTitle=" + labelTitle + ", labelId=" + labelId + ", notes=" + notes
				+ "]";
	}

}
