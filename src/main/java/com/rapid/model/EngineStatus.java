package com.rapid.model;

public enum EngineStatus {
	
	OFF("off"),
	ON("on");
	
	private String value;

	EngineStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
