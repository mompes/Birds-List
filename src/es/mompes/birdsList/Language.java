package es.mompes.birdsList;

import java.io.Serializable;

public enum Language implements Serializable {
	ENGLISH, SPANISH, DANISH, ITALIAN, FRENCH, GERMAN, LATIN;

	@Override
	public String toString() {
		String result;
		switch (this) {
		case SPANISH:
			result = "Spanish";
			break;
		case ENGLISH:
			result = "English";
			break;
		case DANISH:
			result = "Danish";
			break;
		case ITALIAN:
			result = "Italian";
			break;
		case FRENCH:
			result = "French";
			break;
		case GERMAN:
			result = "German";
			break;
		case LATIN:
			result = "Latin";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

	public static Language parseLanguage(final String language) {
		for (Language lang : Language.values()) {
			if (lang.toString().equals(language)) {
				return lang;
			}
		}
		return null;
	}
}
