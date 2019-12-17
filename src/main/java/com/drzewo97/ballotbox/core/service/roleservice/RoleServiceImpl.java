package com.drzewo97.ballotbox.core.service.roleservice;

import com.drzewo97.ballotbox.core.model.role.Role;
import com.drzewo97.ballotbox.core.model.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Set<Role> getVoterRoles() {
		Set<Role> voterRoles = new HashSet<>();
		
		voterRoles.add(roleRepository.findByName("ROLE_USER").get());
		
		return voterRoles;
	}
}
