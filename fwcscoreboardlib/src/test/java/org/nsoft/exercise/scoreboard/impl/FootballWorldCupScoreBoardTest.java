package org.nsoft.exercise.scoreboard.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.nsoft.exercise.scoreboard.api.TrackableScoreBoard;
import org.nsoft.exercise.scoreboard.models.MatchInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FootballWorldCupScoreBoardTest {
    private TrackableScoreBoard footballScoreBoard;

    @BeforeEach
    public void init() {
        footballScoreBoard = new FootballWorldCupScoreBoard();
    }

    @Test
    public void whenOneMatchStarted_ExpectToShow_OnScoreBoard() {
        // given
        var expectedMatchInfo = new MatchInfo("BRAZIL", 0, "ARGENTINA", 0);

        // when
        MatchInfo actualMatchInfo = footballScoreBoard.startMatch("BRAZIL", "ARGENTINA");

        // then
        assertEquals(expectedMatchInfo, actualMatchInfo);
        assertEquals(List.of(expectedMatchInfo), footballScoreBoard.getBoardRanking());
    }

    @Test
    public void whenMatchAlreadyStarted_And_FinishedCalled_Expect_EmptyScoreBoard() {
        // given
        var expectedEmptyScoreBoard = List.of();

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA");
        footballScoreBoard.finishMatch("BRAZIL", "ARGENTINA");

        // then
        assertEquals(expectedEmptyScoreBoard, footballScoreBoard.getBoardRanking());
    }

    @Test
    public void whenMatchAlreadyStarted_And_UpdatedCalled_ExpectToShow_OnScoreBoard() {
        // given
        var expectedMathInfoUpdate = new MatchInfo("BRAZIL", 1, "ARGENTINA", 1);

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA");
        MatchInfo actualMatchInfoUpdate = footballScoreBoard.updateMatch(expectedMathInfoUpdate);

        // then
        assertEquals(actualMatchInfoUpdate, expectedMathInfoUpdate);
        assertEquals(List.of(expectedMathInfoUpdate), footballScoreBoard.getBoardRanking());
    }

    @Test
    public void whenMultipleMachStarted_ExpectToShow_OnScoreBoard_SortedByMostRecentStart() {
        // given
        var expectedMatchesInProgress = List.of(
                new MatchInfo("CROATIA", 0, "ENGLAND", 0),
                new MatchInfo("SPAIN", 0, "FRANCE", 0),
                new MatchInfo("BRAZIL", 0, "ARGENTINA", 0)
        );

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA");
        footballScoreBoard.startMatch("SPAIN", "FRANCE");
        footballScoreBoard.startMatch("CROATIA", "ENGLAND");

        //then
        assertEquals(expectedMatchesInProgress, footballScoreBoard.getBoardRanking());
    }

    @Test
    public void whenMultipleMachStarted__And_UpdateScore_ExpectToShow_OnScoreBoard_SortedByMostScores_And_MostRecentStart() {
        // given
        var expectedMatchesInProgress = List.of(
                new MatchInfo("SPAIN", 1, "FRANCE", 1),
                new MatchInfo("BRAZIL", 1, "ARGENTINA", 0),
                new MatchInfo("PORTUGAL", 0, "ITALY", 0),
                new MatchInfo("CROATIA", 0, "ENGLAND", 0)
        );

        // when
        footballScoreBoard.startMatch("BRAZIL", "ARGENTINA");
        footballScoreBoard.startMatch("SPAIN", "FRANCE");
        footballScoreBoard.startMatch("CROATIA", "ENGLAND");
        footballScoreBoard.startMatch("PORTUGAL", "ITALY");

        footballScoreBoard.updateMatch(
                new MatchInfo("BRAZIL", 1, "ARGENTINA", 0)
        );
        footballScoreBoard.updateMatch(
                new MatchInfo("SPAIN", 1, "FRANCE", 0)
        );
        footballScoreBoard.updateMatch(
                new MatchInfo("SPAIN", 1, "FRANCE", 1)
        );

        // then
        assertEquals("""
                1. SPAIN 1 - FRANCE 1
                2. BRAZIL 1 - ARGENTINA 0
                3. PORTUGAL 0 - ITALY 0
                4. CROATIA 0 - ENGLAND 0""", footballScoreBoard.toString());
        assertEquals(expectedMatchesInProgress, footballScoreBoard.getBoardRanking());
    }

    @Test
    public void whenMatchAlreadyStarted_AndCalledStartAgain_Expect_Exception() {
        // given
        var expectedMatchInfo = new MatchInfo("BRAZIL", 0, "FRANCE", 0);

        // when
        footballScoreBoard.startMatch("BRAZIL", "FRANCE");

        // then
        assertEquals(List.of(expectedMatchInfo), footballScoreBoard.getBoardRanking());
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.startMatch("BRAZIL", "FRANCE"));
    }

    @Test
    public void whenUpdateMatchCalled_WithWrongScore_Expect_Exception() {
        // when
        footballScoreBoard.startMatch("BRAZIL", "FRANCE");
        footballScoreBoard.updateMatch(
                new MatchInfo("BRAZIL", 2, "FRANCE", 1)
        );

        // then
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.updateMatch(
                new MatchInfo("BRAZIL", 1, "FRANCE", 1)
        ));
    }

    @Test
    public void whenFinish_Or_Update_MatchCalled_WhenMatchIsNotInProgress_Expect_Exception() {
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.finishMatch("BRAZIL", "ARGENTINA"));
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.updateMatch(
                new MatchInfo("BRAZIL", 1, "ARGENTINA", 0)
        ));
    }

    @ParameterizedTest
    @CsvSource({",\"\"", "\"\",", ","})
    public void whenStartMatchCalled_WithInvalidArgument_Expect_Exception(String testHomeTeamName, String testGuestTeamName) {
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.startMatch(testHomeTeamName, testGuestTeamName));
    }

    @ParameterizedTest
    @CsvSource({",\"\"", "\"\",", ","})
    public void whenFinishMatchCalled_WithInvalidArgument_Expect_Exception(String testHomeTeamName, String testGuestTeamName) {
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.finishMatch(testHomeTeamName, testGuestTeamName));
    }

    @ParameterizedTest
    @CsvSource({",\"\"", "\"\",", ","})
    public void whenUpdateMatchCalled_WithInvalidArgument_Expect_Exception(String testHomeTeamName, String testGuestTeamName) {
        assertThrows(IllegalArgumentException.class, () -> footballScoreBoard.updateMatch(
                new MatchInfo(testHomeTeamName, 1, testGuestTeamName, 0)
        ));
    }
}