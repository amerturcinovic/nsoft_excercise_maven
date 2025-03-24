package org.nsoft.exercise.scoreboard.api;


import org.nsoft.exercise.scoreboard.models.MatchInfo;

import java.util.List;

/**
 * This interface represents API for tracking score board of matches in sport.
 * It provides methods for common CRUD operations like starting / inserting match on score board, remove and update match.
 * Also, you can get ranking that will return all matches in progress sorted by most scores and most recent match.
 */
public interface TrackableScoreBoard {
    /**
     * <p>This method start new match and add it to score board. This method is idempotent</p>
     *
     * @param homeTeamName  - non-nullable and not empty name of home team as String
     * @param guestTeamName - non-nullable and not empty name of guest team as String
     * @return MatchInfo - return the match information with scores for home team and guest team
     * @throws IllegalArgumentException - thrown when wrong arguments are passed to the call (empty or null)
     * @see MatchInfo - check model MatchInfo and what information it has
     */
    MatchInfo startMatch(String homeTeamName, String guestTeamName) throws IllegalArgumentException;

    /**
     * <p>This method finish match in progress and remove it from score board.</p>
     *
     * @param homeTeamName  - non-nullable and not empty name of home team as String
     * @param guestTeamName - non-nullable and not empty name of guest team as String
     * @return MatchInfo - return the match information with score for home team and guest team
     * @throws IllegalArgumentException - thrown when wrong arguments are passed to the call (empty or null)
     * @see MatchInfo - check model MatchInfo and what information it has
     */
    MatchInfo finishMatch(String homeTeamName, String guestTeamName) throws IllegalArgumentException;

    /**
     * <p>This method update match in progress with new absolute scores for teams. This method is idempotent</p>
     *
     * @param matchInfo - information model MatchInfo for match update name for teams and new score for teams
     * example: { homeTeam: "BRAZIL", homeTeamScore: 1, guestTeamName: "ARGENTINA", 0 }
     * @return MatchInfo - return the match information with score for home team and guest team
     * @throws IllegalArgumentException - thrown when wrong arguments are passed to the call (empty or null)
     * @see MatchInfo convertToLowerCase
     */
    MatchInfo updateMatch(MatchInfo matchInfo) throws IllegalArgumentException;

    /**
     * <p>This method return ranking of matches in progress, or empty list if there is no match in progress</p>
     *
     * @return List<MatchInfo></MatchInfo> - return the match information with score for home team and guest team
     */
    List<MatchInfo> getBoardRanking();
}