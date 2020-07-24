package com.rapid.model;

public class OrientationSensor {
	
	private double pitch;  //aligned with x-axis
	private double rollLeft;  //aligned with y-axis
	private double rollRight;  //aligned with y-axis
	
	public OrientationSensor(double pitch, double rollLeft, double rollRight) {
		this.pitch = pitch;
		this.rollLeft = rollLeft;
		this.rollRight = rollRight;
	}
	
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		if(pitch < 0 || pitch > 1)
			throw new IllegalArgumentException("The pitch should be between 0 and 1");
		
		this.pitch = pitch;
	}
	
	
	
	
	public double getRollLeft() {
		return rollLeft;
	}

	public void setRollLeft(double rollLeft) {
		if(rollLeft < 0 || rollLeft > 1)
			throw new IllegalArgumentException("The left roll should be between 0 and 1");
		
		this.rollLeft = rollLeft;
	}

	public double getRollRight() {
		return rollRight;
	}

	public void setRollRight(double rollRight) {
		if(rollRight < 0 || rollRight > 1)
			throw new IllegalArgumentException("The right roll should be between 0 and 1");
		
		this.rollRight = rollRight;
	}

	//Returning the string representation of OrientationSensor object
	@Override
	public String toString() {
		return String.format("%s: %s%n%s: %s%n%s: %s%n","\nPitch", getPitch(),"Roll Left", getRollLeft(),"Roll Right", getRollRight());
	}
	
	
	
	
	
	
	
	
	

	
}
