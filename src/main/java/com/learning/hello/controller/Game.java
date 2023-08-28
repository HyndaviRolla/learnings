package com.learning.hello.controller;

import java.util.ArrayList;
import java.util.List;

public class Game {
	public static int currentFedererGame =0;
	public static int currentNadalGame =0;
	
	 static String advantage = "";
	 
 

	public  static  boolean gameCompleted() {
		if(ScoreUpdate.FedererScore >= 4 && ScoreUpdate.FedererScore - ScoreUpdate.NadalScore >= 2) 
		    	System.out.println("Federer won game");
		    else if(ScoreUpdate.NadalScore >= 4 && ScoreUpdate.NadalScore - ScoreUpdate.FedererScore >= 2)
		    	System.out.println("Nadal won game");
       return ScoreUpdate.FedererScore >= 4 && ScoreUpdate.FedererScore - ScoreUpdate.NadalScore >= 2||ScoreUpdate.NadalScore >= 4 && ScoreUpdate.NadalScore - ScoreUpdate.FedererScore >= 2;  	
   }

   public  static void updateGame(String player) {
       if (player.equals("Federer")) {
          currentFedererGame= ++ScoreUpdate.FedererGame;
       } else if (player.equals("Nadal")) {
          currentNadalGame= ++ScoreUpdate.NadalGame;
       }
            
           System.out.println("Games won ");
           System.out.print(ScoreUpdate.FedererGame);
           System.out.print("-");
           System.out.println(ScoreUpdate.NadalGame);
           System.out.println();
         
       
       
   }
   public static void checkDeuce() {
       if (ScoreUpdate.FedererScore == ScoreUpdate.NadalScore && ScoreUpdate.FedererScore == 3) {
           ScoreUpdate.deuce = true;
       } else {
           ScoreUpdate.deuce = false;
       }
   }

   public static void deuceAdvantage(String player) {
       if (player.equals(ScoreUpdate.advantage)) {
           updateGame(player);
           ScoreUpdate.FedererScore = 0;
           ScoreUpdate.NadalScore = 0;
           ScoreUpdate.deuce = false;
       } else {
           ScoreUpdate.advantage = player;
       }
   }
}