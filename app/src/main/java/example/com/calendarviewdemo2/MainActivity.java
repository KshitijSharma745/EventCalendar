package example.com.calendarviewdemo2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Event>>{

    public static ArrayList<Event> events;

    ListView listView;
    CalendarView calendarView;
    ProgressBar progressBar;
    TextView textView4;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView4 = (TextView)findViewById(R.id.textView4);
        button = (Button) findViewById(R.id.button);

        if(calendarView!=null){
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String msg = "Selected date is "+dayOfMonth +"/"+(month+1)+"/"+year;
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
            });
        }



        Retry(listView);
    }

    public boolean isNetworkAvailable(Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }

    @NonNull
   @Override
    public Loader<ArrayList<Event>> onCreateLoader(int i,@Nullable Bundle args) {
        Log.i("hello","I am in OnCreateLoader");
        return new EventLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Event>> loader, ArrayList<Event> events) {




        progressBar.setVisibility(View.INVISIBLE);

        Log.i("hello","I am in OnLoadfinished");

        if(events == null){
            Log.i("merror","null data in onLoadFinished");
            return;
        }
        EventsAdapter adapter = new EventsAdapter(getApplicationContext(),R.layout.list_item,events);
        if(adapter == null){
            Log.i("merror","null adapter");
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),information.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Event>> loader) {

        Log.i("hello", "I am in onLoaderReset");
        getSupportLoaderManager().restartLoader(0, null,  this);

    }

    public void Retry(View view) {

        boolean b = isNetworkAvailable(this);

        if(!b){

            progressBar.setVisibility(View.INVISIBLE);
            textView4.setText("NO INTERNET CONNECTION");
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
        }
        else{

            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
            textView4.setVisibility(View.INVISIBLE);
            getSupportLoaderManager().initLoader(0, null, this);

        }
    }
}
