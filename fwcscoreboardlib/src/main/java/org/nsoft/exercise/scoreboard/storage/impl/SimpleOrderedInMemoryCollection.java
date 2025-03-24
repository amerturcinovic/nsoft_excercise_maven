package org.nsoft.exercise.scoreboard.storage.impl;


import org.nsoft.exercise.scoreboard.models.MatchInfo;
import org.nsoft.exercise.scoreboard.storage.Repository;
import org.nsoft.exercise.scoreboard.storage.Sortable;
import org.nsoft.exercise.scoreboard.storage.entity.MatchDetailsEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/***
 * Implementation of Repository as simple in memory ordered hash map
 * This implementation is not thread safe, but we could wrap this as synchronized collection
 * or add another concurrent thread safe implementation like ConcurrentHashMap
 * that is why if we want to change our Repository we could just add new implementation in
 */
public class SimpleOrderedInMemoryCollection implements Repository, Sortable {
    private final Map<Integer, MatchDetailsEntity> storage = new LinkedHashMap<>();

    @Override
    public MatchInfo findByNames(String homeTeamName, String guestTeamName) {
        MatchDetailsEntity matchDetailsEntity = storage.get(getStorageHashId(new MatchInfo(homeTeamName, guestTeamName)));

        if (matchDetailsEntity != null)
            return toMatchInfo(matchDetailsEntity);

        return null;
    }

    @Override
    public MatchInfo save(MatchInfo matchInfo) {
        var mathId = getStorageHashId(matchInfo);
        storage.putIfAbsent(mathId, toMatchDetailsEntity(matchInfo));
        return toMatchInfo(storage.get(mathId));
    }

    @Override
    public MatchInfo delete(MatchInfo matchInfo) {
        var matchId = getStorageHashId(matchInfo);
        var matchDetailsEntity = storage.remove(matchId);
        if (matchDetailsEntity != null)
            return toMatchInfo(matchDetailsEntity);

        return null;
    }

    @Override
    public MatchInfo update(MatchInfo matchInfo) {
        var matchId = getStorageHashId(matchInfo);
        MatchDetailsEntity matchDetailsEntity = storage.get(matchId);
        if (matchDetailsEntity == null)
            return null;

        storage.computeIfPresent(matchId, (key, value) -> toMatchDetailsEntity(matchInfo));

        return matchInfo;
    }

    @Override
    public List<MatchInfo> getAll() {
        return getSorted()
                .stream()
                .map(this::toMatchInfo)
                .toList();
    }

    @Override
    public String toString() {
        List<MatchDetailsEntity> sorted = getSorted();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            stringBuilder.append((i + 1)).append(". ").append(sorted.get(i)).append("\n");
        }
        return stringBuilder.toString().replaceAll("\\n$", "");
    }

    @Override
    public List<MatchDetailsEntity> getSorted() {
        return new ArrayList<>(storage.values()).reversed().stream().sorted().toList();
    }

    private Integer getStorageHashId(MatchInfo matchInfo) {
        return toMatchDetailsEntity(matchInfo).hashCode();
    }
}