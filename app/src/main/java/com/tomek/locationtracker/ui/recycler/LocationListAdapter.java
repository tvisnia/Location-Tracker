package com.tomek.locationtracker.ui.recycler;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomek.locationtracker.R;
import com.tomek.locationtracker.model.Coordinate;
import com.tomek.locationtracker.model.LocationData;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private List<LocationData> locationList;

    public LocationListAdapter() {
        this.locationList = new ArrayList<>();
    }

    public void addNewItem(Location location, String city, RecyclerView recycler) {
        locationList.add(0, new LocationData(city, location, DateTime.now(), new Coordinate(location.getLongitude(), location.getLatitude())));
        notifyItemInserted(0);
        recycler.smoothScrollToPosition(0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationData locationData = locationList.get(position);
        holder.bind(locationData);
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private TextView coordinates;
        private TextView dateAndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.city);
            coordinates = (TextView) itemView.findViewById(R.id.coordinates);
            dateAndTime = (TextView) itemView.findViewById(R.id.date);

        }

        public void bind(LocationData locationListItem) {
            city.setText(locationListItem.city);
            coordinates.setText(locationListItem.coordinate.toString());
            dateAndTime.setText(locationListItem.getDateAndTime());
        }
    }
}
