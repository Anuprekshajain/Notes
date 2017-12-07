package anupreksha.com.noteit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableController extends DatabaseHandler {
    public TableController(Context context) {
        super(context);
    }
    public boolean create(Note newNote) {

        ContentValues values = new ContentValues();

        values.put("title",newNote.getmTitle());
        values.put("description",newNote.getmDescription() );

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("Notes", null, values) > 0;
        db.close();

        return createSuccessful;
    }
    public List<Note> read() {

        List<Note> recordsList = new ArrayList<Note>();

        String sql = "SELECT * FROM Notes ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));

                Note newNote = new Note();
                newNote.id = id;
                newNote.mTitle=title;
                newNote.mDescription=description;

                recordsList.add(newNote);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
    public Note readSingleRecord(int noteId) {

        Note newNote = null;

        String sql = "SELECT * FROM Notes WHERE id = " + noteId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));

            newNote = new Note();
            newNote.id = id;
            newNote.mTitle= title;
            newNote.mDescription = description;

        }

        cursor.close();
        db.close();

        return newNote;

    }
    public boolean update(Note newNote) {

        ContentValues values = new ContentValues();

        values.put("title", newNote.mTitle);
        values.put("description", newNote.mDescription);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(newNote.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("Notes", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }
    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("Notes", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}
