package com.drzewo97.ballotbox.core.dto.electionvote;

import com.drzewo97.ballotbox.core.dto.candidatedto.CandidateDto;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectionVoteDto {

	private Set<Candidate> choices;
	
	private List<CandidateDto> preferences;
	
	public ElectionVoteDto() {
		choices = new HashSet<>();
		preferences = new ArrayList<>();
	}
	
	public Set<Candidate> getChoices() {
		return choices;
	}
	
	public void setChoices(Set<Candidate> choices) {
		this.choices = choices;
	}
	
	public List<CandidateDto> getPreferences() {
		return preferences;
	}
	
	public void setPreferences(List<CandidateDto> preferences) {
		this.preferences = preferences;
	}
}
