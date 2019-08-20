package com.bridgeit.fundoo.notes.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.fundoo.notes.dto.LabelDto;
import com.bridgeit.fundoo.notes.exception.Response;
import com.bridgeit.fundoo.notes.exception.ResponseHelper;
import com.bridgeit.fundoo.notes.model.Label;
import com.bridgeit.fundoo.notes.repository.LabelRepository;

@Component
public class Utility {

	@Autowired
	private Response statusResponse;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private LabelRepository labelRepository;

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

	public boolean validation(LabelDto labelDto, String token) {

		long userId = tokenGenerator.decryptToken(token);
		List<Label> list = labelRepository.findAllByUserId(userId);
		Stream<Label> labelName = list.stream().filter(line -> line.getLabelTitle().equals(labelDto.getLabelTitle()));
		System.out.println(labelName.toString());
		for (Label label : list) {
			if (label.getLabelTitle().equals(labelDto.getLabelTitle())) {
				return true;

			}
		}
		return false;

	}

}
