package uk.ac.wlv.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "message_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MESSAGES = "messages";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public MessageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CONTENT + " TEXT,"
                + COLUMN_IMAGE_PATH + " TEXT,"
                + COLUMN_TIMESTAMP + " INTEGER"
                + ")";
        db.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public long addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, message.getContent());
        values.put(COLUMN_IMAGE_PATH, message.getImagePath());
        values.put(COLUMN_TIMESTAMP, message.getTimestamp());
        long id = db.insert(TABLE_MESSAGES, null, values);
        db.close();
        return id;
    }

    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGES + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                message.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
                message.setImagePath(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH)));
                message.setTimestamp(cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return messageList;
    }

    public int updateMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, message.getContent());
        values.put(COLUMN_IMAGE_PATH, message.getImagePath());
        values.put(COLUMN_TIMESTAMP, message.getTimestamp());
        return db.update(TABLE_MESSAGES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(message.getId())});
    }

    public void deleteMessage(long messageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(messageId)});
        db.close();
    }
}
