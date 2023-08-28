package com.learning.hello.controller;

public class OdometerController {
	
	private static final String DIGITS = "123456789";
	
	private int reading;
	
	public static int getMinReading(int size) {
		return Integer.valueOf(DIGITS.substring(0, size));
	}
	
	private static int getMaxReading(int size) {
		return Integer.valueOf(DIGITS.substring(DIGITS.length() - size, DIGITS.length()));
	}
	
	private static int getSize(int reading) {
		return String.valueOf(reading).length();
	}
	public OdometerController()
	{
		
	}
	public void odometer(int number) {
		reading = number;
	}
	
	public OdometerController(OdometerController copy) {
		reading = copy.reading;
	}
	
	public int getReading() {
		return reading;
	}
	
 
	@Override
	public String toString() {
		return "(" + reading + ")";
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof OdometerController))
			return false;
		OdometerController otherOdo = (OdometerController) other;
		return otherOdo.reading == this.reading;
	}
	
	public static boolean isAscending(int reading) {
		if (reading < 10)
			return true;
		if(reading % 10 <= (reading / 10) % 10)
			return false;
		return isAscending(reading / 10);
	}
	
	
	public void incrementReading() {
		do {
			if (reading == getMaxReading(getSize(reading)))
				reading = getMinReading(getSize(reading));
			else
				reading++;
		} while (!isAscending(reading));
	}
	
	public OdometerController nextReading() {
		OdometerController temp = new OdometerController(this);
		temp.incrementReading();
		return temp;
	}
	
	public void decrementReading() {
		do {
			if (reading == getMinReading(getSize(reading)))
				reading = getMaxReading(getSize(reading));
			else
				reading--;
		} while (!isAscending(reading));
	}
	public OdometerController prevReading() {
		OdometerController temp = new OdometerController(this);
		temp.decrementReading();
		return temp;
	}
	public void incrementbyStep(int k)
	{
		for(int i=0;i<k;i++)
			incrementReading();
	}
	public OdometerController nextStepReading(int k) {
		OdometerController temp = new OdometerController(this);
		 
		temp.incrementbyStep(k);
		
		return temp;
	}
	
	public void DecrementbyStep(int k)
	{
		for(int i=0;i<k;i++)
			decrementReading();
	}
	public OdometerController prevStepReading(int k) {
		OdometerController temp = new OdometerController(this);
		 
		temp.DecrementbyStep(k);
		
		return temp;
	}
	public void reset() {
		this.reading = getMinReading(getSize(this.reading));
	}
	
	public int getSize() {
		return getSize(this.reading);
	}
}