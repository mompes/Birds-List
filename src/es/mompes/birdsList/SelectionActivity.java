package es.mompes.birdsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Spinner;

public class SelectionActivity extends Activity {
	/**
	 * All the zones that are shown.
	 */
	private List<Region> regions;
	private AdapterZoneItem adapter;
	private ListView listView;
	/**
	 * The languages to select.
	 */
	private Spinner languages;
	/**
	 * Store the zones than have been selected.
	 */
	private List<Boolean> selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		// Recover the spinner
		this.languages = (Spinner) findViewById(R.id.languages);
		this.languages.setAdapter(new ArrayAdapter<Language>(this,
				android.R.layout.simple_list_item_single_choice, Language
						.values()));
		this.languages.setSelection(0);
		// Initialize the list of regions selected
		this.selected = new ArrayList<Boolean>(Region.values().length);
		for (int i = 0; i < Region.values().length; i++) {
			this.selected.add(false);
		}
		// Set the listener for all the regions
		CheckBox allZones = (CheckBox) findViewById(R.id.cBAllRegions);
		allZones.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// If the button is checked then all the zones should be
				// selected.
				if (isChecked) {
					for (int i = 0; i < Region.values().length; i++) {
						selected.set(i, true);
					}
				}
				// Otherwise none should be selected
				else {
					for (int i = 0; i < Region.values().length; i++) {
						selected.set(i, false);
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		this.regions = new ArrayList<Region>(Region.values().length);
		for (Region zone : Region.values()) {
			this.regions.add(zone);
		}
		this.adapter = new AdapterZoneItem(this, R.layout.zone_item,
				this.regions, this.selected);
		listView = (ListView) findViewById(R.id.lVZones);
		listView.setAdapter(this.adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onClickSelectionButton(View v) {
		Intent birdsIntent = new Intent(this, BirdsActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("language",
				(Language) this.languages.getSelectedItem());
		List<Region> finalZones = new LinkedList<Region>();
		for (int i = 0; i < this.regions.size(); i++) {
			if (this.selected.get(i)) {
				finalZones.add(this.regions.get(i));
			}
		}
		b.putSerializable("zones", (Serializable) finalZones);
		birdsIntent.putExtras(b);
		startActivity(birdsIntent);
	}

	public void zoneItemClicked(View v) {
		CheckBox cB = (CheckBox) v;
		int i = 0;
		for (Region zone : this.regions) {
			if (zone.toHumanString().equals(cB.getText())) {
				if (cB.isChecked()) {
					this.selected.set(i, true);
				} else {
					this.selected.set(i, false);
				}
				break;
			}
			i++;
		}
	}
}
