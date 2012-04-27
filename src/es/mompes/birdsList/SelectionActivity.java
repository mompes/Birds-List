package es.mompes.birdsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SelectionActivity extends Activity {
	/**
	 * All the zones that are shown.
	 */
	private List<Region> regions;
	private AdapterZoneItem adapter;
	private ListView listView;
	/**
	 * Selects the language to show the name of the birds.
	 */
	private CheckBox english, latin;
	/**
	 * If the user selects any subregion it is store in this variable.
	 */
	private String subRegionSelected;
	/**
	 * Store the zones than have been selected.
	 */
	private List<Boolean> selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		this.english = (CheckBox) findViewById(R.id.cBEnglishName);
		this.latin = (CheckBox) findViewById(R.id.cBLatinName);
		this.selected = new ArrayList<Boolean>(Region.values().length);
		for (int i = 0; i < Region.values().length; i++) {
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
		registerForContextMenu(listView);
		listView.setAdapter(this.adapter);
		// Initialize the variable
		this.subRegionSelected = "";
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onClickSelectionButton(View v) {
		Intent birdsIntent = new Intent(this, BirdsActivity.class);
		Bundle b = new Bundle();
		b.putBoolean("english", this.english.isChecked());
		b.putBoolean("latin", this.latin.isChecked());
		b.putString("subRegion", this.subRegionSelected);
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getClass().equals(ListView.class)) {
			Log.d("ContextMenu", "ListView");
			return;
		}
		Log.d("ContextMenu", "Creating");
		// Log.d("ContextMenu", "menuInfo: " + menuInfo.getClass());
		// AdapterContextMenuInfo aCMI = (AdapterContextMenuInfo) menuInfo;
		RelativeLayout rL = (RelativeLayout) v;
		CheckBox cB = (CheckBox) rL.findViewById(R.id.cBItemZone);
		Log.d("ContextMenu", "Region: " + cB.getText().toString());
		for (String subRegion : Manager.getSubRegions(this,
				Region.parseHumanRegion(cB.getText().toString()))) {
			menu.add(subRegion);
			Log.d("ContextMenu", "Subregion: " + subRegion);
		}
		Log.d("ContextMenu", "Created");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		this.subRegionSelected = item.getTitle().toString();
		return super.onContextItemSelected(item);
	}

}
