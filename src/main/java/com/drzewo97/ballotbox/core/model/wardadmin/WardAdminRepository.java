package com.drzewo97.ballotbox.core.model.wardadmin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardAdminRepository extends CrudRepository<WardAdmin, Long> {}
