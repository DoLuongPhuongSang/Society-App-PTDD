package com.example.frontend.data.remote;

import com.example.frontend.data.model.ApiResponse;
import com.example.frontend.data.model.Friend;
import com.example.frontend.data.model.LoginResponse;
import com.example.frontend.data.model.Member;
import com.example.frontend.data.model.Group;
import com.example.frontend.data.model.Post;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login") // Endpoint đăng nhập
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ApiResponse<LoginResponse>> register(@Body RegisterRequest request);

    // Lấy danh sách gợi ý kết bạn
    @GET("friends/suggestions")
    Call<ApiResponse<List<Friend>>> getFriendSuggestions();

    // 1. Lấy danh sách bạn bè
    @GET("friends")
    Call<ApiResponse<List<Friend>>> getFriends();

    // 2. Lấy danh sách lời mời kết bạn (Pending)
    @GET("friends/pending")
    Call<ApiResponse<List<Friend>>> getPendingRequests();

    // 3. Gửi lời mời kết bạn
    @POST("friends/request/{id}")
    Call<ApiResponse<Object>> sendFriendRequest(@Path("id") String userId);

    // 4. Chấp nhận lời mời
    @PUT("friends/accept/{id}")
    Call<ApiResponse<Object>> acceptFriendRequest(@Path("id") String userId);

    // 5. Từ chối lời mời
    @DELETE("friends/decline/{id}")
    Call<ApiResponse<Object>> declineFriendRequest(@Path("id") String userId);

    // 6. Huỷ kết bạn
    @DELETE("friends/remove/{id}")
    Call<ApiResponse<Object>> removeFriend(@Path("id") String userId);

    // ===== GROUP =====
    @GET("groups/my-groups") // Phải khớp với router.get("/my-groups", ...)
    Call<ApiResponse<List<Group>>> getMyGroups();
    // create group
    @POST("groups")
    Call<ApiResponse<Group>> createGroup(@Body GroupRequest request);

    // join
    @POST("groups/{groupId}/join")
    Call<ApiResponse<Object>> joinGroup(@Path("groupId") String groupId);

    // leave
    @POST("groups/{groupId}/leave")
    Call<ApiResponse<Object>> leaveGroup(@Path("groupId") String groupId);

    // members
    @GET("groups/{groupId}/members")
    Call<ApiResponse<List<Member>>> getMembers(@Path("groupId") String groupId);

    // post
    @POST("groups/post")
    Call<ApiResponse<Object>> createPost(@Body CreatePostRequest request);

    // posts list
    @GET("groups/{groupId}/posts")
    Call<ApiResponse<List<Post>>> getPosts(@Path("groupId") String groupId);
}
