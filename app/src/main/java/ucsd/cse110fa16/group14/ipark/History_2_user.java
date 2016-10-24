package ucsd.cse110fa16.group14.ipark;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class History_2_user extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_2_user);

        //ListView trns = (ListView)findViewById(R.id.l);
        String[] transactions = new String[]{ "Illegal Parking: 24", "Illegal Parking: 25", "Illegal Parking: 40"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
            android.R.layout.simple_list_item_1, transactions);
        getListView().setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onItemsSelected(MenuItem item){
        int id= item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
