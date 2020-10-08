package com.WizGuys.eStudent.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Task;
import com.WizGuys.eStudent.model.TimeTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public  class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.RecyclerViewHolder>{
    private Context mContext;
    private ImageView updateButton, deleteButton;
    private List<TimeTable> tasks;
    private OnItemClickListener mListener;
    public TimeTableAdapter(Context context, List<TimeTable> uploads) {
        mContext = context;
        tasks = uploads;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.table_model, parent, false);
        return new RecyclerViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        TimeTable currentTask = tasks.get(position);

            holder.timeDate.setText("Date "+currentTask.getGrade());
            holder.timeGrade.setText("Grade "+currentTask.getDate());


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView timeGrade, timeDate;
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            timeGrade =itemView.findViewById ( R.id.timeGrade );
            timeDate = itemView.findViewById(R.id.timeDate);

            updateButton = itemView.findViewById(R.id.timeTableUpdate);
            deleteButton = itemView.findViewById(R.id.timeTableDelete);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteItemClick(position);
                        }
                    }
                }
            });
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onShowItemClick(position);
                        }
                    }
                }
            });


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add( Menu.NONE, 1, 1, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Delete");
            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
}