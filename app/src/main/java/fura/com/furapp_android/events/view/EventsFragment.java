package fura.com.furapp_android.events.view;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import fura.com.furapp_android.R;
import fura.com.furapp_android.events.model.EventsDataView;
import fura.com.furapp_android.events.model.helpers.Event;
import fura.com.furapp_android.events.presenter.EventsPresenter;
import fura.com.furapp_android.events.view.helpers.EventsAdapter;

public class EventsFragment extends Fragment implements EventsInterface {

    //region GLOBAL FIELDS
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private EventsPresenter eventsPresenter;
    //endregion

    //region CLASS CONSTRUCTORS
    //Initialize the presenter with this fragment
    public EventsFragment(){
        eventsPresenter = new EventsPresenter(this);
    }
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // Initialize adapter to show events information
        adapter =new EventsAdapter(getContext(), eventsPresenter);
        //Create the layout to the event cards
        final LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Assign the adapter to recycler view
        recyclerView.setAdapter(adapter);
        eventsPresenter.UpdateEvents();
        return view;
    }

    //region Implementation of the interface EventsInterface
    // Gets event list, assign it to the adapter and refresh the view
    @Override
    public void updateAdapter(List<Event> _events) {
        adapter.eventList = _events;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateAssistButton(EventsAdapter.MyViewHolder eventHolder) {
        eventHolder._btn_attend.setBackgroundColor(Color.GREEN);
        eventHolder._btn_attend.setText(R.string.attend_button_check);
        eventHolder._btn_attend.setEnabled(false);
    }

    @Override
    public void notifyUser(String strMessage, Context context) {
        Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayDataTextView(String strData, TextView view) {
        view.setText(strData);
    }

    @Override
    public void displayCoverImageView(String strSource, ImageView view, Context context) {
        Picasso.with(context).load(strSource).into(view);
    }
    //endregion

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
