package com.drzewo97.ballotbox.model.choice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends CrudRepository<Choice, Long> {
    Boolean existsByName(String name);
}
