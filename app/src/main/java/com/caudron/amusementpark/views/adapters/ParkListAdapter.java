package com.caudron.amusementpark.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Park;

import java.util.List;

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkViewHolder> {

    private OnItemClickListener mListener;
    private List<Park> mParkList;

    public ParkListAdapter(List<Park> parkList) {
        mParkList = parkList;
    }

    public void updateParkList(List<Park> parkList) {
        mParkList = parkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_park_card, parent, false);
        return new ParkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkViewHolder holder, int position) {
        Park park = mParkList.get(position);
        holder.mTextViewParkName.setText(park.getName());
        if(park.getCountry() != null) {
            holder.mTextViewParkLocation.setText(park.getCountry().getName());
        } else {
            holder.mTextViewParkLocation.setText("");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(park);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mParkList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Park park);
    }

    public static class ParkViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewParkName;
        private TextView mTextViewParkLocation;

        public ParkViewHolder(View itemView) {
            super(itemView);

            mTextViewParkName = itemView.findViewById(R.id.text_view_park_name);
            mTextViewParkLocation = itemView.findViewById(R.id.text_view_park_location);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}


