package fura.com.furapp_android.view.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import fura.com.furapp_android.R;
import fura.com.furapp_android.model.FacebookRequest;
import fura.com.furapp_android.model.dataModels.EventHelpers.Event;

/**
 * Created by ramon on 24/11/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private Context context;
    public List<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        // Create the view's objects
        public ImageView cover;
        public TextView name, city, date, start_time, location, idEvent;
        // Button variable to make Attend function.
        public Button _btn_attend;
        public MyViewHolder(View view){
            super(view);
            cover=view.findViewById(R.id.img_cover_card_events);
            name =view.findViewById(R.id.txv_name_card_events);
            city =view.findViewById(R.id.txv_city_card_events);
            date=view.findViewById(R.id.txv_date_card_events);
            start_time =view.findViewById(R.id.txv_start_time_card_events);
            location=view.findViewById(R.id.txv_location_card_events);
            // Variable for the id of the event.
            idEvent=view.findViewById(R.id.txv_id_card_events);
            // Initialization of the button.
            _btn_attend=view.findViewById(R.id.btn_attend_event_card);

            // Listener of the button for the Attend function.
            _btn_attend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertAttendBuilder = new AlertDialog.Builder(v.getContext());

                    alertAttendBuilder.setMessage("¿Desea asistir al evento seleccionado?");

                    alertAttendBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Code
                            FacebookRequest.AttendEventFromFacebook(idEvent.getText().toString());
                        }
                    });

                    alertAttendBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Code
                        }
                    });



                    AlertDialog alertAttendMessage = alertAttendBuilder.create();
                    alertAttendMessage.show();

                }
            });
        }
    }
    public EventsAdapter(Context context){
        //Get the context
        this.context=context;
        //Initialize the list of events
        this.eventList=new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //When create the view holder it inflates the event card
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);
        return new MyViewHolder(item);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        try {
            //Gets the object to show
            Event event = eventList.get(position);
            //Set the event properties in the view
            //cover
            //Set the event's id.
            holder.idEvent.setText(event.getId());
            holder.name.setText(event.getName());
            holder.city.setText(event.getPlace().getLocation().getCity());
            holder.start_time.setText(event.getStart_time());
            holder.date.setText(event.getStart_date());
            holder.location.setText(event.getPlace().getName());
        }
        catch (Exception e){ }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
