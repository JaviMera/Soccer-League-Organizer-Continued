package com.mera.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Javi on 9/7/2016.
 */
public class Teams {

    public final static int MAX_PLAYERS = 11;

    public static Map<Integer, Team> mapByName(Set<Team> teams)
    {
        Map<Integer, Team> numberedTeams = new HashMap<>();
        teams.forEach(team -> numberedTeams.put(numberedTeams.size() + 1, team));

        return numberedTeams;
    }
}
