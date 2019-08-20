package com.bridgeit.fundoo.notes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
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
	@ManyToMany(mappedBy = "label",cascade=CascadeType.ALL)
	private List<Note> notes = new ArrayList<Note>();

	public Label() {

	}

}
