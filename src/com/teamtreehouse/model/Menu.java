package com.teamtreehouse.model;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Javi on 9/5/2016.
 */
public class Menu {

    private Map<String, String> mOptions;
    private Prompter mPrompter;

    public Menu(InputStream stream)
    {
        mPrompter = new Prompter(stream);
        mOptions = new TreeMap<>();
        mOptions.put("1", "Create new team.");
        mOptions.put("2", "Exit");
    }

    public void displayOptions()
    {
        System.out.println("***** WELCOME TO THE AMAZING SOCCER LEAGUE *****");
        System.out.println();
        System.out.println();
        mOptions.forEach((menuOption, optionDescription) -> {
            System.out.println(menuOption + ") " + optionDescription);
        });
    }

    public int getOption(String message)
    {
        mPrompter.display(message);
        return mPrompter.getInt();
    }
}
