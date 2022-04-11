package org.me.gcu.wilson_abby_s2130913final;
//Abby Wilson
//S2130913
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ParseData extends AppCompatActivity {
    private String urlSource1 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String urlSource2 = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String urlSource3 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

    private TextView url;
    private String result = "";
    private ListView listView;
    private Toolbar toolbar;
    DatePickerDialog.OnDateSetListener dateSetListener;


    //Current Incidents stored in a list
    List<TrafficData> trafficDataList;
    ListViewAdapter listViewAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trafficdata);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        trafficDataList = new ArrayList<>();
        listView = findViewById(R.id.listView1);

        if (MainActivity.getCurrentIncidents() == true) {
            getSupportActionBar().setTitle("Current Incidents");
            startProgress(urlSource1);
        } else if (MainActivity.getRoadworks() == true) {
            getSupportActionBar().setTitle("Current Roadworks");
            startProgress(urlSource2);
        } else if (MainActivity.getPlannedRoadworks() == true) {
            getSupportActionBar().setTitle("Planned Roadworks");
            startProgress(urlSource3);
        }

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker picker, int year, int month, int dayOfMonth) {

                month = month + 1;

                String dateString = Integer.toString(dayOfMonth) + month + year;

                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMyyyy");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMMM yyyy");

                try {
                    Date searchDate = dateFormat.parse(dateString);
                    String theSearchDate = dateFormat1.format(searchDate);

                    List<TrafficData> newList = new ArrayList<>();
                    for (TrafficData item : trafficDataList)
                    {
                        if (theSearchDate.compareTo(item.startDate) >= 0 && theSearchDate.compareTo(item.endDate) <= 0) {
                            newList.add(item);
                        }

                    }

                    listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.listview, newList);
                    listView.setAdapter(listViewAdapter);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                TrafficData item = (TrafficData)adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), DetailedTrafficData.class);
                intent.putExtra("TrafficData", item);

                startActivity(intent);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem calanderIcon = menu.findItem(R.id.calendarIcon);

        // show the button when some condition is true
        if (MainActivity.getCurrentIncidents() == true) {
            calanderIcon.setVisible(false);
        }


        MenuItem search = menu.findItem(R.id.searchIcon);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.calendarIcon)
        {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                    dateSetListener, year, month, day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            datePickerDialog.show();
        }
        else if (id == R.id.searchIcon) {}
        return true;
    }//End of onOptionsItemsSelected


    public void startProgress(String theUrl) {
        //Run network access on a separate thread
        new Thread(new Task(theUrl)).start();
    }//End of startProgress

    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection urlConnection;
            BufferedReader bufferedReader = null;
            String inputLine = "";
            TrafficData trafficDataItem = null;

            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                urlConnection = aurl.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((inputLine = bufferedReader.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);
                }
                bufferedReader.close();
            } catch (IOException ex) {
                Log.e("MyTag", "IOException");
            }

            if (result != null) {
                try {
                    XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                    parserFactory.setNamespaceAware(true);

                    XmlPullParser xpp = parserFactory.newPullParser();
                    xpp.setInput(new StringReader(result));

                    int event = xpp.getEventType();
                    DateFormat help = new DateFormat();

                    while (event != XmlPullParser.END_DOCUMENT) {
                        switch (event) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:

                                if (xpp.getName().equalsIgnoreCase("item"))
                                {
                                    trafficDataItem = new TrafficData("", "", "", "", "", "");
                                }
                                else if (trafficDataItem != null)
                                {
                                    if (xpp.getName().equalsIgnoreCase("title"))
                                    {
                                        trafficDataItem.setTitle(xpp.nextText().trim());
                                    }
                                    else if(xpp.getName().equals("description")){
                                        event = xpp.next();
                                        trafficDataItem.setDescription(help.getDescription(xpp.getText()));
                                        String[] dates = help.getDates(xpp.getText());

                                        if(dates != null) {
                                            trafficDataItem.setStartDate(help.convertLongDateToShort(dates[0]));
                                            trafficDataItem.setEndDate(help.convertLongDateToShort(dates[1]));
                                        }

                                    }
                                    else if (xpp.getName().equalsIgnoreCase("link"))
                                    {
                                        trafficDataItem.setLink(xpp.nextText().trim());
                                    }
                                    else if (xpp.getName().equalsIgnoreCase("pubDate"))
                                    {
                                        trafficDataItem.setDate(xpp.nextText().trim());
                                    }
                                    else if (xpp.getName().equalsIgnoreCase("point"))
                                    {
                                        String latLonString = xpp.nextText().trim();
                                        String lonString  = latLonString.substring(latLonString.indexOf("-"));
                                        String latString = latLonString.replaceAll(lonString, "");

                                        trafficDataItem.setLat(latString);
                                        trafficDataItem.setLon(lonString);
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (xpp.getName().equalsIgnoreCase("item") && trafficDataItem != null)
                                {
                                    trafficDataItem.setDescription(trafficDataItem.getDescription().replaceAll("<br />", "\\\n"));
                                    trafficDataList.add(trafficDataItem);
                                }
                                break;
                        }
                        event = xpp.next();
                    }
                }
                catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }



        ParseData.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.listview, trafficDataList);
                    listView.setAdapter(listViewAdapter);
                }
            });

        }
    }
}



