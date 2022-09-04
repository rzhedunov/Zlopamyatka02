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
    public static final String NAME = "name";

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
        db.execSQL("CREATE TABLE " + TABLE_NAME1
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT);");

        ContentValues values = new ContentValues();

        values.put(NAME, "Театр");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Пиво");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Гости");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Грипп");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Комм.платежи");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Выкл.света");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Выкл.хол.воды");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Выкл.гор.воды");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Занял в долг");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Дождь");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Истерика");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "ГИБДД штраф");
        db.insert(TABLE_NAME1, null, values);
        values.put(NAME, "Благотворительность");
        db.insert(TABLE_NAME1, null, values);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);

        onCreate(db);
    }

}