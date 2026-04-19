package com.example.frontend.ui.group;

import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.data.model.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.VH> {

    private List<Group> list;

    public GroupAdapter(List<Group> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Group g = list.get(position);
        h.txtName.setText(g.getGroupName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 🔥 QUAN TRỌNG
    public void updateData(List<Group> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtName;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtGroupName);
        }
    }
}
