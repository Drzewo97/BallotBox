package com.drzewo97.ballotbox.core.model.wardprotocol;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;

import javax.persistence.*;

@MappedSuperclass
public abstract class WardProtocolBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Poll poll;
	
	@ManyToOne
	private Ward ward;
	
	private Integer votersAuthorizedCount;
	
	private Integer ballotsReceived;
	private Integer ballotsGiven;
	private Integer ballotsRemained;
	private String reasonForBallotsSumMiscalculation;
	
	private Integer ballotsTakenFromBox;
	private String reasonForBallotsTakenMiscalculation;
	
	private Integer ballotsInvalid;
	private Integer ballotsValid;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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
	
	public Integer getVotersAuthorizedCount() {
		return votersAuthorizedCount;
	}
	
	public void setVotersAuthorizedCount(Integer votersAuthorizedCount) {
		this.votersAuthorizedCount = votersAuthorizedCount;
	}
	
	public Integer getBallotsReceived() {
		return ballotsReceived;
	}
	
	public void setBallotsReceived(Integer ballotsReceived) {
		this.ballotsReceived = ballotsReceived;
	}
	
	public Integer getBallotsGiven() {
		return ballotsGiven;
	}
	
	public void setBallotsGiven(Integer ballotsGiven) {
		this.ballotsGiven = ballotsGiven;
	}
	
	public Integer getBallotsRemained() {
		return ballotsRemained;
	}
	
	public void setBallotsRemained(Integer ballotsRemained) {
		this.ballotsRemained = ballotsRemained;
	}
	
	public String getReasonForBallotsSumMiscalculation() {
		return reasonForBallotsSumMiscalculation;
	}
	
	public void setReasonForBallotsSumMiscalculation(String reasonForBallotsSumMiscalculation) {
		this.reasonForBallotsSumMiscalculation = reasonForBallotsSumMiscalculation;
	}
	
	public Integer getBallotsTakenFromBox() {
		return ballotsTakenFromBox;
	}
	
	public void setBallotsTakenFromBox(Integer ballotsTakenFromBox) {
		this.ballotsTakenFromBox = ballotsTakenFromBox;
	}
	
	public String getReasonForBallotsTakenMiscalculation() {
		return reasonForBallotsTakenMiscalculation;
	}
	
	public void setReasonForBallotsTakenMiscalculation(String reasonForBallotsTakenMiscalculation) {
		this.reasonForBallotsTakenMiscalculation = reasonForBallotsTakenMiscalculation;
	}
	
	public Integer getBallotsInvalid() {
		return ballotsInvalid;
	}
	
	public void setBallotsInvalid(Integer ballotsInvalid) {
		this.ballotsInvalid = ballotsInvalid;
	}
	
	public Integer getBallotsValid() {
		return ballotsValid;
	}
	
	public void setBallotsValid(Integer ballotsValid) {
		this.ballotsValid = ballotsValid;
	}
}
