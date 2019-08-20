package com.bridgeit.fundoo.notes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bridgeit.fundoo.notes.controller.NoteController;

@SpringBootApplication
public class FundooProjectNotesApplication {
	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	public static void main(String[] args) {
		SpringApplication.run(FundooProjectNotesApplication.class, args);

	}

}
