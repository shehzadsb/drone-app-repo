package com.rapid.model;

public enum Direction {
	
	FORWARD("forward"),
	LEFT("left"),
	RIGHT("right"),
	BACK("back"),
	UP("up"),
	DOWN("down");

	private String value;

	Direction(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
