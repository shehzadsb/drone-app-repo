package com.rapid.model;

public enum DroneStatus {
	OFF("off"),
	HOVERING("hovering"),
	MOVE("move");
	
	private String value;

	DroneStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

