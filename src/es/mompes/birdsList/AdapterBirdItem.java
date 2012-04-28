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
	private Language language;
	private DBBirds dBBirds;

	public AdapterBirdItem(Context context, int textViewResourceId,
			List<Bird> objects, Language language, DBBirds dBBirds) {
		super(context, textViewResourceId, objects);
		this.birds = objects;
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.language = language;
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
		TextView name = (TextView) v.findViewById(R.id.ItemBirdEnglishName);
		name.setText(bird.getName(this.language));
		// The latin name
		TextView latinName = (TextView) v.findViewById(R.id.ItemBirdLatinName);
		latinName.setText(bird.getName(Language.LATIN));
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
