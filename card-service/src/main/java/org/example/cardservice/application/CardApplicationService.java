package org.example.cardservice.application;

import java.util.UUID;

import org.example.cardservice.user.User;
import org.example.cardservice.user.UserServiceClient;
import org.example.cardservice.verification.VerificationApplication;
import org.example.cardservice.verification.VerificationResult;
import org.example.cardservice.verification.VerificationServiceClient;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
class CardApplicationService {

	private final UserServiceClient userServiceClient;
	private final VerificationServiceClient verificationServiceClient;

	public CardApplicationService(UserServiceClient userServiceClient,
			VerificationServiceClient verificationServiceClient) {
		this.userServiceClient = userServiceClient;
		this.verificationServiceClient = verificationServiceClient;
	}

	public CardApplication registerApplication(CardApplicationDto applicationDTO) {
		User user = userServiceClient.registerUser(applicationDTO.user).getBody();
		CardApplication application = new CardApplication(UUID.randomUUID(),
				user, applicationDTO.cardCapacity);
		if (User.Status.OK != user.getStatus()) {
			application.setApplicationResult(ApplicationResult.rejected());
			return application;
	}
		VerificationResult verificationResult = verificationServiceClient
				.verify(new VerificationApplication(application.getUuid(),
						application.getCardCapacity())).getBody();
		if (!VerificationResult.Status.VERIFICATION_PASSED
				.equals(verificationResult.status)) {
			application.setApplicationResult(ApplicationResult.rejected());
		}
		else {
			application.setApplicationResult(ApplicationResult.granted());
		}
		return application;
	}
}
