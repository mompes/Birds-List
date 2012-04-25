package es.mompes.birdsList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class BirdsActivity extends Activity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7749624101659486198L;
	private AdapterBirdItem adapter;
	private List<Bird> birds;
	/**
	 * If it is true the english name will be showed.
	 */
	private boolean english;
	/**
	 * If it is true the latin name will be showed.
	 */
	private boolean latin;
	private List<Zone> zones;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birds_list);
		// Recover the data passed
		if (savedInstanceState != null) {
			this.english = savedInstanceState.getBoolean("english");
			this.latin = savedInstanceState.getBoolean("latin");
			this.zones = (List<Zone>) savedInstanceState
					.getSerializable("zones");
		} else {
			Bundle b = getIntent().getExtras();
			this.english = b.getBoolean("english");
			this.latin = b.getBoolean("latin");
			this.zones = (List<Zone>) b.getSerializable("zones");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Fill the list
		this.birds = new LinkedList<Bird>();
		for (Zone zone : this.zones) {
			this.birds.addAll(Manager.getBirdsOfZone(this, zone));
		}
		Log.d("Number of birds", Integer.toString(birds.size()));
		this.adapter = new AdapterBirdItem(this, R.layout.bird_item,
				this.birds, this.english, this.latin);
		ListView listView = (ListView) findViewById(R.id.listViewBirds);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("english", this.english);
		outState.putBoolean("latin", this.latin);
		outState.putSerializable("zones", (Serializable) this.zones);
		super.onSaveInstanceState(outState);
	}
}