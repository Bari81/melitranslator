package com.meli.morsedecoder.ws.morsedecoder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Message {

	private String text;
	
	@JsonProperty("text")
	public String getText() {
		return text;
	}
	@JsonProperty("text")
	public void setText(String text) {
		this.text = text;
	}
}
