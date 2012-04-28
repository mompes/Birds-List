package es.mompes.birdsList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Bird implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2526860255418058243L;
	private Map<Language, String> names;
	private Region zone;
	private int id;

	/**
	 * @param latinName
	 * @param englishName
	 * @param zone
	 */
	public Bird(Map<Language, String> names, Region zone, int id) {
		this.names = names;
		this.zone = zone;
		this.id = id;
	}

	public String getName(Language lang) {
		return this.names.get(lang);
	}

	public Region getZone() {
		return zone;
	}

	public int getId() {
		return id;
	}

	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default de-serialization first
		aInputStream.defaultReadObject();
		this.names = (Map<Language, String>) aInputStream.readObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
		aOutputStream.writeObject(this.names);
	}
}
