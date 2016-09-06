package com.teamtreehouse.model;

import java.io.InputStream;
import java.util.Scanner;

public class Prompter {

    private Scanner mScanner;
    public Prompter(InputStream input)
    {
        mScanner = new Scanner(input);
    }

    public void display(String message)
    {
        print(message);
    }

    public void display(String message, Object... params)
    {
        print(message, params);
    }

    public String getString() {
        return mScanner.next();
    }

    public int getInt()
    {
        return mScanner.nextInt();
    }

    public void close() {
        mScanner.close();
    }

    private void print(String message)
    {
        System.out.printf(message);
    }

    private void print(String message, Object...args)
    {
        System.out.printf(message, args);
    }
}