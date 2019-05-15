package com.drzewo97.ballotbox.web.dao.polldao;

import com.drzewo97.ballotbox.model.poll.Poll;

/**
 * DAO for Poll entity
 * @see com.drzewo97.ballotbox.model.poll.Poll
 */
public class PollDao {

    private Long id;

    private String name;

    private String description;

    public static PollDao construct(Poll poll){
        PollDao dao = new PollDao();
        dao.setId(poll.getId());
        dao.setName(poll.getName());
        dao.setDescription(poll.getDescription());

        return dao;
    }

    private PollDao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
