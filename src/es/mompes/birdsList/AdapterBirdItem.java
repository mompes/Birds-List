package es.mompes.birdsList;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AdapterBirdItem extends ArrayAdapter<Bird> {
	private List<Bird> birds;
	private Context context;
	private int textViewResourceId;
	private boolean english, latin;
	private DBBirds dBBirds;

	public AdapterBirdItem(Context context, int textViewResourceId,
			List<Bird> objects, boolean english, boolean latin, DBBirds dBBirds) {
		super(context, textViewResourceId, objects);
		this.birds = objects;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.english = english;
		this.latin = latin;
		this.dBBirds = dBBirds;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(this.textViewResourceId, null);
		}
		Bird bird = this.birds.get(position);
		// The english name
		TextView englishName = (TextView) v
				.findViewById(R.id.ItemBirdEnglishName);
		englishName.setText(bird.getEnglishName());
		if (this.english) {
			englishName.setVisibility(View.VISIBLE);
		} else {
			englishName.setVisibility(View.GONE);
		}
		TextView latinName = (TextView) v.findViewById(R.id.ItemBirdLatinName);
		latinName.setText(bird.getLatinName());
		// The latin name
		if (this.latin) {
			latinName.setVisibility(View.VISIBLE);
		} else {
			latinName.setVisibility(View.GONE);
		}
		// The watched checkbox
		CheckBox watched = (CheckBox) v.findViewById(R.id.ItemWatched);
		// Ask to the database if this bird has been watched
		if (this.dBBirds.isWatched(this.birds.get(position).getId())) {
			watched.setChecked(true);
		} else {
			watched.setChecked(false);
		}
		return v;
	}
}
