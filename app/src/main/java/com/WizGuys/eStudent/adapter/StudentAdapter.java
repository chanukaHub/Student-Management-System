package com.WizGuys.eStudent.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Student;
import com.WizGuys.eStudent.students.StudentItems;
import com.squareup.picasso.Picasso;


import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ImageView btnDeleteStudent,studentUpdate;
    private List<Student> students;
    private StudentAdapter.OnItemClickListener mListener;
    public StudentAdapter(Context context, List<Student> uploads) {
        mContext = context;
        students = uploads;
    }
    @Override
    public StudentAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_student, parent, false);
        return new StudentAdapter.RecyclerViewHolder(v);
    }
    @Override
    public void onBindViewHolder(StudentAdapter.RecyclerViewHolder holder, int position) {
        Student currentStudent = students.get(position);

        holder.nameTextView.setText(currentStudent.getName());
        holder.indexTextView.setText(currentStudent.getIndex());
        holder.emailTextView.setText(currentStudent.getEmail());

        Picasso.with(mContext)
                .load(currentStudent.getImageURL())
                .placeholder(R.drawable.capture1)
                .fit()
                .centerCrop()
                .into(holder.studentImageView);
    }
    @Override
    public int getItemCount() {
        return students.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView nameTextView,indexTextView,emailTextView;
        public ImageView studentImageView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nameTextView =itemView.findViewById ( R.id.textViewStudentName );
            indexTextView = itemView.findViewById(R.id.textViewStudentIndex);
            emailTextView = itemView.findViewById(R.id.textViewStudentEmail);
            studentImageView = itemView.findViewById(R.id.studentImageView);
            btnDeleteStudent = itemView.findViewById(R.id.studentDelete);
            studentUpdate = itemView.findViewById(R.id.studentUpdate);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
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
            studentUpdate.setOnClickListener(new View.OnClickListener() {
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

        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            return false;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }
    public void setOnItemClickListener(StudentAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
