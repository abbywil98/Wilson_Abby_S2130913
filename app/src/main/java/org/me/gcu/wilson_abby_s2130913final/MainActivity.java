package org.me.gcu.wilson_abby_s2130913final;
//Abby Wilson
//S2130913
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button CurrentIncidentsBtn;
    public Button PlannedRoadworksBtn;
    public Button RoadworksBtn;
    public static Boolean CurrentIncidents;
    public static Boolean PlannedRoadworks;
    public static Boolean Roadworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare all views
        CurrentIncidentsBtn = findViewById(R.id.CurrentIncidentsBtn);
        CurrentIncidentsBtn.setOnClickListener(this);
        PlannedRoadworksBtn = findViewById(R.id.PlannedRoadworksBtn);
        PlannedRoadworksBtn.setOnClickListener(this);
        RoadworksBtn = findViewById(R.id.RoadworksBtn);
        RoadworksBtn.setOnClickListener(this);
    }


    public void onClick (View theView)
    {
        Intent theIntent;
        if (theView == CurrentIncidentsBtn) {
            CurrentIncidents = true;
            PlannedRoadworks = false;
            Roadworks = false;
            theIntent = new Intent(getApplicationContext(), ParseData.class);
            startActivity(theIntent);
        } else if (theView == PlannedRoadworksBtn) {
            CurrentIncidents = false;
            PlannedRoadworks = true;
            Roadworks = false;
            theIntent = new Intent(getApplicationContext(), ParseData.class);
            startActivity(theIntent);
        } else if (theView == RoadworksBtn) {
            CurrentIncidents = false;
            PlannedRoadworks = false;
            Roadworks = true;
            theIntent = new Intent(getApplicationContext(), ParseData.class);
            startActivity(theIntent);
        }


    }
    //returns roadworks
    public static Boolean getRoadworks()
    {
        return Roadworks;
    }

    //returns incidents
    public static Boolean getCurrentIncidents()
    {
        return CurrentIncidents;
    }

    //Returns Planned Roadworks
    public static Boolean getPlannedRoadworks()
    {
        return PlannedRoadworks;
    }



}

