package com.bridgeit.fundoo.notes.exception;


public class ResponseHelper {
	public static Response statusResponse(int status, String message,  Object result ) {
		Response statusResponse = new Response();
		statusResponse.setMessage(message);
		statusResponse.setStatus(status);
		statusResponse.setResult(result);
		return statusResponse;
	}
}
