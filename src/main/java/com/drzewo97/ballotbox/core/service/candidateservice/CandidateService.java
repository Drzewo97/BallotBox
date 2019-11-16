package com.drzewo97.ballotbox.core.service.candidateservice;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.dto.candidatedto.CandidateDto;

import java.util.List;

public interface CandidateService {
    Boolean existsByName(String name);
    void save(CandidateDto candidateDto);
    List<Candidate> findAllCandidates();
}
