package com.teamtreehouse.model;

import com.mera.model.Team;
import com.sun.deploy.util.StringUtils;

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
        mOptions.put("6", "View roster");
        mOptions.put("7", "Exit");
    }

    public void displayTitle()
    {
        mPrompter.display("***** WELCOME TO THE AMAZING SOCCER LEAGUE *****");
        mPrompter.display("\n");
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

        mPrompter.display("***** Available Teams *****");
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

        mPrompter.display("***** Available Players *****");
        mPrompter.display("\n");
        mPrompter.display("\n");

        mPrompter.display(String.format("%4s%-25s%-20s", "   ", "Player Name", "Player Height"));
        mPrompter.display("\n");
        mPrompter.display("\n");

        numberedPlayers.forEach((number, player) -> {
            mPrompter.display(String.format("%4s%-30s%-1s", number + ") " ,player.getLastName() + player.getFirstName(), player.getHeightInInches()));
            mPrompter.display("\n");
        });

        mPrompter.display("\n");
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

    public void displayTeamReport(Set<String> heightRanges, Map<String, Map<Integer, String>> playersHeightByRange) {

        mPrompter.display("***** Team Report By Height *****");
        mPrompter.display("\n");
        mPrompter.display("\n");

        mPrompter.display(String.format("%-30s%-20s%-50s","Player Height Range", "Player Count", "Players"));
        mPrompter.display("\n");

        heightRanges.forEach((key) -> {

            playersHeightByRange.get(key).forEach((count, playerNames) -> {
                mPrompter.display(String.format("%-3s%-32s%-15s%-50s", "   ", key, count, playerNames));
                mPrompter.display("\n");
            });
        });

        mPrompter.display("\n");
    }

    public void displayLeagueBalanceReport(Set<Team> teams) {

        mPrompter.display("***** League Balance Report *****");
        mPrompter.display("\n");
        mPrompter.display("\n");

        mPrompter.display(String.format("%-25s%-20s%-20s%-15s", "Team Name", "Experienced", "Inexperienced", "Experience Avr"));
        mPrompter.display("\n");
        mPrompter.display("\n");

        teams.forEach(team -> {
            mPrompter.display(String.format("%-30s%-21s%-21s%-10s",team.getName(), team.getExperiencedPlayersCount(),team.getInexperiencedPlayersCount(), team.getExperienceAverage()));
            mPrompter.display("\n");
        });

        mPrompter.display("\n");
    }

    public void displayAddPlayerFailure(Player player) {

        mPrompter.display("*****ERROR!!!*****");
        mPrompter.display("\n");
        mPrompter.display(player.getFirstName() + " " + player.getLastName() + " can't be added.");
        mPrompter.display("\n");
    }

    public void displayRemovePlayerFailure() {
        mPrompter.display("*****ERROR!!!*****");
        mPrompter.display("\n");
        mPrompter.display("Player does not exist in this team.");
        mPrompter.display("\n");
    }

    public void displayTeamRoster(Set<Player> players) {

        mPrompter.display("***** Team Roster *****");
        mPrompter.display("\n");
        mPrompter.display("\n");

        mPrompter.display(String.format("%-30s%-20s", "Player Name", "Player Height"));
        mPrompter.display("\n");
        mPrompter.display("\n");

        players.forEach((player) -> {
            mPrompter.display(String.format("%-30s%-20s",player.getLastName() + ", " + player.getFirstName(), player.getHeightInInches()));
            mPrompter.display("\n");
        });

        mPrompter.display("\n");
    }

    public void displayAddTeamFailure() {

        mPrompter.display("\n");
        mPrompter.display("You have reached number of teams allowed in the league. Sorry :(");
        mPrompter.display("\n");
        mPrompter.display("\n");
    }
}
