package com.mera.model;

import com.teamtreehouse.model.Player;

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
        mOptions.put("3", "Add player to waiting list");
        mOptions.put("4", "Remove player from team");
        mOptions.put("5", "Remove player from league");
        mOptions.put("6", "View team report");
        mOptions.put("7", "View league balance report");
        mOptions.put("8", "View roster");
        mOptions.put("9", "Exit");
    }

    public void displayTitle(String header)
    {
        mPrompter.display("\n");
        mPrompter.display(header);
        mPrompter.display("\n");
        mPrompter.display("\n");
    }

    public void displayTitle(String header, String subtitle)
    {
        displayTitle(header);

        mPrompter.display(subtitle);
        mPrompter.display("\n");
        mPrompter.display("\n");
    }

    public void displayOptions() {
        mOptions.forEach((menuOption, optionDescription) -> {
            mPrompter.display(menuOption + ") " + optionDescription);
            mPrompter.display("\n");
        });
    }

    public void displayError( String message)
    {
        displayTitle("*****ERROR!!!*****");
        mPrompter.display(message);
        mPrompter.display("\n");
    }

    public int getNumber(String message) {

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

    public String getString(String message) {

        mPrompter.display(message);
        try
        {
            return mPrompter.getString();
        }
        catch(IOException ioe)
        {
            mPrompter.display("Failed at getting the word entered.");
        }

        return null;
    }

    public void displayTeams(Map<Integer, Team> teams) {

        teams.forEach((number, team) -> {
            mPrompter.display(number + ") " + team.getName());
            mPrompter.display("\n");
        });
    }

    public void displayPlayers(Map<Integer, Player> numberedPlayers) {

        displayTitle(String.format("%4s%-25s%-20s", "   ", "Player Name", "Player Height"));
        numberedPlayers.forEach((number, player) -> {
            mPrompter.display(String.format("%4s%-30s%-1s", number + ") " ,player.getLastName() + ", " + player.getFirstName(), player.getHeightInInches()));
            mPrompter.display("\n");
        });

        mPrompter.display("\n");
    }

    public void displayAddedPlayer(Player player, Team team) {

        mPrompter.display("Adding " + formatName(player) + " to " + team.getName() + "!");
        mPrompter.display("\n");
    }

    public void displayRemovedPlayer(Player player, Team team) {

        mPrompter.display("Removing " + formatName(player) + " from " + team.getName() + ".");
        mPrompter.display("\n");
    }

    public void displayTeamReport(Set<String> heightRanges, Map<String, Map<Integer, String>> playersHeightByRange) {

        displayTitle(String.format("%-30s%-20s%-50s","Player Height Range", "Player Count", "Players"));
        heightRanges.forEach((key) -> {
            playersHeightByRange.get(key).forEach((count, playerNames) -> {
                mPrompter.display(String.format("%-3s%-32s%-15s%-50s", "   ", key, count, playerNames));
                mPrompter.display("\n");
            });
        });

        mPrompter.display("\n");
    }

    public void displayLeagueBalanceReport(Set<Team> teams) {

        displayTitle(String.format("%-25s%-20s%-20s%-15s", "Team Name", "Experienced", "Inexperienced", "Experience Avr"));
        teams.forEach(team -> {
            mPrompter.display(String.format("%-30s%-21s%-21s%-10s",team.getName(), team.getExperiencedPlayersCount(),team.getInexperiencedPlayersCount(), team.getExperienceAverage()));
            mPrompter.display("\n");
        });

        mPrompter.display("\n");
    }

    public void displayTeamRoster(Set<Player> players) {

        displayTitle(String.format("%4s%-25s%-20s", "   ", "Player Name", "Player Height"));
        players.forEach((player) -> {
            mPrompter.display(String.format("%-30s%-20s", formatName(player), player.getHeightInInches()));
            mPrompter.display("\n");
        });

        mPrompter.display("\n");
    }

    private String formatName(Player player)
    {
        return player.getLastName() + ",  " + player.getFirstName();
    }
}
