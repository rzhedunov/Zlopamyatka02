package ru.zhedunov.zlopamyatka02.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import androidx.annotation.Nullable;

public class ZlopamyatkaDbHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_ZLOPAMYATKA = "zlopamyatka.db";
    public static final String TABLE_NAME1 = "events";
    public static final String TABLE_NAME2 = "facts";
    public static final String VIEW_NAME = "v_facts";
    public static final String NAME = "name";
//    public static final String NAME2 = "val";

//    public static final String SPECIAL_DATA_DIR = "SpecialDataDirectory";

    private Context context;

    //Конструктор
    public ZlopamyatkaDbHelper(Context con) {
        super(con, DB_ZLOPAMYATKA, null, 1);
        this.context = con;
    }

    //Конструктор, который не нужен вроде
    public ZlopamyatkaDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table events(_id INTEGER PRIMARY KEY AUTOINCREMENT, name text );");

        ContentValues values = new ContentValues();
        values.put(NAME, "Театр");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Пиво");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Гости");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Грипп");
        db.insert(TABLE_NAME1, null, values);

//        db.execSQL("create table facts(_id INTEGER PRIMARY KEY AUTOINCREMENT, event_id INTEGER, event_time INTEGER);");
        db.execSQL("create table facts(_id INTEGER PRIMARY KEY AUTOINCREMENT, event_id INTEGER, event_time INTEGER, FOREIGN KEY (event_id) REFERENCES events(id));");
//
        ContentValues values2 = new ContentValues();
        values2.put("event_id", 1);
        values2.put("event_time", 12345);
        db.insert("facts", null, values2);
        values2.put("event_id", 1);
        values2.put("event_time", 12346);
        db.insert("facts", null, values2);
        values2.put("event_id", 2);
        values2.put("event_time", 12347);
        db.insert("facts", null, values2);

        String sqlquery = "CREATE VIEW v_facts AS " +
                "SELECT events.name, events._id, facts._id as facts_id, facts.event_time " +
                "FROM events, facts " +
                "WHERE events._id = facts.event_id ORDER BY facts.event_time DESC;";

        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);

        onCreate(db);
    }

}