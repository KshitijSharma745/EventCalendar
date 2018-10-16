package example.com.calendarviewdemo2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        int position = getIntent().getIntExtra("position",0);

        final Event event = MainActivity.selectedday.get(position);

        TextView soc=(TextView) findViewById (R.id.society);
        soc.setText (event.getOrgSociety ());

        TextView title=(TextView) findViewById (R.id.title);
        title.setText (event.getName ());

        TextView startd=(TextView) findViewById (R.id.start_date_i);
        startd.setText (event.getStartDate().substring(0,10));

        TextView endd=(TextView) findViewById (R.id.end_date_i);
        endd.setText (event.getEndDate().substring(0,10));

        TextView startt=(TextView) findViewById (R.id.start_time_i);
        startt.setText (event.getStartTime ());

        TextView endt=(TextView) findViewById (R.id.end_time_i);
        endt.setText (event.getEndTime ());

        TextView dett=(TextView) findViewById (R.id.det);
        dett.setText (event.getDetails ());
        dett.setMovementMethod (new ScrollingMovementMethod ());

        TextView locc=(TextView) findViewById (R.id.loc);
        locc.setText (event.getLocation ());

        TextView lnk=(TextView) findViewById (R.id.link);
        lnk.setText (event.getLink ());

        lnk.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(event.getLink ()));
                startActivity(i);

            }
        });



    }
}
