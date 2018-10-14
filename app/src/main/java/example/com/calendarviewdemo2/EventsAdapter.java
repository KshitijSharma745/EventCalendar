package example.com.calendarviewdemo2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsAdapter extends ArrayAdapter<Event> {

    private int resourceLayout;
    private Context mContext;

    public EventsAdapter(Context context, int resource, ArrayList<Event> events) {

        super(context,resource,events);
        this.resourceLayout = resource;
        this.mContext = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            try {
                listItemView = LayoutInflater.from (mContext).inflate (
                        resourceLayout, parent, false);
            }
            catch (RuntimeException e) {
                Log.d ("EventAdapter", "there is error");
            }
        }


        Event currentEvent = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = listItemView.findViewById(R.id.event_name);
        nameTextView.setText(currentEvent.getName());

        TextView startTime = (TextView) listItemView.findViewById(R.id.start_time);
        TextView endTime = (TextView) listItemView.findViewById(R.id.end_time);

        startTime.setText(currentEvent.getStartTime());
        endTime.setText(currentEvent.getEndTime());

        return listItemView;
    }

}




