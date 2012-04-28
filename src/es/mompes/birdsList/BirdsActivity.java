package es.mompes.birdsList;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
	 * The selected language.
	 */
	private Language language;
	/**
	 * The zones that the user has selected to watch.
	 */
	private List<Region> regions;
	/**
	 * A dialog used to show information to the user.
	 */
	private ProgressDialog dialog;
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
			this.language = (Language) savedInstanceState
					.getSerializable("language");
			this.regions = (List<Region>) savedInstanceState
					.getSerializable("zones");
		} else {
			Bundle b = getIntent().getExtras();
			this.language = (Language) b.getSerializable("language");
			this.regions = (List<Region>) b.getSerializable("zones");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Latin alphabetic");
		menu.add(this.language.toString() + " alphabetic");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("Latin alphabetic")) {
			Comparator<Bird> comparator = new Comparator<Bird>() {

				public int compare(Bird lhs, Bird rhs) {
					return lhs.getName(Language.LATIN).compareTo(
							rhs.getName(Language.LATIN));
				}
			};
			Collections.sort(this.birds, comparator);
		} else {
			Comparator<Bird> comparator = new Comparator<Bird>() {

				public int compare(Bird lhs, Bird rhs) {
					return lhs.getName(language).compareTo(
							rhs.getName(language));
				}
			};
			Collections.sort(this.birds, comparator);
		}
		this.adapter.notifyDataSetChanged();
		return super.onOptionsItemSelected(item);
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
			dialog.setTitle("Progress");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		}
		new fillBirds(this).execute(this.regions);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("language", this.language);
		outState.putSerializable("zones", (Serializable) this.regions);
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
			if (this.birds.get(i).getName(Language.LATIN).equals(latinName)) {
				// Updates the value in the database
				this.dBBirds.updateBird(this.birds.get(i).getId(),
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

	private class fillBirds extends
			AsyncTask<List<Region>, Integer, List<Bird>> {

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
			dialog.setMax(regions.size());
			dialog.setMessage("Loading regions...");
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Bird> doInBackground(List<Region>... zones) {
			// Fill the list
			List<Bird> birds = new LinkedList<Bird>();
			int j = 0;
			for (Region zone : zones[0]) {
				j++;
				birds.addAll(Manager.getBirdsOfRegion(this.activity, zone));
				// Publish the progress each time than a new zone is loaded
				publishProgress(j);
			}
			return birds;
		}

		@Override
		protected void onPostExecute(final List<Bird> result) {
			// Store the birds in the database if they aren't yet
			for (int i = 0; i < result.size(); i++) {
				if (!dBBirds.isBird(result.get(i).getId())) {
					dBBirds.insertBird(result.get(i).getId(), result.get(i)
							.getName(Language.LATIN), false);
				}
			}
			// Put the birds in the listView
			birds = result;
			adapter = new AdapterBirdItem(this.activity, R.layout.bird_item,
					birds, language, dBBirds);
			final ListView listView = (ListView) findViewById(R.id.listViewBirds);
			listView.setAdapter(adapter);

			// Creates a list with the names of the selected birds in english
			// and in latin
			List<String> birdsString = new LinkedList<String>();
			for (int i = 0; i < result.size(); i++) {
				birdsString.add(result.get(i).getName(language));
				birdsString.add(result.get(i).getName(Language.LATIN));
			}
			// Set the list to the autoCompleteTextView
			ArrayAdapter<String> adapterACTV = new ArrayAdapter<String>(
					activity, android.R.layout.simple_list_item_1, birdsString);
			AutoCompleteTextView aCTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
			// When the user click on one of the suggestions the list move to
			// that bird
			aCTV.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					for (int i = 0; i < result.size(); i++) {
						if (result.get(i).getName(language)
								.equals(((TextView) arg1).getText())
								| result.get(i).getName(Language.LATIN)
										.equals(((TextView) arg1).getText())) {
							listView.setSelection(i);
							break;
						}
					}
				}
			});
			aCTV.setAdapter(adapterACTV);

			dialog.dismiss();
			super.onPostExecute(result);
		}

		protected void onProgressUpdate(Integer... values) {
			dialog.setProgress(values[0]);
			super.onProgressUpdate(values);
		}
	}
}