package com.drzewo97.ballotbox.core.service.ward;

import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.model.wardprotocol.WardProtocolBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class WardServiceImpl implements WardService {
	
	@Autowired
	private WardRepository wardRepository;
	
	@Override
	public Boolean isWardAdmin(String username, Integer wardId) {
		Set<Ward> adminsWards = wardRepository.findAllByWardAdminUsername(username);
		return adminsWards.stream().filter(ward -> ward.getId().equals(wardId)).count() > 0;
	}
	
	/**
	 * Get all wards that could and should provide protocols for this poll
	 * @param poll
	 * @return
	 */
	@Override
	public Set<Ward> findAllEligibleForPollProtocol(Poll poll) {
		Set<Ward> returner = new HashSet<>();
		
		switch (poll.getPollScope()){
			case COUNTRY:
				returner.addAll(wardRepository.findAllByDistrictCountry(poll.getCountry()));
				break;
			case DISTRICT:
				returner.addAll(wardRepository.findAllByDistrict(poll.getDistrict()));
				break;
			case WARD:
				returner.add(poll.getWard());
				break;
		}
		
		return returner;
	}
	
	@Override
	public Boolean isProtocolValid(WardProtocolBase wardProtocol) {
		// null check
		if(wardProtocol.getBallotsTakenFromBox() == null || wardProtocol.getBallotsGiven() == null ||
		wardProtocol.getBallotsValid() == null || wardProtocol.getBallotsInvalid() == null ||
		wardProtocol.getBallotsReceived() == null || wardProtocol.getBallotsRemained() == null ||
		wardProtocol.getVotersAuthorizedCount() == null){
			return false;
		}
		
		if(wardProtocol instanceof CandidatesVotesCountWardProtocol){
			if(((CandidatesVotesCountWardProtocol)wardProtocol).getCandidateProtocolVotesAsList().stream().anyMatch(c -> c.getVotesCount() == null)){
				return false;
			}
		}
		
		if(wardProtocol.getBallotsGiven() < wardProtocol.getVotersAuthorizedCount()){
			// more authorized votes than given ballots
			return false;
		}
		
		//sum of given to voters and remained ballots smaller than ballots receiver
		if((wardProtocol.getBallotsGiven() + wardProtocol.getBallotsRemained()) != wardProtocol.getBallotsReceived()){
			// no reason for miscalculation
			if(wardProtocol.getReasonForBallotsSumMiscalculation().isBlank()){
				return false;
			}
		}
		
		// number of ballots given isn't equal to number of ballots taken from ballot box
		if(wardProtocol.getBallotsGiven() != wardProtocol.getBallotsTakenFromBox()){
			// no reason for miscalculation
			if(wardProtocol.getReasonForBallotsTakenMiscalculation().isBlank()){
				return false;
			}
		}
		
		if(wardProtocol.getBallotsInvalid() + wardProtocol.getBallotsValid() != wardProtocol.getBallotsTakenFromBox()){
			return false;
		}
		
		return true;
	}
}
