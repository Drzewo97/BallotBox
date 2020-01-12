package com.drzewo97.ballotbox.core.service.ward;

import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
