package org.example.fraudverifier.user;

import java.util.UUID;

import org.example.fraudverifier.VerificationResult;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
class UserRegistrationVerificationService {

	VerificationResult verifyUser(UUID uuid, int age) {
		if (age < 18 || age > 99) {
			return VerificationResult.failed(uuid);
		}
		return VerificationResult.passed(uuid);
	}

}
