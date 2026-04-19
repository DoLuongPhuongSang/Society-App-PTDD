package com.example.frontend.ui.group;

import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.data.model.Group;

import java.util.ArrayList;
import java.util.List;

public class MyGroupFragment extends Fragment {

    private GroupViewModel vm;
    private GroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Đảm bảo file layout này có chứa Button btnCreateGroup và RecyclerView rvGroups
        return inflater.inflate(R.layout.fragment_my_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Ánh xạ và thiết lập RecyclerView
        RecyclerView rv = view.findViewById(R.id.rvGroups);

        // LỖI THƯỜNG GẶP: Thiếu LayoutManager sẽ khiến danh sách không hiện
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GroupAdapter(new ArrayList<>());
        rv.setAdapter(adapter);

        // 2. Khởi tạo ViewModel (Dùng requireActivity để đồng bộ với Activity)
        vm = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);

        // 3. Bắt sự kiện nút Tạo nhóm
        Button btnCreate = view.findViewById(R.id.btnCreateGroup);
        btnCreate.setOnClickListener(v -> {
            // Gọi hàm hiển thị Dialog thay vì gọi vm.createGroup trực tiếp
            showCreateGroupDialog();
        });

        // 4. Theo dõi kết quả tạo nhóm (Để thông báo cho người dùng)
        vm.createGroupResult.observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result.isSuccess()) {
                    Toast.makeText(getContext(), "Tạo nhóm thành công!", Toast.LENGTH_SHORT).show();
                    vm.loadMyGroups();
                } else {
                    Toast.makeText(getContext(), "Lỗi: " + result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 5. Theo dõi danh sách nhóm (Observe Data)
        vm.myGroupsResult.observe(getViewLifecycleOwner(), result -> {
            if (result != null && result.isSuccess()) {
                List<Group> data = result.getData();
                if (data != null) {
                    adapter.updateData(data);
                }
            }
        });

        // 6. Load dữ liệu lần đầu
        vm.loadMyGroups();
    }
    private void showCreateGroupDialog() {
        // 1. Khởi tạo AlertDialog
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());

        // Nạp layout dialog_create_group.xml vào (Đảm bảo tên file XML của bạn đúng như này)
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_group, null);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Làm nền trong suốt để thấy bo góc của CardView
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // 2. Ánh xạ các View bên trong Dialog
        android.widget.EditText edtName = dialogView.findViewById(R.id.edtName);
        android.widget.RadioButton rbPrivate = dialogView.findViewById(R.id.rbPrivate);
        Button btnConfirm = dialogView.findViewById(R.id.btnCreate); // Nút "Tạo nhóm" trong dialog

        // 3. Xử lý khi bấm nút "Tạo nhóm" trong Dialog
        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            boolean isPrivate = rbPrivate.isChecked(); // Nếu chọn radio riêng tư thì true

            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập tên nhóm", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi ViewModel để gửi dữ liệu lên Server
            vm.createGroup(name, "Mô tả nhóm mặc định", isPrivate);

            // Đóng dialog
            dialog.dismiss();
        });

        dialog.show();
    }
}