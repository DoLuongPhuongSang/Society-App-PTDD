package com.example.frontend.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.frontend.R;
import com.example.frontend.data.model.ApiResponse;
import com.example.frontend.data.model.Conversation;
import com.example.frontend.data.model.User;
import com.example.frontend.data.remote.ApiClient;
import com.example.frontend.data.remote.ApiService;
import com.example.frontend.data.repository.ChatRepository;
import com.example.frontend.ui.chat.ChatDetailFragment;
import com.example.frontend.utils.Result;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendProfileActivity extends AppCompatActivity {

    private String friendId;
    private ChatRepository chatRepository;
    private final MutableLiveData<Result<Conversation>> convLive = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        friendId = getIntent().getStringExtra("FRIEND_ID");
        String friendName   = getIntent().getStringExtra("FRIEND_NAME");
        String friendAvatar = getIntent().getStringExtra("FRIEND_AVATAR");

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        CircleImageView imgAvatar = findViewById(R.id.imgAvatar);
        TextView tvName = findViewById(R.id.tvFriendName);
        MaterialButton btnMessage = findViewById(R.id.btnMessage);

        tvName.setText(friendName != null ? friendName : "");
        if (friendAvatar != null && !friendAvatar.isEmpty()) {
            Glide.with(this).load(friendAvatar)
                    .placeholder(R.drawable.ic_user).into(imgAvatar);
        }

        chatRepository = new ChatRepository(this);

        // Nút nhắn tin → tạo/lấy conversation rồi mở ChatDetailFragment
        btnMessage.setOnClickListener(v -> {
            if (friendId == null) return;
            btnMessage.setEnabled(false);
            chatRepository.getOrCreateConversation(friendId, convLive);
        });

        convLive.observe(this, result -> {
            if (result == null) return;
            if (result.status == Result.Status.SUCCESS && result.data != null) {
                Fragment chatFrag = ChatDetailFragment.newInstance(result.data, null);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.friendPostsContainer, chatFrag)
                        .addToBackStack(null)
                        .commit();
            } else if (result.status == Result.Status.ERROR) {
                btnMessage.setEnabled(true);
                Toast.makeText(this, "Không thể mở chat: " + result.message, Toast.LENGTH_SHORT).show();
            }
        });

        // Hiện bài viết của bạn
        if (savedInstanceState == null && friendId != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.friendPostsContainer, ProfileFeedFragment.forUser(friendId))
                    .commit();
        }
    }
}
