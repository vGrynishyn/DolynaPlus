package ua.com.dolina_plus.db_test;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends  AppCompatActivity {

    ListView mList;
    //TextView header;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String selectedFromList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //header = (TextView)findViewById(R.id.header);
        mList = (ListView)findViewById(R.id.list);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RouteActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        sqlHelper = new DatabaseHelper(getApplicationContext());
        // Create DB
        sqlHelper.Create_DB();
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            sqlHelper.Open_DB();
            String query = "select _id, city from " + DatabaseHelper.TABLE_CITY;
            userCursor = sqlHelper.database.rawQuery(query, null);
            String[] headers = new String[]{DatabaseHelper.COLUMN_CITY};
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, userCursor, headers, new int[]{android.R.id.text1}, 0);
            //header.setText("Виберіть місто");
            mList.setAdapter(userAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AboutDialogFragment dlg = new AboutDialogFragment();
        int id = item.getOrder();

        switch (id) {
            case 0:
                new SearchFragment().show(getSupportFragmentManager(), "dlgSearchFragment");
                break;
            case 1:
                dlg.show(getFragmentManager(), "dlg1");
                break;
            case 2:
                finish();
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}

