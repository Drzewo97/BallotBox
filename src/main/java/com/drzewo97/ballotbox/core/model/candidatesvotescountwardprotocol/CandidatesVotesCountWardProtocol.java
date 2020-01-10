package com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol;

import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotes;
import com.drzewo97.ballotbox.core.model.wardprotocol.WardProtocolBase;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CandidatesVotesCountWardProtocol extends WardProtocolBase {

	@OneToMany
	Set<CandidateProtocolVotes> candidateProtocolVotes;
	
	@Transient
	List<CandidateProtocolVotes> candidateProtocolVotesAsList;
	
	public Set<CandidateProtocolVotes> getCandidateProtocolVotes() {
		return candidateProtocolVotes;
	}
	
	public void setCandidateProtocolVotes(Set<CandidateProtocolVotes> candidateProtocolVotes) {
		this.candidateProtocolVotes = candidateProtocolVotes;
		this.candidateProtocolVotesAsList = new ArrayList<>(candidateProtocolVotes);
	}
	
	public List<CandidateProtocolVotes> getCandidateProtocolVotesAsList(){
		return candidateProtocolVotesAsList;
	}
	
	public void setCandidateProtocolVotesAsList(List<CandidateProtocolVotes> candidateProtocolVotesAsList){
		this.candidateProtocolVotesAsList = candidateProtocolVotesAsList;
		this.candidateProtocolVotes = new HashSet<>(candidateProtocolVotesAsList);
	}
}
