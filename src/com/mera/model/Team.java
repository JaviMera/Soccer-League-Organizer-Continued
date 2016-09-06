package com.mera.model;

import com.teamtreehouse.model.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Javi on 9/6/2016.
 */
public class Team implements Comparable<Team>{

    private String mName;
    private String mCoach;
    private Set<Player> mPlayers;

    public Team(String name, String coach)
    {
        mName = name;
        mCoach = coach;
        mPlayers = new HashSet<>();
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
}
