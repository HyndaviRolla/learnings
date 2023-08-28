package com.learning.hello.controller;

import java.util.ArrayList;
import java.util.List;

public class ScoreUpdate {
   public static int FedererScore = 0;
   public static int NadalScore = 0;
  public  static int FedererGame = 0;
  public  static int NadalGame = 0;
  public  static int FedererSet = 0;
  public  static int NadalSet = 0;
  public  static boolean deuce = false;
 public   static String advantage = "";
 public  static boolean tiebreaker = false;
  public static  int tiebreakerFederer = 0;
  public  static int tiebreakerNadal= 0;
  public  static int currentFedererScore=0;
  public static int currentNadalScore=0;
  public static List<Integer> score = new ArrayList<Integer>();
   public static int[] matches;
   
    public static void updateScore(String player) { 
        if (player.equals("Federer")) 
         currentFedererScore = ++ FedererScore;
         else if (player.equals("Nadal")) 
          currentNadalScore = ++NadalScore;
        System.out.print(FedererScore);
        System.out.print("-");
           System.out.println(NadalScore);
    
           if (deuce) {
    			Game.deuceAdvantage(player);
            } else if (Game.gameCompleted()) {
                Game.updateGame(player);
                Game.checkDeuce();
                FedererScore = 0;
                NadalScore = 0;
               
            }
        if (Set.setCompleted()) {
            Set.updateSet(player);
            FedererGame = 0;
            NadalGame = 0;  
        }
        if (MatchCompleted()) {
        	System.out.println();
            System.out.println("Federersets: " + FedererSet);
            System.out.println("Nadalsets: " + NadalSet);
            MatchWinner();
        }
         
    }
    private static void MatchWinner() {
        if (FedererSet >= 2 && NadalSet < 2) {
            System.out.println("Match won by Federer");
        }
        else if (NadalSet >= 2 && FedererSet < 2)
        	System.out.println("Match won by Nadal ");
    }

    public  static boolean MatchCompleted() {
     return FedererSet >= 2 && NadalSet < 2 || (NadalSet >= 2 && FedererSet < 2);
    }
    public static void  startGame() {
    	 System.out.println("hii");
        while (!ScoreUpdate.MatchCompleted()) {
          String player = Players.choosePlayer();
                 ScoreUpdate.updateScore(player);
                
                
        }
	
	 
    }
}
