package example.com.calendarviewdemo2;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.Types.INTEGER;
import static javax.xml.xpath.XPathConstants.STRING;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Event>>{

    public static ArrayList<Event> events;


    ListView listView;
    CalendarView calendarView;
    ProgressBar progressBar;
    TextView textView4;
    Button button;
    String date;
    ImageView imageView;
    String str;
    TextView calendarTv;
    EventsAdapter adapter;

    public static ArrayList<Event> selectedday;

    int clickedDate;

    private int getDayColor(int da) {
        int dayColorResourceId;
        int test=da%7;
        Log.d ("mainSwitch"," test "+test);
        switch ((test)) {
            case 0:
                dayColorResourceId = R.color.day_1;
                break;
            case 1:
                dayColorResourceId = R.color.day_2;
                break;
            case 2:
                dayColorResourceId = R.color.day_3;
                break;
            case 3:
                dayColorResourceId = R.color.day_4;
                break;
            case 4:
                dayColorResourceId = R.color.day_5;
                break;
            case 5:
                dayColorResourceId = R.color.day_6;
                break;
            case 6:
                dayColorResourceId = R.color.day_7;
                break;

            default:
                dayColorResourceId = R.color.def;
                break;
        }
        return ContextCompat.getColor(this, dayColorResourceId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView4 = (TextView)findViewById(R.id.textView4);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        calendarTv = (TextView) findViewById(R.id.calendarTv);

        imageView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        selectedday = new ArrayList<Event>();
        calendarView.setEnabled(false);
        calendarView.setVisibility(View.INVISIBLE);
        calendarTv.setVisibility(View.INVISIBLE);
        //selectedday.clear();
        str = null;
        date = null;

        adapter = new EventsAdapter(getApplicationContext(),R.layout.list_item,selectedday);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),information.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        if(calendarView!=null){

            long todaydate = calendarView.getDate();

            Date dateObject = new Date(todaydate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = dateFormat.format(dateObject);
            int index = 0;

            for(int j = 0; j<date.length();++j){
                if(date.charAt(j) == ' '){
                    index = j; break;
                }
            }

            date = date.substring(0,index);
            clickedDate = convertTOInt(date.substring(0,2));
            listView.setBackgroundColor (getDayColor (clickedDate));
           // appbarSetColor(getDayColor (clickedDate));
            str = date.substring(6,10)+"-"+date.substring(3,5)+"-"+date.substring(0,2);

            Log.i("hello",str);
        }




        Retry(listView);
    }

//    private void appbarSetColor(int dayColor) {
//        ActionBar actionBar;
//
//        actionBar = getActionBar();
//        ColorDrawable colorDrawable = new ColorDrawable(dayColor);
//        actionBar.setBackgroundDrawable(colorDrawable);
//        //Color.parseColor("#93E9FA")
//    }

    private void print() {
        for(int i = 0; i<selectedday.size(); ++i){
            Log.i("helloyo",selectedday.get(i).getName());
        }
    }

    public boolean isNetworkAvailable(Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }

    @NonNull
   @Override
    public Loader<ArrayList<Event>> onCreateLoader(int i,@Nullable Bundle args) {
        Log.i("hello","I am in OnCreateLoader");
        Retry2();
        return new EventLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Event>> loader, ArrayList<Event> events) {

        Log.i("hello","I am in OnLoadfinished");

        if(events == null){
            Log.i("merror","null data in onLoadFinished");
            Retry2();
            return;
        }
        progressBar.setVisibility(View.INVISIBLE);
        calendarView.setVisibility(View.VISIBLE);
        calendarView.setEnabled(true);
        calendarTv.setVisibility(View.INVISIBLE);
        Retry2();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                ++month;
                selectedday.clear();
                String msg = "Selected date is "+dayOfMonth +"/"+(month)+"/"+year;
                if(dayOfMonth<10){

                    if(month<10){
                        date = year+"-0"+(month)+"-0"+dayOfMonth;
                    }
                    else{
                        date = year+"-"+(month)+"-0"+dayOfMonth;
                    }
                }
                else{

                    if(month<10){
                        date = year+"-0"+(month)+"-"+dayOfMonth;
                    }
                    else{
                        date = year+"-"+(month)+"-"+dayOfMonth;
                    }
                }


                getEventsInSelectedday();
                if(selectedday==null||selectedday.size()==0){
                    sorryNoEvents();
                }
                else{
                    gotEvents();
                }
                print();
                //Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

                //String msg = "Selected date is "+dayOfMonth +"/"+(month+1)+"/"+year;
                //Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                clickedDate=dayOfMonth;
                //Log.d ("mainActivity",date+" ");
                listView.setBackgroundColor (getDayColor (clickedDate));
                // appbarSetColor(getDayColor (clickedDate));
                //Log.d ("mainActivity",date+" this is after color ");

            }
        });


        getEventsInSelectedday();
        if(selectedday==null||selectedday.size()==0){
            sorryNoEvents();
        }
        else{
            gotEvents();
        }
        print();
    }

    private void sorryNoEvents(){

        Retry2();
        listView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);


    }

    private void gotEvents() {
        Retry2();
        imageView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    private void getEventsInSelectedday() {
        Retry2();
        selectedday.clear();
        if(date == null)
        {
            if(str==null){
                return;
            }
            else{
                date = str;
            }
        }
        Log.i("hello","selected date "+date + "--"+ date.length());

        for(int i = 0; i<events.size(); ++i){
            String startDate = events.get(i).getStartDate();
            String endDate = events.get(i).getEndDate();

            int startYear = convertTOInt(startDate.substring(0,4));
            int startDay = convertTOInt(startDate.substring(8,10));
            int startMonth = convertTOInt(startDate.substring(5,7));

            int endYear = convertTOInt(endDate.substring(0,4));
            int endDay = convertTOInt(endDate.substring(8,10));
            int endMonth = convertTOInt(endDate.substring(5,7));

            int todayYear = convertTOInt(date.substring(0,4));
            int todayDay = convertTOInt(date.substring(8,10));
            int todayMonth = convertTOInt(date.substring(5,7));

            if(todayYear>=startYear&&todayYear<=endYear){
                if(todayMonth>=startMonth&&todayMonth<=endMonth){
                    if(todayDay>=startDay&&todayDay<=endDay){
                        selectedday.add(events.get(i));
                        Log.i("hello","selectedday - " + events.get(i).getName());
                    }
                }
            }

        }

    }

    private int convertTOInt(String str){
        int x = 0;
        for(int i = 0; i<str.length(); ++i){
            x = x*10 + str.charAt(i)-48;
        }
        return x;
    }
    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Event>> loader) {
        Retry2();
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
            calendarView.setEnabled(false);
            listView.setVisibility(View.INVISIBLE);
        }
        else{
            calendarView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
            calendarView.setEnabled(true);
            textView4.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            getSupportLoaderManager().initLoader(0, null, this);

        }
    }
    private void Retry2(){
        boolean b = isNetworkAvailable(this);

        if(!b){

            progressBar.setVisibility(View.INVISIBLE);
            textView4.setText("NO INTERNET CONNECTION");
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
            calendarView.setEnabled(false);
            listView.setVisibility(View.INVISIBLE);
        }
    }
}
