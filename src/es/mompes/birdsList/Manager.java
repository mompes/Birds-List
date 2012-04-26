package es.mompes.birdsList;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
	 * @param zone
	 *            The zone where the birds than we are asking for live.
	 * @return All the known birds of that specific zone.
	 */
	public static List<Bird> getBirdsOfZone(final Activity activity,
			final Zone zone) {
		LinkedList<Bird> list = new LinkedList<Bird>();
		XmlResourceParser parser = null;
		parser = activity.getResources().getXml(R.xml.birds);
		try {
			String latinName = "";
			String genus = "";
			String breedingRegion = "";
			String englishName = "";
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType) {
				case XmlPullParser.START_TAG:
					name = parser.getName();
					if (name.equals("english_name") & parser.getDepth() > 5) {
						englishName = parser.nextText();
					} else if (name.equals("latin_name")) {
						latinName = parser.nextText();
					} else if (name.equals("genus")) {
						parser.next();
						genus = parser.nextText();
					} else if (name.equals("breeding_regions")) {
						breedingRegion = parser.nextText();
					} else if (!englishName.equals("") & name.equals("code")) {
						int code = Integer.parseInt(parser.nextText());
						if (breedingRegion.contains(zone.toString())) {
							list.add(new Bird(genus + " " + latinName,
									englishName, zone, code));
						}
						englishName = "";
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

	public List<String> getSubZones(final Activity activity, final Zone zone) {
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
						String zoneString = parser.nextText();
						parser.next();
						if (zoneString.contains(zone.toString())) {
							String[] subZones = parser.nextText().split(", ");
							for (String z : subZones) {
								list.add(z);
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
