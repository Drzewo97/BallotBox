package com.drzewo97.ballotbox.core.model.vote;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;

public interface IVote {
	Candidate getMostPreferredCandidate();
}
