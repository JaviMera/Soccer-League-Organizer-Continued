package com.teamtreehouse.model;

import com.mera.model.Team;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by Javi on 9/5/2016.
 */
public class Menu {

    private Map<String, String> mOptions;
    private Prompter mPrompter;

    public Menu(Reader reader) {
        mPrompter = new Prompter(reader);
        mOptions = new TreeMap<>();
        mOptions.put("1", "Create new team.");
        mOptions.put("2", "Add player to team");
        mOptions.put("3", "Remove player from team");
        mOptions.put("4", "View team report");
        mOptions.put("5", "View league balance report");
        mOptions.put("6", "Exit");
    }

    public void displayTitle()
    {
        mPrompter.display("***** WELCOME TO THE AMAZING SOCCER LEAGUE *****");
        mPrompter.display("\n");
    }

    public void displayOptions() {
        mOptions.forEach((menuOption, optionDescription) -> {
            mPrompter.display(menuOption + ") " + optionDescription);
            mPrompter.display("\n");
        });
    }

    public int getOption(String message) {

        mPrompter.display(message);
        try
        {
            return mPrompter.getInt();
        }
        catch(IOException ioe)
        {
            mPrompter.display("Failed at getting the right data from the stream.");
            mPrompter.display("\n");
        }
        catch(NumberFormatException nfe)
        {
            mPrompter.display("Enter a valid number next time.");
            mPrompter.display("\n");
        }

        return -1;
    }

    public String getName(String message) {

        mPrompter.display(message);
        try
        {
            return mPrompter.getString();
        }
        catch(IOException ioe)
        {
            mPrompter.display("Failed at getting the right data from the stream.");
            ioe.printStackTrace();
        }

        return null;
    }

    public void displayTeams(Map<Integer, Team> teams) {

        mPrompter.display("Available Teams:");
        mPrompter.display("\n");
        teams.forEach((number, team) -> {
            mPrompter.display(number + ") " + team.getName());
            mPrompter.display("\n");
        });
    }

    public void displayEmptyTeamTitle() {

        mPrompter.display("There are currently no teams in the league.");
        mPrompter.display("\n");
    }

    public void displayPlayers(Map<Integer, Player> numberedPlayers) {

        mPrompter.display("Available Players:");
        mPrompter.display("\n");
        mPrompter.display("\n");

        numberedPlayers.forEach((number, player) -> {
            mPrompter.display(number + ") " + player.getLastName() + ", " + player.getFirstName() + '\t' + player.getHeightInInches());
            mPrompter.display("\n");
        });
    }

    public void displayAddedPlayer(Player player, Team team) {

        mPrompter.display(
            "Adding " +
            player.getFirstName() +
            " " +
            player.getLastName() +
            " to " +
            team.getName() +
            "!");

        mPrompter.display("\n");
    }

    public void displayRemovedPlayer(Player player, Team team) {
        mPrompter.display(
            "Removing " +
            player.getFirstName() +
            " " +
            player.getLastName() +
            " from " +
            team.getName() +
            ".");

        mPrompter.display("\n");
    }

    public void displayTeamReport(Team team) {

        mPrompter.display("Viewing a report for " + team.getName());
        mPrompter.display("\n");

        List<Player> playersByHeight = team.groupByHeight();

        playersByHeight.forEach(player -> {
            mPrompter.display(
                player.getFirstName() +
                " " +
                player.getLastName() +
                "\t" +
                player.getHeightInInches());

            mPrompter.display("\n");
        });
    }

    public void displayLeagueBalanceReport(Set<Team> teams) {

        mPrompter.display("League Balance Report");
        mPrompter.display("\n");

        teams.forEach(team -> {
            mPrompter.display(team.getName() + " " + team.getExperiencedPlayersCount() + " " + team.getInexperiencedPlayersCount());
            mPrompter.display("\n");
        });
    }

    public void displayAddPlayerFailure(Player player) {

        mPrompter.display("*****ERROR!!!*****");
        mPrompter.display("\n");
        mPrompter.display(player.getFirstName() + " " + player.getLastName() + " already exists in this team!.");
        mPrompter.display("\n");
    }

    public void displayRemovePlayerFailure() {
        mPrompter.display("*****ERROR!!!*****");
        mPrompter.display("\n");
        mPrompter.display("Player does not exist in this team.");
        mPrompter.display("\n");
    }
}
