package es.mompes.birdsList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBBirds {
	public static final String KEY_ID = "_id";
	public static final String KEY_LATIN_NAME = "name";
	public static final String KEY_WATCHED = "watched";

	private static final String DATABASE_NAME = "MyBirds";
	private static final String DATABASE_TABLE = "birds";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " (" + KEY_ID + " integer primary key, "
			+ KEY_LATIN_NAME + " text unique not null, " + KEY_WATCHED
			+ " integer not null);";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBBirds(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBBirds open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---insert a bird into the database---
	public long insertBird(int id, String latinName, Boolean watched) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ID, id);
		initialValues.put(KEY_LATIN_NAME, latinName);
		initialValues.put(KEY_WATCHED, watched ? 1 : 0);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// ---retrieves a bird---
	public Cursor getBird(String latinName) {
		Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
				KEY_LATIN_NAME, KEY_WATCHED, KEY_ID }, KEY_LATIN_NAME + " = ?",
				new String[] { latinName }, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean isBird(String latinName) {
		Cursor mCursor = db.query(DATABASE_TABLE,
				new String[] { KEY_LATIN_NAME }, KEY_LATIN_NAME + " = ?",
				new String[] { latinName }, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor != null && mCursor.getCount() > 0;
	}

	public boolean isWatched(String latinName) {
		Cursor mCursor = db.query(DATABASE_TABLE, new String[] { KEY_WATCHED },
				KEY_LATIN_NAME + " = ?", new String[] { latinName }, null,
				null, null);
		if (mCursor != null && mCursor.getCount() > 0) {
			mCursor.moveToFirst();
		} else {
			return false;
		}
		return mCursor.getInt(mCursor.getColumnIndex(KEY_WATCHED)) == 1;
	}

	// ---updates a bird---
	public boolean updateBird(String latinName, Boolean watched) {
		ContentValues updatedValues = new ContentValues();
		updatedValues.put(KEY_LATIN_NAME, latinName);
		updatedValues.put(KEY_WATCHED, watched ? 1 : 0);
		return db.update(DATABASE_TABLE, updatedValues, KEY_LATIN_NAME + "= ?",
				new String[] { latinName }) > 0;
	}

	// ---retrieves a bird---
	public Cursor getBird(int id) {
		Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
				KEY_LATIN_NAME, KEY_WATCHED, KEY_ID }, KEY_ID + " = ?",
				new String[] { Integer.toString(id) }, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean isBird(int id) {
		Cursor mCursor = db.query(DATABASE_TABLE, new String[] { KEY_ID },
				KEY_ID + " = ?", new String[] { Integer.toString(id) }, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor != null && mCursor.getCount() > 0;
	}

	public boolean isWatched(int id) {
		Cursor mCursor = db.query(DATABASE_TABLE, new String[] { KEY_WATCHED },
				KEY_ID + " = ?", new String[] { Integer.toString(id) }, null,
				null, null);
		if (mCursor != null && mCursor.getCount() > 0) {
			mCursor.moveToFirst();
		} else {
			return false;
		}
		return mCursor.getInt(mCursor.getColumnIndex(KEY_WATCHED)) == 1;
	}

	// ---updates a bird---
	public boolean updateBird(int id, Boolean watched) {
		ContentValues updatedValues = new ContentValues();
		updatedValues.put(KEY_ID, id);
		updatedValues.put(KEY_WATCHED, watched ? 1 : 0);
		return db.update(DATABASE_TABLE, updatedValues, KEY_ID + "= ?",
				new String[] { Integer.toString(id) }) > 0;
	}
}
