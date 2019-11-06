package com.drzewo97.ballotbox.core.model.poll;

/**
 * Tells how poll is constructed
 */
public enum VotingMode {
    /**
     * Voter can give at most some amount of choices
     */
    AT_MOST,

    /**
     * Voter has to give exact amount of choices
     */
    EXACTLY
}
