package org.me.gcu.wilson_abby_s2130913final;
//Abby Wilson
//S2130913
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

public class DetailedTrafficData extends ParseData {

    private Toolbar toolbar;
    TrafficData items;
    TextView descriptionText;
    TextView linkText;
    TextView publishDateText;
    TextView startEndDateText;
    TextView latLonText;

    protected void onCreate(Bundle savedInstanceState) {

        //Sets view to detailed list view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedtrafficview);


        items = (TrafficData) getIntent().getSerializableExtra("TrafficData");
        getSupportActionBar().setTitle(items.getTitle());

        //Declare all views
        descriptionText = findViewById(R.id.descriptionText);
        latLonText = findViewById(R.id.latLonText);
        linkText = findViewById(R.id.linkText);
        publishDateText = findViewById(R.id.publishDateText);
        latLonText = findViewById(R.id.latLonText);



        descriptionText.setText(items.getDescription());
        linkText.setText(items.getLink());
        publishDateText.setText(items.getDate());
        latLonText.setText(items.getLatLon());
    }
}

