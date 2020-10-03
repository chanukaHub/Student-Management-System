package com.WizGuys.eStudent.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Subject;
import com.WizGuys.eStudent.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.RecyclerViewHolder>{
        private Context mContext;
        private ImageButton updateButton, deleteButton;
        private List<Subject> subjects;
        private OnItemClickListener mListener;

        public SubjectAdapter (Context context, List<Subject> uploads) {
            mContext = context;
            subjects = uploads;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.subject_list_item, parent, false);
            return new RecyclerViewHolder(v);
        }


    @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {

            Subject currentSubject = subjects.get(position);

            holder.nameTextView.setText(Common.SUB_NAME + currentSubject.getName());
            holder.chapterTextView.setText(Common.CHAPTERS + currentSubject.getChapters());
            holder.infoTextView.setText(Common.INFO + currentSubject.getInfo());


        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }


        public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
                View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public TextView nameTextView, chapterTextView, infoTextView;
            public RecyclerViewHolder(View itemView) {
                super(itemView);

                nameTextView =itemView.findViewById ( R.id.name_subject_list);
                chapterTextView = itemView.findViewById(R.id.chapter_subject_list);
                infoTextView = itemView.findViewById(R.id.info_subject_list);
                updateButton = itemView.findViewById(R.id.update_subject);
                deleteButton = itemView.findViewById(R.id.delete_subject);


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

}
