package es.mompes.birdsList;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class AdapterZoneItem extends ArrayAdapter<Region> {

	private List<Region> regions;
	private Context context;
	private int textViewResourceId;
	private List<Boolean> selected;

	public AdapterZoneItem(final Context context, int textViewResourceId,
			final List<Region> objects, final List<Boolean> selected) {
		super(context, textViewResourceId, objects);
		this.regions = objects;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.selected = selected;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(this.textViewResourceId, null);
		}
		((Activity) this.context).registerForContextMenu(v);
		Region region = this.regions.get(position);
		CheckBox checkBox = (CheckBox) v.findViewById(R.id.cBItemZone);
		if (this.selected.get(position)) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}
		checkBox.setText(region.toHumanString());
		return v;
	}

	@Override
	public int getCount() {
		return this.regions.size();
	}

	@Override
	public Region getItem(int position) {
		return this.regions.get(position);
	}

	@Override
	public int getPosition(Region item) {
		return this.regions.indexOf(item);
	}

}
