package com.jovin.customcalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Users> arrayList;
    LayoutInflater inflater;
    int i = 0;

    public EventsRecyclerAdapter(Context context, ArrayList<Users> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowlayout, parent, false);
        return new MyViewHolder((view));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Users user = arrayList.get(position);
        holder.Event.setText(user.getNAME());
        holder.Shift.setText(user.getSHIFT());
        if(arrayList.get(position).getSHIFT().equals("Off"))
        {
            holder.Event.setTextColor(context.getColor(R.color.red));
            holder.Shift.setTextColor(context.getColor(R.color.red));
        }
        holder.SwapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String currentStaff = arrayList.get(position).getNAME();
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String userName = dataSnapshot.child("name").getValue(String.class);
                        arrayList.get(position).setNAME(userName);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                notifyDataSetChanged();
            }
        });
        //holder.DateText.setText(user.getDATE());
        holder.Event.setText(user.getNAME());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView DateText, Event, Shift;
        Button SwapBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //DateText = itemView.findViewById(R.id.eventDate);
            Event = itemView.findViewById(R.id.eventName);
            Shift = itemView.findViewById(R.id.shiftName);
            SwapBtn = itemView.findViewById(R.id.swapBtn);
        }
    }

}
