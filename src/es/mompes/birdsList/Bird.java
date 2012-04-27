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
	private Region zone;
	private int id;

	/**
	 * @param latinName
	 * @param englishName
	 * @param zone
	 */
	public Bird(String latinName, String englishName, Region zone, int id) {
		this.latinName = latinName;
		this.englishName = englishName;
		this.zone = zone;
		this.id = id;
	}

	public String getLatinName() {
		return latinName;
	}

	public String getEnglishName() {
		return englishName;
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
	}

	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}
