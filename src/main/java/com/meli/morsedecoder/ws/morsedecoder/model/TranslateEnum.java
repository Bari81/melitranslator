package com.meli.morsedecoder.ws.morsedecoder.model;

public enum TranslateEnum {

		A(".-"),
		B("-..."),
		C("-.-."),
		D("-.."),
		E("."),
		F("..-."),
		G("--."),
		H("...."),
		I(".."),
		J(".---"),
		K("-.-"),
		L(".-.."),
		M("--"),
		N("-."),
		O("---"),
		P(".--."),
		Q("--.-"),
		R(".-."),
		S("..."),
		T("-"),
		U("..-"),
		V("...-"),
		W(".--"),
		X("-..-"),
		Y("-.--"),
		Z("--.."),
		ZERO("-----"),
		ONE(".----"),
		TWO("..---"),
		THREE("...--"),
		FOUR("....-"),
		FIVE("....."),
		SIX("-...."),
		SEVEN("--..."),
		EIGHT("---.."),
		NINE("----.");
		
		 private String code;

		TranslateEnum(String code) {
			this.setCode(code);
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}
