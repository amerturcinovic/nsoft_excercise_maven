package org.nsoft.exercise.scoreboard.storage.entity;

import java.util.Objects;

/***
 * MatchDetailsEntity for persistence
 *
 * @param homeTeamName - name of home team for match
 * @param homeTeamScore - absolute score value for home team
 * @param guestTeamName - name of guest team for match
 * @param guestTeamScore - absolute score value for guest team
 */
public record MatchDetailsEntity(
        String homeTeamName,
        Integer homeTeamScore,
        String guestTeamName,
        Integer guestTeamScore
) implements Comparable<MatchDetailsEntity> {
    @Override
    public String toString() {
        return homeTeamName + " " + homeTeamScore + " - " + guestTeamName + " " + guestTeamScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MatchDetailsEntity match = (MatchDetailsEntity) obj;
        return (homeTeamName + guestTeamName).equals(match.homeTeamName + match.guestTeamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeamName, guestTeamName);
    }

    @Override
    public int compareTo(MatchDetailsEntity matchDetailsEntity) {
        return (matchDetailsEntity.homeTeamScore() + matchDetailsEntity.guestTeamScore()) - (homeTeamScore + guestTeamScore);
    }
}
