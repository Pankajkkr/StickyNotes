package com.rvgroup.stickynotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rvgroup.stickynotes.R;
import com.rvgroup.stickynotes.activities.Note;
import com.rvgroup.stickynotes.databinding.ItemEachViewBinding;

import javax.security.auth.callback.Callback;

public class RvAdapter extends ListAdapter<Note,RvAdapter.ViewHolder> {


    public RvAdapter(){
        super(CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getBody().equals(newItem.getBody());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_each_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = getItem(position);
        holder.binding.titleA.setText(note.getTitle());
        holder.binding.bodyA.setText(note.getBody());
    }

    public Note getNote(int position){
        return getItem(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       ItemEachViewBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemEachViewBinding.bind(itemView);
        }
    }
}
