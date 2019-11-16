package com.drzewo97.ballotbox.core.service.candidateservice;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.dto.candidatedto.CandidateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Boolean existsByName(String name) {
        return candidateRepository.existsByName(name);
    }

    @Override
    public void save(CandidateDto candidateDto) {
        Candidate candidate = new Candidate();
        candidate.setName(candidateDto.getName());

        candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        candidateRepository.findAll().forEach(candidates::add);

        return candidates;
    }
}
