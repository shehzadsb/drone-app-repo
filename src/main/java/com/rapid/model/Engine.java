package com.rapid.model;

public class Engine {
	private int power;
	private EngineStatus engineStatus;
	
	public Engine(int power, EngineStatus engineStatus) {
		this.power = power;
		this.engineStatus = engineStatus;
	}
	
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		if(power < 0 || power > 100)
			throw new IllegalArgumentException("The power should be between 0 and 100");
		this.power = power;
	}
	
	public EngineStatus getEngineStatus() {
		return engineStatus;
	}
	public void setEngineStatus(EngineStatus engineStatus) {
		this.engineStatus = engineStatus;
	}
	
	//Returning the string representation of Engine object
	@Override
	public String toString() {
		return String.format("%s: %n%s: %n", getPower(), getEngineStatus().value());
	}
	

}
