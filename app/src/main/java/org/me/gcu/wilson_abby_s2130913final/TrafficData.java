package org.me.gcu.wilson_abby_s2130913final;
//Abby Wilson
//S2130913
import java.io.Serializable;
import java.util.Date;

public class TrafficData implements Serializable {

    //Declare variables
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String latitude;
    private String longitude;

    public Date setStartDate;
    public Date setEndDate;
    public String startDate;
    public String endDate;


    public TrafficData(String title, String description, String link, String pubDate, String lat, String lon) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.latitude = lat;
        this.longitude = lon;
    }

    //Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return "Visit our website for more details: " + link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }



    public String getLat() {
        return latitude;
    }

    public void setLat(String lat) {
        this.latitude = lat;
    }

    public String getLon() {
        return longitude;
    }

    public void setLon(String lon) {
        this.longitude = lon;
    }

    public String getDate() {
        return "Date Published: " + pubDate;
    }

    public void setDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLatLon() {
        return "Latitude is: " + latitude + "" + "" + "" + "" + "Longitude is: " + longitude;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public int calculateDays(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            int days = ((int) ((date2.getTime() / (24 * 60 * 60 * 1000)) - (int) (date1.getTime() / (24 * 60 * 60 * 1000))));
            return days;
        } else {
            return 0;
        }
    }

    //Calculate duration of works in days
    public int getDurationInDays() {
        int days = calculateDays(setStartDate, setEndDate);
        return days;
    }

    public String getStartEndDate()
    {
        return ("Start date: " + startDate + " " + " " + " " + "End Date: " + endDate
                + "\n" + "Estimated Time of Works: " + getDurationInDays() + " days");
    }
}



