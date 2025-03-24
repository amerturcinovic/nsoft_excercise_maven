package org.nsoft.exercise.scoreboard.models;

/***
 * MatchInfo - model for API request and response
 *
 * @param homeTeamName - name of home team for match
 * @param homeTeamScore - absolute score value for home team
 * @param guestTeamName - name of guest team for match
 * @param guestTeamScore - absolute score value for guest team
 */
public record MatchInfo(
        String homeTeamName,
        Integer homeTeamScore,
        String guestTeamName,
        Integer guestTeamScore
) {
    public MatchInfo(String homeTeamName, String guestTeamName) {
        this(homeTeamName, 0, guestTeamName, 0);
    }
}
