package com.drzewo97.ballotbox.votingmachine.service.converter.voter;

import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.service.converter.Converter;
import com.drzewo97.ballotbox.votingmachine.dto.VoterDto;
import org.springframework.stereotype.Service;

@Service
public class VoterToUserConverter implements Converter<VoterDto, User> {
	@Override
	public User convert(VoterDto voterDto) {
		User user = new User();
		
		user.setUsername(voterDto.getPassword());
		user.setFirstName(voterDto.getFirstName());
		user.setSurname(voterDto.getSurname());
		user.setPassword(voterDto.getPassword());
		user.setCitizenId(voterDto.getCitizenId());
		user.setEmail(voterDto.getEmail());
		user.setAddress(voterDto.getAddress());
		user.setCountry(voterDto.getCountry());
		user.setDistrict(voterDto.getDistrict());
		user.setWard(voterDto.getWard());
		
		return user;
	}
}
