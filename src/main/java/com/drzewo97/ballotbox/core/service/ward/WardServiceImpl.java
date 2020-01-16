package com.drzewo97.ballotbox.core.service.ward;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
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
}
