package es.mompes.birdsList;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class AdapterZoneItem extends ArrayAdapter<Zone> {

	private List<Zone> zones;
	private Context context;
	private int textViewResourceId;
	private List<Boolean> selected;

	public AdapterZoneItem(final Context context, int textViewResourceId,
			final List<Zone> objects, final List<Boolean> selected) {
		super(context, textViewResourceId, objects);
		this.zones = objects;
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
		Zone zone = this.zones.get(position);
		CheckBox checkBox = (CheckBox) v.findViewById(R.id.ItemZoneCheckBox);
		if (this.selected.get(position)) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}
		checkBox.setText(zone.toHumanString());
		return v;
	}

}
