package com.meli.morsedecoder.ws.morsedecoder.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.meli.morsedecoder.ws.morsedecoder.model.TranslateEnum;

@Service
public class ConverterServiceImpl implements ConverterService{

	private Map<Integer, Integer> pointsAndHyphensLimits;
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	
	/**
	 * Check if String is numeric
	 * **/
	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}
	/**
	 * Retrieve number name to get the enum number value
	 * **/
	private String getNumberByChar(char param) {
		String numberName = "";

		switch (param) {
		case '0':
			numberName = "ZERO";
			break;
		case '1':
			numberName = "ONE";
			break;

		case '2':
			numberName = "TWO";
			break;

		case '3':
			numberName = "THREE";
			break;

		case '4':
			numberName = "FOUR";
			break;

		case '5':
			numberName = "FIVE";
			break;

		case '6':
			numberName = "SIX";
			break;

		case '7':
			numberName = "SEVEN";
			break;

		case '8':
			numberName = "EIGHT";
			break;

		case '9':
			numberName = "NINE";
			break;

		default:
			break;
		}

		return numberName;
	}

	/**
	 * 
	 * @param Char text letter
	 * @return String morse word by name in enum
	 */
	
	private String decodeByName(char param) {
		StringBuffer decodedParam = new StringBuffer();
		String charToStr = Character.toString(param).toUpperCase();
		if (isNumeric(charToStr)) {
			charToStr = getNumberByChar(param);
		}
		for (TranslateEnum te : TranslateEnum.values()) {
			if (te.name().contentEquals(charToStr)) {
				decodedParam.append(te.getCode());
				decodedParam.append(" ");
			}
		}
		return decodedParam.toString();
	}

	/**
	 * 
	 * @param String morse word
	 * @return String text by value in enum
	 */
	private String decodeByValue(String param) {
		StringBuffer decodedParam = new StringBuffer();
		String aux = "";
		if (param.isEmpty()) {
			aux = " ";
			decodedParam.append(aux);
		} else {
			for (TranslateEnum te : TranslateEnum.values()) {
				if (te.getCode().contentEquals(param)) {
					aux = te.name();
					decodedParam.append(aux);
				}
			}
		}
		return decodedParam.toString();
	}
	/**
	 * Retrieve morse text by text input
	 * @param String text (1 space means 2 spaces in morse in order to separate words)
	 * @return String morse text
	 */
	public String getMorseByText(String textInput) {
		StringBuffer aux = new StringBuffer();
		for (int i = 0; i < textInput.length(); i++) {
			char x = textInput.charAt(i);
			if(x == ' ') {
				aux.append(' ');
			} else {
				aux.append(this.decodeByName(x));
			}
		}
		return aux.toString();
	}
	
	/**
	 * Retrieve text by morse input text
	 * @param String morse text (1 space as separator between letters, 2 spaces to separate words)
	 * @return String text
	 */
	public String translate2Human(String morseInput) {

		String[] parts = morseInput.split(" ");
		StringBuffer aux = new StringBuffer();
		for (int i = 0; i < parts.length; i++) {
			aux.append(this.decodeByValue(parts[i]));
		}
		return aux.toString();
	}

	/**
	 * Retrieve morse text by bits sequence
	 * @param String bits sequence (>4 zeros = separator, > 8 zeros space)
	 * @return String morse text
	 */
	public String getMorseByBitsString(String bitsInput) {
		String morseResult = "";
		int limitPoint = getLimitByMedia(bitsInput);
		int counter1 = 0;
		int counter0 = 0;
		char[] chars = bitsInput.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '1') {
				counter1 += 1;
				System.out.println(counter0);
				if (counter0 >= 4 && counter0 < 8) {

					morseResult = morseResult + " ";
				}
				if (counter0 >= 8) {

					morseResult = morseResult + "  ";
				}
				System.out.println(counter0);
				System.out.println(morseResult);
				counter0 = 0;
			} else {
				counter0 += 1;
				if (counter1 > 0) {
					if (counter1 < limitPoint) {
						morseResult = morseResult + ".";
					} else {
						morseResult = morseResult + "-";
					}
				}
				counter1 = 0;
			}
		}
		return morseResult;
	}

	
	/**
	 *Retrieve the limit to get a - or a . in morse by bits secuence
	 * @param String bits sequence (>4 zeros = separator, > 8 zeros space)
	 * @return String morse text
	 */
	public int getLimitByMedia(String bitsInput) {

		pointsAndHyphensLimits = new HashMap<Integer, Integer>();
		int counter = 0;

		char[] chars = bitsInput.toCharArray();
		for (int i = 0; i < chars.length; i++) {

			if (chars[i] == '1') {
				counter += 1;
				if (i == (chars.length - 1)) {
					putCountersInMap(counter);
				}
			} else {
				if (counter > 0) {
					putCountersInMap(counter);
				}
				counter = 0;
			}

		}
		System.out.println(pointsAndHyphensLimits);
		// double median = calculateMedian();
		double media = calculateMedia();
		int mediaInt = (int) Math.round(media);
		System.out.println(mediaInt);

		return mediaInt;
	}

	private void putCountersInMap(int counter) {
		int counterBits = 1;
		if (pointsAndHyphensLimits.containsKey(counter)) {
			pointsAndHyphensLimits.put(counter, pointsAndHyphensLimits.get(counter) + 1);
		} else {
			pointsAndHyphensLimits.put(counter, counterBits);
		}
	}

	private double calculateMedian() {
		Set<Integer> keys = pointsAndHyphensLimits.keySet();
		int[] arrayOfBitGroups = new int[keys.size()];
		int index = 0;
		for (Integer element : keys)
			arrayOfBitGroups[index++] = element.intValue();
		Arrays.sort(arrayOfBitGroups);
		double median;
		if (arrayOfBitGroups.length % 2 == 0)
			median = ((double) arrayOfBitGroups[arrayOfBitGroups.length / 2]
					+ (double) arrayOfBitGroups[arrayOfBitGroups.length / 2 - 1]) / 2;
		else
			median = (double) arrayOfBitGroups[arrayOfBitGroups.length / 2];

		return median;
	}

	private double calculateMedia() {
		int total = 0;
		double media = 0;
		int sizeOfMap = pointsAndHyphensLimits.size();
		for (Map.Entry<Integer, Integer> entry : pointsAndHyphensLimits.entrySet()) {
			total += entry.getValue();
		}

		media = ((double) total / (double) sizeOfMap);
		return media;
	}

}
