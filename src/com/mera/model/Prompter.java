package com.mera.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Prompter {

    private BufferedReader mReader;
    public Prompter(Reader reader)
    {
        mReader = new BufferedReader(reader);
    }

    public void display(String message)
    {
        print(message);
    }

    public String getString() throws IOException{
        return mReader.readLine();
    }

    public int getInt() throws IOException
    {
        String option = mReader.readLine();
        return Integer.parseInt(option);
    }

    private void print(String message)
    {
        System.out.printf(message);
    }
}