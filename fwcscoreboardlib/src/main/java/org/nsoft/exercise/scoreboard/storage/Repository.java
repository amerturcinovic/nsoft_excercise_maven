package org.nsoft.exercise.scoreboard.storage;

import org.nsoft.exercise.scoreboard.models.MatchInfo;
import org.nsoft.exercise.scoreboard.storage.entity.MatchDetailsEntity;

import java.util.List;

/***
 * Repository interface that has basic CRUD functions
 */
public interface Repository {
    MatchInfo findByNames(String homeTeamName, String guestTeamName);

    MatchInfo save(MatchDetailsEntity matchDetailsEntity) throws IllegalArgumentException;

    MatchInfo delete(MatchDetailsEntity matchDetailsEntity) throws IllegalArgumentException;

    MatchInfo update(MatchDetailsEntity matchDetailsEntity) throws IllegalArgumentException;

    List<MatchInfo> getAll();
}
