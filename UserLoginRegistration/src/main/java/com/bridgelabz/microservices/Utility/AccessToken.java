package com.bridgelabz.microservices.Utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

@Component
public class AccessToken {

	private String secret = "name";

	public String generateAccessToken(String userid) {

		String token = null;
		try {
			token = JWT.create().withClaim("userid", userid).sign(Algorithm.HMAC256(secret));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}

	public String verifyAccessToken(String token) {

		Verification verification;
		String userid = null;
		try {
			verification = JWT.require(Algorithm.HMAC256(secret));
			JWTVerifier jwtVerifier = verification.build();
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			decodedJWT.getAlgorithm();
			Claim claim = decodedJWT.getClaim("userid");
			userid = claim.asString();

		} catch (SignatureVerificationException e) {
			e.printStackTrace();
		} catch (JWTDecodeException e) {
			System.out.println("Invalid Token");
		}
		return userid;
	}
}
