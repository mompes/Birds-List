package es.mompes.birdsList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Bird implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2526860255418058243L;
	private String latinName;
	private String englishName;
	private Zone zone;

	/**
	 * @param latinName
	 * @param englishName
	 * @param zone
	 */
	public Bird(String latinName, String englishName, Zone zone) {
		this.latinName = latinName;
		this.englishName = englishName;
		this.zone = zone;
	}

	public String getLatinName() {
		return latinName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public Zone getZone() {
		return zone;
	}

	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default de-serialization first
		aInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}