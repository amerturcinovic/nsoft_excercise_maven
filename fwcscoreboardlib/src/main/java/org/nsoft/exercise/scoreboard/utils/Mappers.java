package org.nsoft.exercise.scoreboard.utils;

import org.nsoft.exercise.scoreboard.models.MatchInfo;
import org.nsoft.exercise.scoreboard.storage.entity.MatchDetailsEntity;

public class Mappers {
    public static MatchInfo toMatchInfo(MatchDetailsEntity matchDetails) {
        return new MatchInfo(
                matchDetails.homeTeamName(),
                matchDetails.homeTeamScore(),
                matchDetails.guestTeamName(),
                matchDetails.guestTeamScore()
        );
    }

    public static MatchDetailsEntity toMatchDetailsEntity(MatchInfo matchInfo) {
        return new MatchDetailsEntity(
                matchInfo.homeTeamName(),
                matchInfo.homeTeamScore(),
                matchInfo.guestTeamName(),
                matchInfo.guestTeamScore()
        );
    }

    public static MatchDetailsEntity toMatchDetailsEntity(String homeTeamName, String guestTeamName) {
        return new MatchDetailsEntity(homeTeamName, guestTeamName);
    }
}
