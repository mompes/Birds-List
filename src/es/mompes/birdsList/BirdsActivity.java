package es.mompes.birdsList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BirdsActivity extends Activity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7749624101659486198L;
	private AdapterBirdItem adapter;
	/**
	 * The birds that are showed to the user.
	 */
	private List<Bird> birds;
	/**
	 * If it is true the english name will be showed.
	 */
	private boolean english;
	/**
	 * If it is true the latin name will be showed.
	 */
	private boolean latin;
	/**
	 * The zones that the user has selected to watch.
	 */
	private List<Zone> zones;
	/**
	 * A dialog used to show information to the user.
	 */
	private ProgressDialog dialog;
	/**
	 * Store the watched birds.
	 */
	private List<Boolean> watched;
	/**
	 * Database.
	 */
	private DBBirds dBBirds;

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
		if (this.dBBirds == null) {
			this.dBBirds = new DBBirds(this);
			this.dBBirds.open();
		}
		if (this.dialog == null) {
			dialog = new ProgressDialog(this);
			dialog.setMessage("Loading...");
			dialog.setTitle("Progress");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		}
		new fillBirds(this).execute(this.zones);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("english", this.english);
		outState.putBoolean("latin", this.latin);
		outState.putSerializable("zones", (Serializable) this.zones);
		super.onSaveInstanceState(outState);
	}

	public void onClickWatched(View v) {
		// Recover the bird that have marked as watched
		CheckBox cb = (CheckBox) v;
		RelativeLayout rl = (RelativeLayout) cb.getParent();
		TextView tv = (TextView) rl.findViewById(R.id.ItemBirdLatinName);
		String latinName = tv.getText().toString();
		// Store the new watched bird
		for (int i = 0; i < this.birds.size(); i++) {
			if (this.birds.get(i).getLatinName().equals(latinName)) {
				this.watched.set(i, cb.isChecked());
				// Updates the value in the database
				this.dBBirds.updateBird(this.birds.get(i).getLatinName(),
						cb.isChecked());
				break;
			}
		}
	}

	@Override
	protected void onStop() {
		this.dBBirds.close();
		super.onStop();
	}

	private class fillBirds extends AsyncTask<List<Zone>, Integer, List<Bird>> {

		private Activity activity;

		/**
		 * @param activity
		 */
		public fillBirds(Activity activity) {
			super();
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			dialog.setProgress(0);
			dialog.setMax(zones.size());
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Bird> doInBackground(List<Zone>... zones) {
			// Fill the list
			List<Bird> birds = new LinkedList<Bird>();
			int i = 0;
			for (Zone zone : zones[0]) {
				i++;
				birds.addAll(Manager.getBirdsOfZone(this.activity, zone));
				publishProgress(i);
			}
			return birds;
		}

		@Override
		protected void onPostExecute(List<Bird> result) {
			for (int i = 0; i < result.size(); i++) {
				if (!dBBirds.isBird(result.get(i).getLatinName())) {
					dBBirds.insertBird(result.get(i).getLatinName(), false);
				}
			}
			watched = new LinkedList<Boolean>();
			for (int i = 0; i < result.size(); i++) {
				Cursor cursor = dBBirds.getBird(result.get(i).getLatinName());
				if (cursor != null
						&& cursor.getString(
								cursor.getColumnIndex(DBBirds.KEY_LATIN_NAME))
								.equals(result.get(i).getLatinName())) {
					watched.add(cursor.getInt(cursor
							.getColumnIndex(DBBirds.KEY_WATCHED)) == 1 ? true
							: false);
				} else {
					watched.add(false);
				}
			}
			birds = result;
			adapter = new AdapterBirdItem(this.activity, R.layout.bird_item,
					birds, watched, english, latin);
			ListView listView = (ListView) findViewById(R.id.listViewBirds);
			listView.setAdapter(adapter);
			dialog.dismiss();
			super.onPostExecute(result);
		}

		protected void onProgressUpdate(Integer... values) {
			dialog.setProgress(values[0]);
			super.onProgressUpdate(values);
		}
	}
}