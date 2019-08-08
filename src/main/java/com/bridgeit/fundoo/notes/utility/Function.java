package com.bridgeit.fundoo.notes.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.exception.ResponseHelper;

@Component
public class Function {

	@Autowired
	private Response statusResponse;

	public String date() {

		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		return formattedDate;

	}

	public Response responseGenerator(int code, String msg, Object obj) {
		statusResponse = ResponseHelper.statusResponse(code, msg, obj);
		return statusResponse;

	}

}
