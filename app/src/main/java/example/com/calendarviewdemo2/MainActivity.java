package example.com.calendarviewdemo2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);

        if(calendarView!=null){
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String msg = "Selected date is "+dayOfMonth +"/"+(month+1)+"/"+year;
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }



        ArrayList<String> events = new ArrayList<String> ();
        events.add("bb");
        events.add("ff");
        events.add("cc");
        events.add("hey");
        events.add("ji");
        // Find a reference to the {@link ListView} in the layout
        ListView eventsListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EventsAdapter adapter = new EventsAdapter (this, events);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        eventsListView.setAdapter(adapter);
    }
}
