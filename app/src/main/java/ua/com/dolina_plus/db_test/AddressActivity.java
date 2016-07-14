package ua.com.dolina_plus.db_test;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AddressActivity extends AppCompatActivity {
    DatabaseHelper sqlHelper;
    long CityId;
    String Route;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CityId = extras.getLong("id");
            Route = extras.getString("route");
        }

        mListView=(ListView)findViewById(R.id.address_list);
        sqlHelper = new DatabaseHelper(getApplicationContext());

        // создаем базу данных
        sqlHelper.Create_DB();
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            sqlHelper.Open_DB();
            String query = "select _id, address, timeperiod from " + DatabaseHelper.TABLE_ROUTE + " where city_id="+ String.valueOf(CityId) + " and route='"+Route+"'" ;//and route=route_id
            userCursor = sqlHelper.database.rawQuery(query, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_ADDRESS, DatabaseHelper.COLUMN_TIME};
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
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
