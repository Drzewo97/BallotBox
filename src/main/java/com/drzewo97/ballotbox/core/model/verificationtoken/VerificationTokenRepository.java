package com.drzewo97.ballotbox.core.model.verificationtoken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);
}
