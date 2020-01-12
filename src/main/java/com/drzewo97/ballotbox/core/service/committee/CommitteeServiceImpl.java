package com.drzewo97.ballotbox.core.service.committee;

import com.drzewo97.ballotbox.core.model.committee.CommitteeRepository;
import com.drzewo97.ballotbox.core.model.committee.Committee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommitteeServiceImpl implements CommitteeService {
	
	@Autowired
	private CommitteeRepository committeeRepository;
	
	@Override
	public Boolean isCommitteeAdmin(String username, Long committeeId) {
		Set<Committee> adminsCommittees = committeeRepository.findAllByCommitteeAdminUsername(username);
		return adminsCommittees.stream().filter(committee -> committee.getId().equals(committeeId)).count() > 0;
	}
}
