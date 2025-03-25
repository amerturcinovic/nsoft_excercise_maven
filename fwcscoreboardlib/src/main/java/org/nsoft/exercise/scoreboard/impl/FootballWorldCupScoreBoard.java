package org.nsoft.exercise.scoreboard.impl;


import org.nsoft.exercise.scoreboard.api.TrackableScoreBoard;
import org.nsoft.exercise.scoreboard.models.MatchInfo;
import org.nsoft.exercise.scoreboard.storage.Repository;
import org.nsoft.exercise.scoreboard.storage.impl.SimpleInsertOrderedInMemoryCollection;

import java.util.List;

import static org.nsoft.exercise.scoreboard.utils.Mappers.toMatchDetailsEntity;

/***
 * Implementation of TrackableScoreBoard for football world cup
 * Default implementation of this use simple in memory ordered collection that is not thread safe
 * For different implementation you must provide your own Repository implementation for this class
 */
public class FootballWorldCupScoreBoard implements TrackableScoreBoard, Sortable {
    private final Repository repository;

    public FootballWorldCupScoreBoard(Repository repository) {
        this.repository = repository;
    }

    public FootballWorldCupScoreBoard() {
        repository = new SimpleInsertOrderedInMemoryCollection();
    }

    @Override
    public MatchInfo startMatch(String homeTeamName, String guestTeamName) throws IllegalArgumentException {
        if (isMatchInProgress(new MatchInfo(homeTeamName, guestTeamName)))
            throw new IllegalArgumentException("Match is already in progress");

        validateArguments(homeTeamName, guestTeamName);
        return repository.save(toMatchDetailsEntity(homeTeamName, guestTeamName));
    }

    @Override
    public MatchInfo finishMatch(String homeTeamName, String guestTeamName) throws IllegalArgumentException {
        if (!isMatchInProgress(new MatchInfo(homeTeamName, guestTeamName)))
            throw new IllegalArgumentException("Match is not in progress");

        validateArguments(homeTeamName, guestTeamName);
        return repository.delete(toMatchDetailsEntity(homeTeamName, guestTeamName));
    }

    @Override
    public MatchInfo updateMatch(MatchInfo matchInfo) throws IllegalArgumentException {
        if (!isMatchInProgress(matchInfo))
            throw new IllegalArgumentException("Match is not in progress");

        validateArguments(matchInfo);
        validateScoreValues(matchInfo);

        return repository.update(toMatchDetailsEntity(matchInfo));
    }

    @Override
    public List<MatchInfo> getBoardRanking() {
        return getSorted();
    }

    @Override
    public String toString() {
        List<MatchInfo> sortedMatches = getSorted();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sortedMatches.size(); i++) {
            stringBuilder.append((i + 1)).append(". ").append(sortedMatches.get(i)).append("\n");
        }
        return stringBuilder.toString().replaceAll("\\n$", "");
    }

    @Override
    public List<MatchInfo> getSorted() {
        return repository
                .getAll()
                .stream()
                .sorted()
                .toList();
    }


    private void validateScoreValues(MatchInfo matchInfo) {
        MatchInfo matchInProgress = repository.findByNames(matchInfo.homeTeamName(), matchInfo.guestTeamName());

        if (matchInProgress == null)
            throw new IllegalArgumentException("Match is not in progress");

        if (matchInProgress.homeTeamScore() > matchInfo.homeTeamScore() ||
                matchInProgress.guestTeamScore() > matchInfo.guestTeamScore()) {
            throw new IllegalArgumentException("Match score must be positive number an not less than current score");
        }
    }

    private boolean isMatchInProgress(MatchInfo matchInfo) {
        return repository.findByNames(matchInfo.homeTeamName(), matchInfo.guestTeamName()) != null;
    }

    private void validateArguments(String homeTeam, String guestTeam) {
        if (homeTeam == null || guestTeam == null || homeTeam.isEmpty() || guestTeam.isEmpty())
            throw new IllegalArgumentException("You must provide name for both teams");
    }

    private void validateArguments(MatchInfo matchInfo) {
        validateArguments(matchInfo.homeTeamName(), matchInfo.guestTeamName());

        if (matchInfo.guestTeamScore() < 0 || matchInfo.homeTeamScore() < 0)
            throw new IllegalArgumentException("Score number must be positive number and greater then zero");
    }
}
