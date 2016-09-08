package com.mera.model;

import com.teamtreehouse.model.Player;

import java.util.*;

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
        mPlayers = new TreeSet<>();
    }

    public String getName() {
        return mName;
    }

    public boolean addPlayer(Player newPlayer) {
        return mPlayers.size() != 11 && mPlayers.add(newPlayer);
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

    public boolean removePlayer(Player player) {

        return mPlayers.remove(player);
    }

    public Map<String, List<Player>> groupByHeight() {

        Map<String, List<Player>> groupByHeight = new HashMap<>();
        for(int range = 34 ; range < 50 ; range+=5)
        {
            List<Player> playersInRange = new ArrayList<>();

            int min = range + 1;
            int max = min + 5 >= 50
                    ? 50
                    : min + 5;

            mPlayers.forEach(player -> {
                if(player.getHeightInInches() >= min && player.getHeightInInches() <= max )
                    playersInRange.add(player);
            });

            groupByHeight.put(String.format("%d - %d", min, max), playersInRange);
            range++;
        }

        return groupByHeight;
    }

    public long getExperiencedPlayersCount()
    {
        return mPlayers.stream()
                .filter(Player::isPreviousExperience)
                .count();
    }

    public long getInexperiencedPlayersCount() {

        return mPlayers.stream()
                .filter(player -> !player.isPreviousExperience())
                .count();
    }

    public float getExperienceAverage()
    {
        float experiencedPlayers = getExperiencedPlayersCount();

        return Math.round(experiencedPlayers / mPlayers.size() * 100);
    }

    public boolean isFull() {
        return mPlayers.size() == Teams.MAX_PLAYERS;
    }
}
