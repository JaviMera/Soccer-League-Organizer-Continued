package com.mera.model;

import com.teamtreehouse.model.Player;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Javi on 9/6/2016.
 */

public class Team implements Comparable<Team>{

    private String mName;
    private String mCoach;
    private Set<Player> mPlayers;
    private String unexperiencedPlayersCount;

    public Team(String name, String coach)
    {
        mName = name;
        mCoach = coach;
        mPlayers = new TreeSet<>();
    }

    public String getName() {
        return mName;
    }

    public String getCoach() {
        return mCoach;
    }

    public void addPlayer(Player newPlayer)
    {
        mPlayers.add(newPlayer);
    }

    public Set<Player> getPlayers()
    {
        return mPlayers;
    }

    @Override
    public int compareTo(Team otherTeam) {

        if(mName.compareTo(otherTeam.getName()) > 0)
        {
            return 1;
        }
        else if (mName.compareTo(otherTeam.getName()) < 0)
        {
            return -1;
        }

        return 0;
    }

    public void removePlayer(Player player) {

        mPlayers.remove(player);
    }

    public List<Player> groupByHeight() {

        List<Player> playersByHeight = new ArrayList<>(mPlayers);
        Collections.sort(playersByHeight, new HeightComparator());

        return playersByHeight;
    }

    public long getExperiencedPlayersCount()
    {
        return mPlayers.stream()
                .filter(player -> player.isPreviousExperience())
                .count();
    }

    public long getUnexperiencedPlayersCount() {

        return mPlayers.stream()
                .filter(player -> !player.isPreviousExperience())
                .count();
    }
}
