package com.meli.morsedecoder.ws.morsedecoder.service;

public interface ConverterService {
	
	public String getMorseByText(String textInput);
	
	public String translate2Human(String morseInput);
	
	public String getMorseByBitsString(String bitsInput);
	
}
