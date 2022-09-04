package ru.zhedunov.zlopamyatka02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import ru.zhedunov.zlopamyatka02.db.ZlopamyatkaDbHelper;

public class MainActivity extends AppCompatActivity {


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


        ArrayList<String> events = new ArrayList<>();

        if (db != null) {
            Toast.makeText(getApplicationContext(),
                    "Connected to DB Zlopamyatka ", Toast.LENGTH_LONG).show();

            Cursor cursor = db.query(ZlopamyatkaDbHelper.TABLE_NAME1, null,null,
                    null,null,null,null);

            ArrayList<String> list = new ArrayList<String>();
            if ((cursor != null) && (cursor.getCount()>0)) {
                list = new ArrayList<String>();
                cursor.moveToFirst();

                do {
                    String item = cursor.getString(1);
                    events.add(item);

                } while (cursor.moveToNext());
            }

        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Error connecting database!", Toast.LENGTH_LONG).show();
        }
        db.close();


        ListView listView = findViewById(R.id.listView);

//        final String[] catNames = new String[] {
//                "Театр", "Пиво", "Гости", "Грипп", "Комм.платежи",
//                "Выкл.света", "Выкл.хол.воды", "Выкл гор.воды", "Занял в долг", "Дождь",
//                "Истерика", "ГИБДД штраф", "Благотворительность", "Театр", "Пиво", "Гости", "Грипп", "Комм.платежи",
//                "Выкл.света", "Выкл.хол.воды", "Выкл гор.воды", "Занял в долг", "Дождь",
//                "Истерика", "ГИБДД штраф", "Благотворительность"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events);
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