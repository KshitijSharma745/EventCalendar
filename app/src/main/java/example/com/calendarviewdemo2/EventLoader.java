package example.com.calendarviewdemo2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

public class EventLoader extends AsyncTaskLoader<ArrayList<Event>>{

    public static final String eventsApi = "https://csievent.herokuapp.com/api/event";

    public EventLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.i("message","I am in OnStartLoading");

        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Event> loadInBackground() {
        Log.i("message","I am in LoadInBackgroung");
        MainActivity.events = Utils.fetchEvents(eventsApi);

        return MainActivity.events;
    }
}
