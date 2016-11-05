package ucsd.cse110fa16.group14.ipark;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class History_2_user extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_2_user);
        String[] transactions = {"Illegal Parking: 24", "Illegal Parking:25", "Illegal Parking:40"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, transactions);
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);

    }

}
