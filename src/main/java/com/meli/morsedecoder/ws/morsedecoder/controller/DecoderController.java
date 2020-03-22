package com.meli.morsedecoder.ws.morsedecoder.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.morsedecoder.ws.morsedecoder.model.DecodedMessage;
import com.meli.morsedecoder.ws.morsedecoder.model.EncodedMessage;
import com.meli.morsedecoder.ws.morsedecoder.service.ConverterServiceImpl;

@RestController
public class DecoderController {

	@Autowired
	private ConverterServiceImpl cs;
	@Autowired
	private DecodedMessage dm;
	
	/**
	 * Translate bits sequence to morse
	 * @param bits sequence (>4 zeros = separator, > 8 zeros space)
	 * @return  morse text
	 */
	@PostMapping(path = "melitranslator/bits2morse")
	public ResponseEntity<DecodedMessage> decodeBits2Morse(@RequestBody EncodedMessage bitsMessage) {

		String morseResult = cs.getMorseByBitsString(bitsMessage.getText());
		dm.setText(morseResult);
		return new ResponseEntity<DecodedMessage>((DecodedMessage) dm, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Translate morse to text (2 spaces between words)
	 * @param morse
	 * @return text
	 */
	@PostMapping(path = "melitranslator/2text")
	public ResponseEntity<DecodedMessage> decode2text(@RequestBody EncodedMessage morseMessage) {

		String textResult = cs.translate2Human(morseMessage.getText());
		dm.setText(textResult);
		return new ResponseEntity<DecodedMessage>((DecodedMessage)dm, new HttpHeaders(), HttpStatus.OK);

	}
	
	/**
	 * Translate text to morse (1 space means 2 spaces in morse)
	 * @param text
	 * @return morse
	 */
	@PostMapping(path = "melitranslator/2morse")
	public ResponseEntity<DecodedMessage> decode2Morse( @RequestBody EncodedMessage textMessage) {

		String morseResult = cs.getMorseByText(textMessage.getText());
		dm.setText(morseResult);
		return new ResponseEntity<DecodedMessage>((DecodedMessage)dm, new HttpHeaders(), HttpStatus.OK);
	}
}
