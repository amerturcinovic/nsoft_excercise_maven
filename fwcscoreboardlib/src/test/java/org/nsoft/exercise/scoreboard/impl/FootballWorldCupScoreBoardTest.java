package org.nsoft.exercise.scoreboard.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nsoft.exercise.scoreboard.api.TrackableScoreBoard;
import org.nsoft.exercise.scoreboard.models.MatchInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}