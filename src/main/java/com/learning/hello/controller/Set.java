package com.learning.hello.controller;

import java.util.ArrayList;
import java.util.List;

public class Set {
	public static int currentFedererSets =0;

	public static int currentNadalSets =0;
	 
    public static boolean setCompleted() {
        if (ScoreUpdate.tiebreaker) {
            return ScoreUpdate.tiebreakerFederer >= 7 || ScoreUpdate.tiebreakerNadal >= 7;
        } else {
            return ScoreUpdate.FedererGame >= 6 || ScoreUpdate.NadalGame >= 6;
        }
    }

    public static void updateSet(String player) {
        if (player.equals("Federer")) {
           currentFedererSets = ++ScoreUpdate.FedererSet;
        } else if (player.equals("Nadal")) {
            currentNadalSets = ++ScoreUpdate.NadalSet;
        }
       
        System.out.println("sets");
        System.out.print(ScoreUpdate.FedererSet);
        System.out.print("-");
        System.out.println(ScoreUpdate.NadalSet);
        System.out.println();
       
    }
}
