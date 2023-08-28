package com.learning.hello;
import com.learning.hello.controller.Card;
import com.learning.hello.controller.MangathaController;


public class ShuffleCards {
	private MangathaController deck = new MangathaController();
	
	
	
	private Card  guess = new Card(1,2);
	private Registerdetails user1 = new Registerdetails(guess,"in"); 
	boolean winner=false;
	boolean inList = true;
	 
	public ShuffleCards()
	{
		
		deck.shuffle();
	}
	public void iterate()
	{
		for(Card card:deck.cards)
		{
			System.out.println(card);
		}
	}
	 public void checkInOrOut()   {
		 System.out.println("win");
                 while(winner==false) {
                	 System.out.println("jh");
                	 if(inList) {
                		 System.out.println("yt");
                	 Card topcard = deck.removeFromTop();
                	 System.out.println(topcard);
                	 if(user1.card == topcard ) { 
                		 System.out.println("we");
                		 if(user1.pos=="in") {
                            System.out.println("Winner");
                            winner=true;
                		    
                		 }
                		 else {
                			 System.out.println("Loser");
                		 break;
                	 }
                	 }
                	 inList=false;
                	 
                	 }
                	 else if(!inList)
                	 {
                		 System.out.println("ki");
                		 Card topcard = deck.removeFromTop();
                		 System.out.println(topcard);
                    	 if(user1.card == topcard ) { 
                    		 if(user1.pos=="out") {
                                System.out.println("Winner");
                                winner = true;
                    		     
                    		 }
                    		 else {
                    			 System.out.println("Loser");
                    		 break;
                    	 }
                    	 }
                    	 inList = true;
                    	 
                	 }
                	 else
                	 System.out.println("dealer gets the bet");
                	 
                 }
                    
}
	public static void main(String[] args)
	{
		ShuffleCards checkWinner = new ShuffleCards();
		checkWinner.iterate();
		//checkWinner.checkInOrOut();
	}
}
