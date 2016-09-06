package com.teamtreehouse.model;

import com.mera.model.Team;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Javi on 9/5/2016.
 */
public class Menu {

    private Map<String, String> mOptions;
    private Prompter mPrompter;

    public Menu(InputStream stream) {
        mPrompter = new Prompter(stream);
        mOptions = new TreeMap<>();
        mOptions.put("1", "Create new team.");
        mOptions.put("2", "Remove player from team");
        mOptions.put("3", "View team report");
        mOptions.put("4", "View league balance report");
        mOptions.put("5", "Exit");
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
        return mPrompter.getInt();
    }

    public String getName(String message) {
        mPrompter.display(message);
        return mPrompter.getString();
    }

    public void displayTeams(Map<Integer, Team> teams) {

        if(teams.isEmpty())
        {
            mPrompter.display("There are currently no teams in the league.");
            mPrompter.display("\n");
            return;
        }

        mPrompter.display("Available Teams:");
        mPrompter.display("\n");
        teams.forEach((number, team) -> {
            mPrompter.display(number + ") " + team.getName());
            mPrompter.display("\n");
        });
    }
}
