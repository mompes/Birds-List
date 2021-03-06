package es.mompes.birdsList;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;

public class Manager {
	/**
	 * 
	 * @return All the known birds.
	 * @throws XmlPullParserException
	 */
	// public static List<Bird> getAllBirds(final Activity activity) {
	// LinkedList<Bird> list = new LinkedList<Bird>();
	// XmlResourceParser parser = null;
	// parser = activity.getResources().getXml(R.xml.birds);
	// try {
	// String latinName = "";
	// String genus = "";
	// int eventType = parser.getEventType();
	// while (eventType != XmlPullParser.END_DOCUMENT) {
	// String name = null;
	// switch (eventType) {
	// case XmlPullParser.START_TAG:
	// name = parser.getName();
	// if (name.equals("english_name") & parser.getDepth() > 5) {
	// list.add(new Bird(genus + " " + latinName, parser
	// .nextText(), Zone.WORLDWIDE));
	// latinName = "";
	// } else if (name.equals("latin_name")) {
	// latinName = parser.nextText();
	// } else if (name.equals("genus")) {
	// parser.next();
	// genus = parser.nextText();
	// }
	// break;
	// }
	// eventType = parser.next();
	// }
	// } catch (XmlPullParserException e) {
	// throw new RuntimeException("Cannot parse XML");
	// } catch (IOException e) {
	// throw new RuntimeException("Cannot parse XML");
	// } finally {
	// parser.close();
	// }
	// return list;
	// }

	/**
	 * 
	 * @param region
	 *            The region where the birds than we are asking for live.
	 * @return All the known birds of that specific zone.
	 */
	public static List<Bird> getBirdsOfRegion(final Activity activity,
			final Region region) {
		LinkedList<Bird> list = new LinkedList<Bird>();
		XmlResourceParser parser = null;
		parser = activity.getResources().getXml(R.xml.birds);
		try {
			String latinName = "";
			String genus = "";
			String breedingRegion = "";
			String englishName = "";
			String spanishName = "";
			String germanName = "";
			String danishName = "";
			String italianName = "";
			String frenchName = "";
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("english_name") & parser.getDepth() > 5) {
						englishName = parser.nextText();
					} else if (name.equals("latin_name")) {
						latinName = genus + " " + parser.nextText();
					} else if (name.equals("spanish_name")) {
						spanishName = parser.nextText();
					} else if (name.equals("german_name")) {
						germanName = parser.nextText();
					} else if (name.equals("italian_name")) {
						italianName = parser.nextText();
					} else if (name.equals("danish_name")) {
						danishName = parser.nextText();
					} else if (name.equals("french_name")) {
						frenchName = parser.nextText();
					} else if (name.equals("genus")) {
						parser.next();
						genus = parser.nextText();
					} else if (name.equals("breeding_regions")) {
						breedingRegion = parser.nextText();
					} else if (!englishName.equals("") & name.equals("code")) {
						int code = Integer.parseInt(parser.nextText());
						Map<Language, String> languages = new HashMap<Language, String>();
						languages.put(Language.LATIN, latinName);
						languages.put(Language.ENGLISH, englishName);
						languages.put(Language.DANISH, danishName);
						languages.put(Language.FRENCH, frenchName);
						languages.put(Language.GERMAN, germanName);
						languages.put(Language.ITALIAN, italianName);
						languages.put(Language.SPANISH, spanishName);
						if (breedingRegion.contains(region.toString())) {
							list.add(new Bird(languages, region, code));
						}
						englishName = "";
						latinName = "";
						spanishName = "";
						italianName = "";
						germanName = "";
						frenchName = "";
						danishName = "";
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			throw new RuntimeException("Cannot parse XML");
		} catch (IOException e) {
			throw new RuntimeException("Cannot parse XML");
		} finally {
			parser.close();
		}
		return list;
	}

	// public static List<Bird> getBirdsOfSubRegion(final Activity activity,
	// final String subRegion) {
	// LinkedList<Bird> list = new LinkedList<Bird>();
	// XmlResourceParser parser = null;
	// parser = activity.getResources().getXml(R.xml.birds);
	// try {
	// String latinName = "";
	// String genus = "";
	// String breedingSubRegion = "";
	// String englishName = "";
	// int eventType = parser.getEventType();
	// while (eventType != XmlPullParser.END_DOCUMENT) {
	// String name = null;
	// switch (eventType) {
	// case XmlPullParser.START_TAG:
	// name = parser.getName();
	// if (name.equals("english_name") & parser.getDepth() > 5) {
	// englishName = parser.nextText();
	// } else if (name.equals("latin_name")) {
	// latinName = parser.nextText();
	// } else if (name.equals("genus")) {
	// parser.next();
	// genus = parser.nextText();
	// } else if (name.equals("breeding_subregions")) {
	// breedingSubRegion = parser.nextText();
	// } else if (!englishName.equals("") & name.equals("code")) {
	// int code = Integer.parseInt(parser.nextText());
	// if (breedingSubRegion.contains(subRegion)) {
	// list.add(new Bird(genus + " " + latinName,
	// englishName, Region.AF, code));
	// }
	// englishName = "";
	// }
	// break;
	// }
	// eventType = parser.next();
	// }
	// } catch (XmlPullParserException e) {
	// throw new RuntimeException("Cannot parse XML");
	// } catch (IOException e) {
	// throw new RuntimeException("Cannot parse XML");
	// } finally {
	// parser.close();
	// }
	// return list;
	// }

	public static List<String> getSubRegions(final Activity activity,
			final Region zone) {
		LinkedList<String> list = new LinkedList<String>();
		XmlResourceParser parser = null;
		parser = activity.getResources().getXml(R.xml.birds);
		try {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("breeding_regions")) {
						String regionString = parser.nextText();
						parser.next();
						if (regionString.equals(zone.toString())) {
							String[] subRegions = parser.nextText().split(", ");
							for (String sR : subRegions) {
								if (sR.length() > 2 & !list.contains(sR)) {
									list.add(sR);
								}
							}
						}
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			throw new RuntimeException("Cannot parse XML");
		} catch (IOException e) {
			throw new RuntimeException("Cannot parse XML");
		} finally {
			parser.close();
		}
		return list;
	}
}
