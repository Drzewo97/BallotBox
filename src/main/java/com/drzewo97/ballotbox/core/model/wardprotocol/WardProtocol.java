package com.drzewo97.ballotbox.core.model.wardprotocol;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;

import javax.persistence.*;

@Entity
public class WardProtocol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Poll poll;
	
	@ManyToOne
	private Ward ward;
	
	private Long votersAuthorizedCount;
	
	private Long ballotsReceived;
	private Long ballotsGiven;
	private Long ballotsRemained;
	private String reasonForBallotsSumMiscalculation;
	
	private Long ballotsTakenFromBox;
	private String reasonForBallotsTakenMiscalculation;
	
	private Long ballotsInvalid;
	private Long ballotsValid;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Poll getPoll() {
		return poll;
	}
	
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
	public Ward getWard() {
		return ward;
	}
	
	public void setWard(Ward ward) {
		this.ward = ward;
	}
	
	public Long getVotersAuthorizedCount() {
		return votersAuthorizedCount;
	}
	
	public void setVotersAuthorizedCount(Long votersAuthorizedCount) {
		this.votersAuthorizedCount = votersAuthorizedCount;
	}
	
	public Long getBallotsReceived() {
		return ballotsReceived;
	}
	
	public void setBallotsReceived(Long ballotsReceived) {
		this.ballotsReceived = ballotsReceived;
	}
	
	public Long getBallotsGiven() {
		return ballotsGiven;
	}
	
	public void setBallotsGiven(Long ballotsGiven) {
		this.ballotsGiven = ballotsGiven;
	}
	
	public Long getBallotsRemained() {
		return ballotsRemained;
	}
	
	public void setBallotsRemained(Long ballotsRemained) {
		this.ballotsRemained = ballotsRemained;
	}
	
	public String getReasonForBallotsSumMiscalculation() {
		return reasonForBallotsSumMiscalculation;
	}
	
	public void setReasonForBallotsSumMiscalculation(String reasonForBallotsSumMiscalculation) {
		this.reasonForBallotsSumMiscalculation = reasonForBallotsSumMiscalculation;
	}
	
	public Long getBallotsTakenFromBox() {
		return ballotsTakenFromBox;
	}
	
	public void setBallotsTakenFromBox(Long ballotsTakenFromBox) {
		this.ballotsTakenFromBox = ballotsTakenFromBox;
	}
	
	public String getReasonForBallotsTakenMiscalculation() {
		return reasonForBallotsTakenMiscalculation;
	}
	
	public void setReasonForBallotsTakenMiscalculation(String reasonForBallotsTakenMiscalculation) {
		this.reasonForBallotsTakenMiscalculation = reasonForBallotsTakenMiscalculation;
	}
	
	public Long getBallotsInvalid() {
		return ballotsInvalid;
	}
	
	public void setBallotsInvalid(Long ballotsInvalid) {
		this.ballotsInvalid = ballotsInvalid;
	}
	
	public Long getBallotsValid() {
		return ballotsValid;
	}
	
	public void setBallotsValid(Long ballotsValid) {
		this.ballotsValid = ballotsValid;
	}
}
