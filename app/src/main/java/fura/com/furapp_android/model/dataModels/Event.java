package fura.com.furapp_android.model.dataModels;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramon on 22/11/17.
 */

public class Event {
    //region Fields
    private String name;
    private String start_time;
    private String place_name;
    private String location;
    private String city;
    private String country;
    private String state;
    private String street;
    private String description;
    private String longitude;
    private String latitude;
    //endregion

    //region Encapsulation
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getStart_time() { return start_time; }

    public void setStart_time(String start_time) { this.start_time = start_time; }

    public String getPlace_name() { return place_name; }

    public void setPlace_name(String place_name) { this.place_name = place_name; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description;}

    public String getLongitude() { return longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getLatitud() { return latitude; }

    public void setLatitud(String latitud) { this.latitude = latitud; }
    //endregion

    //region Constructors
    public Event(){}

    public Event(String _name, String _start_time, String _place_name, String _location, String _city, String _country, String _state, String _street, String _description, String _longitude, String _latitude){
        this.name=_name;
        this.start_time=_start_time;
        this.place_name=_place_name;
        this.location=_location;
        this.city=_city;
        this.country=_country;
        this.state=_state;
        this.street=_street;
        this.description=_description;
        this.longitude=_longitude;
        this.latitude=_latitude;
    }

    public Event(JSONObject jsonObject){
        try {
            this.name=jsonObject.getString("name");
            this.start_time=jsonObject.getString("start_time");
            /*this.place_name=_place_name;
            this.location=_location;
            this.city=_city;
            this.country=_country;
            this.state=_state;
            this.street=_street;
            this.description=_description;
            this.longitude=_longitude;
            this.latitude=_latitude;*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
