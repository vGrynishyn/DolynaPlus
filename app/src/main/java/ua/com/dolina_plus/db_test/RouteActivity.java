package ua.com.dolina_plus.db_test;

import android.content.Intent;
import android.database.SQLException;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class RouteActivity extends AppCompatActivity {

    ListView mListView;
    //TextView header;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    long CityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CityId = extras.getLong("id");
        }

     //   header = (TextView)findViewById(R.id.district_header);
        mListView = (ListView)findViewById(R.id.district_list);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Route = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
                Intent intent1 = new Intent(getApplicationContext(), TabActivity.class);
                intent.putExtra("id", CityId);
                intent.putExtra("route", Route);
                //startActivity(intent);
                startActivity(intent1);
           }
        });

        sqlHelper = new DatabaseHelper(getApplicationContext());

        // создаем базу данных
        sqlHelper.Create_DB();
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            sqlHelper.Open_DB();
            String query = "select _id, route from " + DatabaseHelper.TABLE_ROUTE + " where city_id="+ String.valueOf(CityId);
            userCursor = sqlHelper.database.rawQuery(query, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_ROUTE};
            //String[] headers = new String[]{DatabaseHelper.COLUMN_ROUTE, DatabaseHelper.COLUMN_DISTRICT };
            //userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, userCursor, headers, new int[]{android.R.id.text1}, 0);
            mListView.setAdapter(userAdapter);
        }
        catch (SQLException ex){}
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
        sqlHelper.database.close();
        userCursor.close();
    }
}




