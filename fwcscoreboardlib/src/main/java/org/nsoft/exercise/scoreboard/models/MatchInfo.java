package org.nsoft.exercise.scoreboard.models;

import java.util.Objects;

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
) implements Comparable<MatchInfo> {
    public MatchInfo(String homeTeamName, String guestTeamName) {
        this(homeTeamName, 0, guestTeamName, 0);
    }

    @Override
    public String toString() {
        return homeTeamName + " " + homeTeamScore + " - " + guestTeamName + " " + guestTeamScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MatchInfo match = (MatchInfo) obj;
        return (homeTeamName + guestTeamName).equals(match.homeTeamName + match.guestTeamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeamName, guestTeamName);
    }

    @Override
    public int compareTo(MatchInfo matchInfo) {
        return (matchInfo.homeTeamScore() + matchInfo.guestTeamScore()) - (homeTeamScore + guestTeamScore);
    }
}
