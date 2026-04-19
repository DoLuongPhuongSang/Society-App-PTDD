package com.example.frontend.ui.group;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.frontend.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GroupActivity extends AppCompatActivity implements GroupActionListener {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    ImageView btnBack, btnSetting, btnAdd;

    GroupViewModel vm; // 🔥 ADD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        // ===== INIT VIEW =====
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        btnBack = findViewById(R.id.btnBack);
        btnSetting = findViewById(R.id.btnSetting);
        btnAdd = findViewById(R.id.btnAdd);

        // 🔥 INIT VIEWMODEL
        vm = new ViewModelProvider(this).get(GroupViewModel.class);

        // ===== VIEWPAGER =====
        viewPager.setAdapter(new GroupPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Nhóm của bạn");
            else if (position == 1) tab.setText("Khám phá");
            else tab.setText("Bài viết");
        }).attach();

        // ===== BUTTON =====
        btnBack.setOnClickListener(v -> finish());

        btnSetting.setOnClickListener(v ->
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
        );

        btnAdd.setOnClickListener(v -> showAddOptions());

        observe(); // 🔥 ADD
    }

    // 🔥 CALLBACK từ Fragment
    @Override
    public void onCreateGroupClick() {
        showCreateGroupDialog();
    }

    // ===== OBSERVE API =====
    private void observe() {

        vm.createGroupResult.observe(this, r -> {
            switch (r.status) {
                case LOADING:
                    Toast.makeText(this, "Đang tạo nhóm...", Toast.LENGTH_SHORT).show();
                    break;

                case SUCCESS:
                    Toast.makeText(this, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show();
                    break;

                case ERROR:
                    Toast.makeText(this, r.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    // ===== MENU ADD =====
    private void showAddOptions() {

        String[] options = {"Đăng bài", "Tạo nhóm"};

        new android.app.AlertDialog.Builder(this)
                .setTitle("Chọn hành động")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) showCreatePostDialog();
                    else showCreateGroupDialog();
                })
                .show();
    }

    // ===== POPUP CREATE GROUP =====
    private void showCreateGroupDialog() {

        View view = getLayoutInflater().inflate(R.layout.dialog_create_group, null);

        EditText edtName = view.findViewById(R.id.edtName);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroupPrivacy);
        Button btnCreate = view.findViewById(R.id.btnCreate);

        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                .setView(view)
                .create();

        btnCreate.setOnClickListener(v -> {

            String name = edtName.getText().toString().trim();
            boolean isPrivate = radioGroup.getCheckedRadioButtonId() == R.id.rbPrivate;

            if (name.isEmpty()) {
                Toast.makeText(this, "Nhập tên nhóm", Toast.LENGTH_SHORT).show();
                return;
            }

            vm.createGroup(name, "", isPrivate); // 🔥 CALL API
            dialog.dismiss();
        });

        dialog.show();
    }

    // ===== POPUP POST =====
    private void showCreatePostDialog() {

        View view = getLayoutInflater().inflate(R.layout.dialog_create_post, null);

        EditText edtPost = view.findViewById(R.id.edtPost);
        Button btnPost = view.findViewById(R.id.btnPost);

        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                .setView(view)
                .create();

        btnPost.setOnClickListener(v -> {

            String content = edtPost.getText().toString().trim();

            if (content.isEmpty()) {
                Toast.makeText(this, "Nhập nội dung", Toast.LENGTH_SHORT).show();
                return;
            }

            // 👉 TODO: gọi vm.post(content, groupId)
            Toast.makeText(this, "Đã gửi (chưa gắn group)", Toast.LENGTH_SHORT).show();

            dialog.dismiss();
        });

        dialog.show();
    }
}