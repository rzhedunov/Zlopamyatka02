package ru.zhedunov.zlopamyatka02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
//import android.util.Log;
import ru.zhedunov.zlopamyatka02.db.ZlopamyatkaDbHelper;

public class MainActivity extends AppCompatActivity {

//    private static final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Подключение к БД или ее создание - проверка доступности БД
        SQLiteDatabase db2 = new ZlopamyatkaDbHelper(getApplicationContext()).getWritableDatabase();
        if (db2 != null) {
            Toast.makeText(getApplicationContext(),
                    "DB Zlopamyatka is created", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error create database!", Toast.LENGTH_LONG).show();
        }
        db2.close();

        //Подключение к БД - для вывода списка
        SQLiteDatabase db = new ZlopamyatkaDbHelper(getApplicationContext()).getReadableDatabase();

        ArrayList<String> list = new ArrayList<>();

        if (db != null) {
            Toast.makeText(getApplicationContext(),
                    "Connected to DB Zlopamyatka ", Toast.LENGTH_LONG).show();

            Cursor cursor = db.query(ZlopamyatkaDbHelper.VIEW_NAME, null,null,
                    null,null,null,null);

            if ((cursor != null) && (cursor.getCount()>0)) { //Чтение из таблицы
                cursor.moveToFirst();
                do {
                    String item = cursor.getString(0);
                    String item2 = cursor.getString(1);
                    String item3 = cursor.getString(2);
                    String item4 = cursor.getString(3);
                    list.add(item+" "+item2+" "+item3+" "+item4);
                } while (cursor.moveToNext());
            }
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Error connecting database!", Toast.LENGTH_LONG).show();
        }
        db.close();

        ListView listView = findViewById(R.id.listView);
//        final String[] list = new String[] {"Театр", "Пиво", "Гости", "Грипп"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Формирование меню приложения
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Формирование пунктов меню приложения
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}