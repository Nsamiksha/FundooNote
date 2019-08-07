package com.bridgeit.fundoo.notes.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

@Component
public class TokenGenerator {
	
		
		  static String Secret_Key = "Tbckjsghuiwkioip";

		public String createToken(long id) {
			
			try {

				Algorithm algorithm = Algorithm.HMAC256(Secret_Key);

				String token = JWT.create().withClaim("id", id ).sign(algorithm);
				return token;
			} catch (JWTCreationException exception) {
				exception.printStackTrace();

			}
			return null;
		}

		public long decryptToken(String token) {
			long id;
			// for verification algorithm
			Verification verification = JWT.require(Algorithm.HMAC256(Secret_Key));
			JWTVerifier jwtverifier = verification.build();
			// to decode token
			DecodedJWT decodedjwt = jwtverifier.verify(token);
			// retrive data
			Claim claim = decodedjwt.getClaim("id");
			 id = claim.asLong();
			return id;

		}
	}


