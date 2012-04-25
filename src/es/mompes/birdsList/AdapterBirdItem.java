package es.mompes.birdsList;

import java.util.List;

import android.content.Context;
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

	public AdapterBirdItem(Context context, int textViewResourceId,
			List<Bird> objects, boolean english, boolean latin) {
		super(context, textViewResourceId, objects);
		this.birds = objects;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.english = english;
		this.latin = latin;
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
		if (this.english) {
			TextView englishName = (TextView) v
					.findViewById(R.id.ItemBirdEnglishName);
			englishName.setText(bird.getEnglishName());
		}
		// The latin name
		if (this.latin) {
			TextView latinName = (TextView) v
					.findViewById(R.id.ItemBirdLatinName);
			latinName.setText(bird.getLatinName());
		}
		// The watched checkbox
		CheckBox watched = (CheckBox) v.findViewById(R.id.ItemWatched);
		watched.setChecked(false);
		return v;
	}

}
