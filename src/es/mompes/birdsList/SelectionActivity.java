package es.mompes.birdsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class SelectionActivity extends Activity {
	private List<Zone> zones;
	private AdapterZoneItem adapter;
	private ListView listView;
	private CheckBox english, latin;
	/**
	 * Store the zones than have been selected.
	 */
	private List<Boolean> selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.english = (CheckBox) findViewById(R.id.cBEnglishName);
		this.latin = (CheckBox) findViewById(R.id.cBLatinName);
		this.selected = new ArrayList<Boolean>(Zone.values().length);
		for (int i = 0; i < Zone.values().length; i++) {
			this.selected.add(false);
		}
		// Set the listener for all zones
		CheckBox allZones = (CheckBox) findViewById(R.id.cBAllZones);
		allZones.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// If the button is checked then all the zones should be
				// selected.
				if (isChecked) {
					for (int i = 0; i < Zone.values().length; i++) {
						selected.set(i, true);
					}
				}
				// Otherwise none should be selected
				else {
					for (int i = 0; i < Zone.values().length; i++) {
						selected.set(i, false);
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		this.zones = new ArrayList<Zone>(Zone.values().length);
		for (Zone zone : Zone.values()) {
			this.zones.add(zone);
		}
		this.adapter = new AdapterZoneItem(this, R.layout.zone_item,
				this.zones, this.selected);
		listView = (ListView) findViewById(R.id.lVZones);
		listView.setAdapter(this.adapter);
	}

	public void onClickSelectionButton(View v) {
		Intent birdsIntent = new Intent(this, BirdsActivity.class);
		Bundle b = new Bundle();
		b.putBoolean("english", this.english.isChecked());
		b.putBoolean("latin", this.latin.isChecked());
		List<Zone> finalZones = new LinkedList<Zone>();
		for (int i = 0; i < this.zones.size(); i++) {
			if (this.selected.get(i)) {
				finalZones.add(this.zones.get(i));
			}
		}
		b.putSerializable("zones", (Serializable) finalZones);
		birdsIntent.putExtras(b);
		startActivity(birdsIntent);
	}

	public void zoneItemClicked(View v) {
		CheckBox cB = (CheckBox) v;
		int i = 0;
		for (Zone zone : this.zones) {
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
