package com.learning.hello.controller;

import java.util.Random;

public class  Players {
	public static String choosePlayer() {
	 final String[] players = {"Federer","Nadal"};
	  Random random = new Random();
	  int index = random.nextInt(players.length);
	  String player = players[index];
	  return player;
	}
	  
}
