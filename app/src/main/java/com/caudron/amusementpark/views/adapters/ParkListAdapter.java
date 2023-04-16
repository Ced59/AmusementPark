package com.caudron.amusementpark.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Park;
import com.caudron.amusementpark.utils.UtilsCountries;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkViewHolder> {

    private OnItemClickListener mListener;
    private List<Park> mParkList;
    private Park mSelectedPark;
    private Context mContext;

    public ParkListAdapter(Context context, List<Park> parkList) {
        mParkList = parkList;
        mContext = context;
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
        holder.mTextViewParkLocation.setText(UtilsCountries.getLocaleCountryName(mContext.getApplicationContext(), park.getCountryCode()));


        // Vérifier si le parc est sélectionné et définir la couleur de fond en conséquence
        if (mSelectedPark != null && mSelectedPark.equals(park)) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.teal_700));
            holder.cardView.setStrokeColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_blue_lighter));
            holder.cardView.setStrokeColor(ContextCompat.getColor(mContext, R.color.white));
        }

        // Ajouter un OnClickListener pour inverser l'état de la sélection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPark = park;
                notifyDataSetChanged();
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
        public MaterialCardView cardView;

        public ParkViewHolder(View itemView) {
            super(itemView);

            mTextViewParkName = itemView.findViewById(R.id.text_view_park_name);
            mTextViewParkLocation = itemView.findViewById(R.id.text_view_park_location);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}


