package com.rapid.model;

public class Gyroscope {
	
	private double xVelocity;
	private double yVelocity;
	private double zVelocity;
	
	public Gyroscope (double xVel, double yVel, double zVel) {
		xVelocity = xVel;
		yVelocity = yVel;
		zVelocity = zVel;
		
	}
	
	
	
	public double getxVelocity() {
		return xVelocity;
	}



	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}



	public double getyVelocity() {
		return yVelocity;
	}



	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}



	public double getzVelocity() {
		return zVelocity;
	}



	public void setzVelocity(double zVelocity) {
		this.zVelocity = zVelocity;
	}



	//Returning the string representation of Gyroscope object
	@Override
	public String toString() {
		return String.format("%s: %s%n%s: %s%n%s: %s%n","\nX-Velocity", getxVelocity(),"Y-Velocity", getyVelocity(), "Z-Velocity", getzVelocity());
	}
	

}
