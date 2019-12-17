package com.drzewo97.ballotbox.core.service.roleservice;

import com.drzewo97.ballotbox.core.model.role.Role;

import java.util.Set;

public interface RoleService {
	Set<Role> getVoterRoles();
}
