package ua.com.dolina_plus.db_test;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ua.com.dolina_plus.db_test.fragments.FragmentA;

public class SearchResultActivity extends AppCompatActivity {

    ListView mList;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String selectedFromList;
    String city, time;
    int dayType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mList = (ListView)findViewById(R.id.listSearchResult);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city = extras.getString("city");
            time = extras.getString("Time");
            dayType = extras.getInt("dayType");
          //  endTime = extras.getString("endTime");
        }



        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultActivity.this, MapsActivity.class);
                String city = "", address = "";
                sqlHelper.Open_DB();
                String query = "select _id, address, city from " + DatabaseHelper.TABLE_ROUTE + " where _id=" + String.valueOf(id);
                userCursor = sqlHelper.database.rawQuery(query, null);
                while (userCursor.moveToNext()) {
                    city = userCursor.getString(userCursor.getColumnIndex("city"));
                    address = userCursor.getString(userCursor.getColumnIndex("address"));
                }
                userCursor.close();

                intent.putExtra("id", id);
                intent.putExtra("city", city);
                intent.putExtra("address", address);
                if(FragmentA.isOnline(SearchResultActivity.this)) {
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(SearchResultActivity.this);
                    dlgAlert.setMessage(R.string.failed_conection_msg);
                    dlgAlert.setTitle(R.string.failed_conection_title);
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.create().show();
                }
            }
        });

        sqlHelper = new DatabaseHelper(getApplicationContext());
        // Create DB
        sqlHelper.Create_DB();
    }
    public void onResume() {
        super.onResume();
        try {
            sqlHelper.Open_DB();
            String query = "select _id, address, timeperiod from " + DatabaseHelper.TABLE_ROUTE + " where city='"+ city + "' AND days_id="+ String.valueOf(dayType) + " AND substr(timeperiod, 1, 5) BETWEEN '"+ time + "' AND '"+ time +":59' order by timeperiod";
            userCursor = sqlHelper.database.rawQuery(query, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_ADDRESS, DatabaseHelper.COLUMN_TIME};
            userAdapter = new SimpleCursorAdapter(SearchResultActivity.this, android.R.layout.two_line_list_item, userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            mList.setAdapter(userAdapter);
        }
        catch (SQLException ex){}
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Close DB
        sqlHelper.database.close();
        userCursor.close();
    }


}
