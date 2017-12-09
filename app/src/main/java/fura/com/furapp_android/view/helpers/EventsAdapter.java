package fura.com.furapp_android.view.helpers;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import fura.com.furapp_android.R;
import fura.com.furapp_android.domain.StringFormatter;
import fura.com.furapp_android.model.dataModels.EventHelpers.Event;
import fura.com.furapp_android.view.MapsActivity;

/**
 * Created by ramon on 24/11/17.
 */

//Adapter to the events recycler view
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    //region GLOBAL FIELDS
    private Context context;
    public List<Event> eventList;
    private Event event;
    //Event cards expand state
    private SparseBooleanArray expandState=new SparseBooleanArray();
    //endregion

    //region CLASS CONSTRUCTORS
    public EventsAdapter(Context context){
        //Get the context
        this.context=context;
        //Initialize the list of events
        this.eventList=new ArrayList<>();
    }
    //endregion

    //region VIEW

    //Gets the count of events
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    //Create the view's object
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //fields
        public ImageView cover;
        public TextView name, city, date, start_time, location, description, end_time, street;
        public ImageButton expand_button;
        public LinearLayout expandable_layout,place_layout;

        public MyViewHolder(View view){
            super(view);
            //image view
            cover=view.findViewById(R.id.img_cover_card_events);
            //text view
            name =view.findViewById(R.id.txv_name_card_events);
            city =view.findViewById(R.id.txv_city_card_events);
            date=view.findViewById(R.id.txv_date_card_events);
            start_time =view.findViewById(R.id.txv_start_time_card_events);
            location=view.findViewById(R.id.txv_location_card_events);
            description=view.findViewById(R.id.txv_description_card_events);
            end_time=view.findViewById(R.id.txv_end_time_card_events);
            street=view.findViewById(R.id.txv_street_card_events);
            //button
            expand_button=view.findViewById(R.id.btn_drop_down_event_card);
            //layout
            expandable_layout=view.findViewById(R.id.lyt_expandable_details);
            place_layout=view.findViewById(R.id.lyt_place_card_event);
        }
    }
    //When create the view holder it inflates the event card
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position){
        try {
            viewHolder.setIsRecyclable(false);
            //Gets the object to show
            event = eventList.get(position);
            //Set the event properties in the view
            if(event.getCover()!=null)
                Picasso.with(context).load(event.getCover().getSource()).into(viewHolder.cover);
            if(event.getName()!=null)
                viewHolder.name.setText(event.getName());
            if(event.getPlace().getLocation()!=null){
                viewHolder.city.setText(event.getPlace().getLocation().getCity());
                viewHolder.street.setText(event.getPlace().getLocation().getStreet());
            }
            if(event.getStart_time()!=null)
                viewHolder.start_time.setText(context.getString(R.string.start_label)+ StringFormatter.ToHour(event.getStart_time()));
            if(event.getEnd_time()!=null)
                viewHolder.end_time.setText(context.getString(R.string.end_label)+ StringFormatter.ToHour(event.getEnd_time()));
            if(event.getPlace()!=null)
                viewHolder.location.setText(event.getPlace().getName());
            if(event.getStart_time()!=null)
                viewHolder.date.setText(StringFormatter.ToDate(event.getStart_time()));
            if(event.getDescription()!=null)
                viewHolder.description.setText(event.getDescription());
        } catch (Exception e) {}
        //Check expand state
        final boolean isExpanded=expandState.get(position);
        //If is expand it shows the content in expandable layout
        viewHolder.expandable_layout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.expand_button.setRotation(expandState.get(position)?180f:0f);
        //When the expand button is pressed
        viewHolder.expand_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                ExpandButton_OnClick(viewHolder.expandable_layout,viewHolder.expand_button,position);
            }
        });
        //When the layout with place details is pressed
        viewHolder.place_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(event.getPlace().getLocation()!=null)
                    PlaceLayout_OnClick(event.getPlace().getLocation().getLongitude(),event.getPlace().getLocation().getLatitude());
            }
        });
    }
    //endregion

    //region EXPANDABLE CARD

    //Ser initial expanded state to false for each card
    private void ExpandableRecyclerAdapter(List<Event> events) {
        this.eventList = events;
        for (int i = 0; i < events.size(); i++) {
            expandState.append(i, false);
        }
    }

    //Shows or hide the expandable layout when the button is pressed
    private void ExpandButton_OnClick(final LinearLayout expandableLayout, final ImageButton expandButton, final int i){
        if (expandableLayout.getVisibility() == View.VISIBLE){
            CreateRotateAnimator(expandButton, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        }else{
            CreateRotateAnimator(expandButton, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }

    //Creates the animation when the drop down button is pressed
    private ObjectAnimator CreateRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    //When the place details are pressed it launch a map activity
    private void PlaceLayout_OnClick(double longitude,double latitude){
        //Create the intent
        Intent mapsIntent=new Intent(context,MapsActivity.class);
        //Set the parameters
        mapsIntent.putExtra("longitude",longitude);
        mapsIntent.putExtra("latitude",latitude);
        //Start map activity
        context.startActivity(mapsIntent);
    }

    //endregion

}
