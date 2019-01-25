package com.example.rotelukh.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ToDo item);
    }

    private final List<ToDo> items;
    private final OnItemClickListener listener;
    private Context context;

    public ToDoAdapter(List<ToDo> items, OnItemClickListener listener, Context context) {
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener, context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName) TextView name;
        @BindView(R.id.checkBox) CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ToDo item, final OnItemClickListener listener, final Context context) {
            name.setText(item.getTodo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
//                    if(checkBox.isChecked()) {
//                        checkBox.setChecked(false);
//                    }else {
//                        checkBox.setChecked(true);
//                    }
                }
            });
        }
    }
}