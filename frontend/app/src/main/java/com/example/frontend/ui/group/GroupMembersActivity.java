package com.example.frontend.ui.group;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frontend.R;
import com.example.frontend.data.model.GroupMember;
import com.example.frontend.data.repository.GroupRepository;
import com.example.frontend.utils.Result;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupMembersActivity extends AppCompatActivity {

    public static final String EXTRA_GROUP_ID = "groupId";
    public static final String EXTRA_IS_ADMIN = "isAdmin";

    private String groupId;
    private boolean isAdmin;
    private String myUserId;

    private RecyclerView rv;
    private MemberAdapter adapter;
    private GroupRepository repository;

    private final MutableLiveData<Result<List<GroupMember>>> membersLive = new MutableLiveData<>();
    private final MutableLiveData<Result<Object>> kickLive = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        groupId = getIntent().getStringExtra(EXTRA_GROUP_ID);
        isAdmin = getIntent().getBooleanExtra(EXTRA_IS_ADMIN, false);
        if (groupId == null) { finish(); return; }

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        myUserId = prefs.getString("USER_ID", "");

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rv = findViewById(R.id.rvMembers);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemberAdapter();
        rv.setAdapter(adapter);

        repository = new GroupRepository(this);

        membersLive.observe(this, r -> {
            if (r == null) return;
            if (r.status == Result.Status.SUCCESS && r.data != null) {
                adapter.submit(r.data);
            } else if (r.status == Result.Status.ERROR) {
                Toast.makeText(this, r.message, Toast.LENGTH_SHORT).show();
            }
        });

        kickLive.observe(this, r -> {
            if (r == null) return;
            if (r.status == Result.Status.SUCCESS) {
                Toast.makeText(this, "Đã xóa thành viên", Toast.LENGTH_SHORT).show();
                repository.getGroupMembers(groupId, membersLive);
            } else if (r.status == Result.Status.ERROR) {
                Toast.makeText(this, r.message, Toast.LENGTH_SHORT).show();
            }
        });

        repository.getGroupMembers(groupId, membersLive);
    }

    // ─── Inner Adapter ───────────────────────────────────────────────────────
    class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.VH> {
        private final List<GroupMember> items = new ArrayList<>();

        void submit(List<GroupMember> data) {
            items.clear();
            if (data != null) items.addAll(data);
            notifyDataSetChanged();
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_group_member, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            GroupMember m = items.get(pos);
            h.tvName.setText(m.getUsername());
            h.tvRole.setText("admin".equals(m.getRole()) ? "👑 Admin" : "Thành viên");
            if (m.getAvatar() != null && !m.getAvatar().isEmpty()) {
                Glide.with(h.imgAvatar).load(m.getAvatar()).placeholder(R.drawable.ic_user).into(h.imgAvatar);
            } else {
                h.imgAvatar.setImageResource(R.drawable.ic_user);
            }

            // Nút kick: admin thấy, nhưng không kick chính mình và không kick admin khác
            boolean canKick = isAdmin && !m.getUserId().equals(myUserId) && !"admin".equals(m.getRole());
            h.btnKick.setVisibility(canKick ? View.VISIBLE : View.GONE);
            h.btnKick.setOnClickListener(v -> {
                new AlertDialog.Builder(GroupMembersActivity.this)
                        .setTitle("Xóa thành viên")
                        .setMessage("Bạn có chắc muốn xóa " + m.getUsername() + " khỏi nhóm?")
                        .setPositiveButton("Xóa", (d, w) ->
                                repository.kickMember(groupId, m.getUserId(), kickLive))
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        }

        @Override public int getItemCount() { return items.size(); }

        class VH extends RecyclerView.ViewHolder {
            CircleImageView imgAvatar;
            TextView tvName, tvRole;
            Button btnKick;
            VH(@NonNull View v) {
                super(v);
                imgAvatar = v.findViewById(R.id.imgAvatar);
                tvName    = v.findViewById(R.id.tvMemberName);
                tvRole    = v.findViewById(R.id.tvRole);
                btnKick   = v.findViewById(R.id.btnKick);
            }
        }
    }
}
