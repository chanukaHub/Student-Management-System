package com.WizGuys.eStudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Task;

import java.util.List;

public class Todo_adapter extends ArrayAdapter<Task> {
    private Context context;
    int resource;
    private List<Task> taskList;

    public Todo_adapter(@NonNull Context context, int resource, List<Task> tasksList) {
        super(context, resource, tasksList);
        this.context = context;
        this.resource = resource;
        this.taskList = tasksList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.task_list_item, null);

        TextView data = view.findViewById(R.id.task_data);
        TextView date = view.findViewById(R.id.date);

        Button confirmButton = view.findViewById(R.id.confirm_task);
        Button updateButton = view.findViewById(R.id.update_task);
        Button deleteTask = view.findViewById(R.id.delete_task);

        //select list item
        Task task = taskList.get(position);


        //loads data into list....
        data.setText(task.getTask());
        date.setText(task.getDate());


        //button onclick listeners...


        return view;
    }
}
