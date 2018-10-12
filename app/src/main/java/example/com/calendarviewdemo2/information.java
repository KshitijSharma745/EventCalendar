package example.com.calendarviewdemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        int position = getIntent().getIntExtra("position",0);

        Event event = MainActivity.events.get(position);
    }
}
