package es.mompes.birdsList;

import java.io.Serializable;

public enum Zone implements Serializable {
	/**
	 * Europe.
	 */
	EU,
	/**
	 * Australasia.
	 */
	AU,
	/**
	 * Africa.
	 */
	AF,
	/**
	 * South America.
	 */
	SA,
	/**
	 * Worldwide.
	 */
	WORLDWIDE,
	/**
	 * Middle America.
	 */
	MA,
	/**
	 * North America.
	 */
	NA,
	/**
	 * Middle and South America.
	 */
	LA,
	/**
	 * Oriental Region: South Asia from Pakistan to Taiwan, plus southeast Asia,
	 * the Philippines and Greater Sundas.
	 */
	OR,
	/**
	 * Atlantic Ocean.
	 */
	AO,
	/**
	 * Pacific Ocean.
	 */
	PO,
	/**
	 * Indian Ocean.
	 */
	IO,
	/**
	 * Tropical Ocean.
	 */
	TRO,
	/**
	 * Temperate Ocean.
	 */
	TO,
	/**
	 * Northern Ocean.
	 */
	NO,
	/**
	 * Southern Ocean.
	 */
	SO,
	/**
	 * Antarctica.
	 */
	AN,
	/**
	 * Southern Cone.
	 */
	SCONE;

	// @Override
	// public String toString() {
	// String result = "";
	// switch (this) {
	// case EU:
	// result = "EU";
	// break;
	// case AU:
	// result = "AU";
	// break;
	// case AF:
	// result = "AF";
	// break;
	// case SA:
	// result = "SA";
	// break;
	// case WORLDWIDE:
	// result = "Worldwide";
	// break;
	// case MA:
	// result = "MA";
	// break;
	// default:
	// break;
	// }
	// return result;
	// }

	public String toHumanString() {
		String result = "";
		switch (this) {
		case EU:
			result = "Europe";
			break;
		case AU:
			result = "Australia";
			break;
		case AF:
			result = "Africa";
			break;
		case SA:
			result = "South America";
			break;
		case WORLDWIDE:
			result = "Worldwide";
			break;
		case MA:
			result = "Middle America";
			break;
		case NA:
			result = "North America";
			break;
		case LA:
			result = "Latin America";
			break;
		case OR:
			result = "Oriental Region";
			break;
		case AO:
			result = "Atlantic Ocean";
			break;
		case PO:
			result = "Pacific Ocean";
			break;
		case IO:
			result = "Indian Ocean";
			break;
		case TRO:
			result = "Tropical Ocean";
			break;
		case TO:
			result = "Temperate Ocean";
			break;
		case NO:
			result = "Northern Ocean";
			break;
		case SO:
			result = "Southern Ocean";
			break;
		case AN:
			result = "Antarctica";
			break;
		case SCONE:
			result = "Southern Cone";
			break;
		default:
			break;
		}
		return result;
	}

	public static Zone fromHumanString(final String zoneString) {
		for (Zone zone : Zone.values()) {
			if (zone.toHumanString().equals(zoneString)) {
				return zone;
			}
		}
		return null;
	}
}
