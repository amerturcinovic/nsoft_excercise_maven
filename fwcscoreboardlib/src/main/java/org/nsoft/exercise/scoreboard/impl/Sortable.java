package org.nsoft.exercise.scoreboard.impl;

import org.nsoft.exercise.scoreboard.models.MatchInfo;

import java.util.List;

/***
 * Sortable interface that need implementation to get List of sorted entities of matches
 * Implement you custom sort for your Repository
 */
public interface Sortable {
    List<MatchInfo> getSorted();
}
