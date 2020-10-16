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
import com.WizGuys.eStudent.model.Finance;
import com.WizGuys.eStudent.model.Teacher;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public  class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.RecyclerViewHolder>{
    private Context mContext;
    private ImageView financeDelete,financeUpdate;
    private List<Finance> finances;
    private OnItemClickListener mListener;
    public FinanceAdapter(Context context, List<Finance> uploads) {
        mContext = context;
        finances = uploads;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.finance_model, parent, false);
        return new RecyclerViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Finance currentFinance = finances.get(position);

        holder.stdName.setText("Student Name : "+currentFinance.getName());
        holder.stdPayment.setText("Total Amount : "+currentFinance.getAmount());
        holder.stdBalance.setText("Balance Payment : "+currentFinance.getBalance());


    }
    @Override
    public int getItemCount() {
        return finances.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView stdName,stdPayment,stdBalance;
        public ImageView FinanceImageView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            stdName =itemView.findViewById ( R.id.stdName );
            stdPayment = itemView.findViewById(R.id.stdPayment);
            stdBalance = itemView.findViewById(R.id.stdBalance);
            FinanceImageView = itemView.findViewById(R.id.FinanceImageView);
            financeDelete = itemView.findViewById(R.id.financeDelete);
            financeUpdate = itemView.findViewById(R.id.financeUpdate);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            financeDelete.setOnClickListener(new View.OnClickListener() {
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
            financeUpdate.setOnClickListener(new View.OnClickListener() {

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

        @Override
        public void onClick(View v) {}
    }
    public interface OnItemClickListener {
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}