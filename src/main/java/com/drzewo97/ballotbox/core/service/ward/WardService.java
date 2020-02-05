package com.drzewo97.ballotbox.core.service.ward;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.wardprotocol.WardProtocolBase;

import java.util.Set;

public interface WardService {
	Boolean isWardAdmin(String username, Integer wardId);
	Set<Ward> findAllEligibleForPollProtocol(Poll poll);
	Boolean isProtocolValid(WardProtocolBase wardProtocol);
}
