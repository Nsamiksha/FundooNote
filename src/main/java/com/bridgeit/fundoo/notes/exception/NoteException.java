package com.bridgeit.fundoo.notes.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NoteException extends RuntimeException{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		int code;
		String msg;
		 public NoteException(String msg)
		 {
			super(msg);
		 }
		 
		 public NoteException(int code, String msg)
		 {
			 super(msg);
			 this.code =code;
		 }
	}

