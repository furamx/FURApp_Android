package fura.com.furapp_android.view.helpers;

import android.animation.ObjectAnimator;
import android.content.Context;
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
import fura.com.furapp_android.domain.StringFormater;
import fura.com.furapp_android.model.dataModels.EventHelpers.Event;

/**
 * Created by ramon on 24/11/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private Context context;
    public List<Event> eventList;
    private SparseBooleanArray expandState=new SparseBooleanArray();

    private void ExpandableRecyclerAdapter(List<Event> events) {
        this.eventList = events;
        //set initial expanded state to false
        for (int i = 0; i < events.size(); i++) {
            expandState.append(i, false);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        // Create the view's objects
        public ImageView cover;
        public TextView name, city, date, start_time, location, description;
        public ImageButton expand_button;
        public LinearLayout expandable_layout;
        public MyViewHolder(View view){
            super(view);
            cover=view.findViewById(R.id.img_cover_card_events);
            name =view.findViewById(R.id.txv_name_card_events);
            city =view.findViewById(R.id.txv_city_card_events);
            date=view.findViewById(R.id.txv_date_card_events);
            start_time =view.findViewById(R.id.txv_start_time_card_events);
            location=view.findViewById(R.id.txv_location_card_events);
            description=view.findViewById(R.id.txv_description_card_events);
            //
            expand_button=view.findViewById(R.id.btn_drop_down_event_card);
            expandable_layout=view.findViewById(R.id.lyt_expandable_details);
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position){
        try {
            viewHolder.setIsRecyclable(false);
            //Gets the object to show
            Event event = eventList.get(position);
            //Set the event properties in the view
            if(event.getCover()!=null)
                Picasso.with(context).load(event.getCover().getSource()).into(viewHolder.cover);
            if(event.getName()!=null)
                viewHolder.name.setText(event.getName());
            if(event.getPlace().getLocation() !=null)
                viewHolder.city.setText(event.getPlace().getLocation().getCity());
            if(event.getStart_time()!=null)
                viewHolder.start_time.setText(StringFormater.ToHour(event.getStart_time()));
            if(event.getPlace().getName()!=null)
                viewHolder.location.setText(event.getPlace().getName());
            if(event.getStart_time()!=null)
                viewHolder.date.setText(StringFormater.ToDate(event.getStart_time()));
            if(event.getDescription()!=null)
                viewHolder.description.setText(event.getDescription());
            //Check expand state
            final boolean isExpanded=expandState.get(position);
            viewHolder.expandable_layout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
            viewHolder.expand_button.setRotation(expandState.get(position)?180f:0f);
            viewHolder.expand_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(final View v){
                    OnClickButton(viewHolder.expandable_layout,viewHolder.expand_button,position);
                }
            });
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private void OnClickButton(final LinearLayout expandableLayout,final ImageButton expandButton, final int i){
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
    private ObjectAnimator CreateRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
