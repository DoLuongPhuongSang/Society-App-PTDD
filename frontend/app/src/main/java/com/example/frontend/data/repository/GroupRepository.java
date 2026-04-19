package com.example.frontend.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.frontend.data.model.*;
import com.example.frontend.data.remote.*;
import com.example.frontend.utils.Result;

import java.util.List;

import retrofit2.*;

public class GroupRepository {

    private ApiService apiService;

    public GroupRepository(Context context) {
        try {
            apiService = ApiClient.getApiService(context);
        } catch (Exception e) {
            e.printStackTrace();
            apiService = null;
        }
    }

    // ===== CREATE GROUP =====
    public void createGroup(String name, String desc, MutableLiveData<Result<Group>> liveData) {
        if (apiService == null) {
            android.util.Log.e("GroupRepository", "API service is null");
            liveData.postValue(Result.error("API service not initialized", null));
            return;
        }

        android.util.Log.d("GroupRepository", "Creating group: name=" + name + ", desc=" + desc);
        liveData.postValue(Result.loading(null));

        apiService.createGroup(new GroupRequest(name, desc, false))
                .enqueue(new Callback<ApiResponse<Group>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Group>> call, Response<ApiResponse<Group>> response) {
                        android.util.Log.d("GroupRepository", "Create group response: " + response.code() + " " + response.message());
                        if (response.isSuccessful() && response.body() != null) {
                            android.util.Log.d("GroupRepository", "Response body: " + response.body().toString());
                            if (response.body().isSuccess()) {
                                android.util.Log.d("GroupRepository", "Create group success");
                                liveData.postValue(Result.success(response.body().getData()));
                            } else {
                                android.util.Log.e("GroupRepository", "API returned error: " + response.body().getMessage());
                                liveData.postValue(Result.error(response.body().getMessage(), null));
                            }
                        } else {
                            android.util.Log.e("GroupRepository", "Response not successful or body is null");
                            try {
                                String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                                android.util.Log.e("GroupRepository", "Error body: " + errorBody);
                            } catch (Exception e) {
                                android.util.Log.e("GroupRepository", "Error reading error body", e);
                            }
                            liveData.postValue(Result.error("Create group failed", null));
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Group>> call, Throwable t) {
                        android.util.Log.e("GroupRepository", "Create group network failure", t);
                        liveData.postValue(Result.error(t.getMessage(), null));
                    }
                });
    }
    public void getMyGroups(MutableLiveData<Result<List<Group>>> liveData) {
        if (apiService == null) {
            liveData.postValue(Result.error("API service not initialized", null));
            return;
        }

        liveData.postValue(Result.loading(null));
        // 1. Đổi Callback<List<Group>> thành Callback<ApiResponse<List<Group>>>
        apiService.getMyGroups().enqueue(new Callback<ApiResponse<List<Group>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Group>>> call, Response<ApiResponse<List<Group>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 2. Lấy dữ liệu thực sự (List<Group>) từ trong object ApiResponse
                    List<Group> groups = response.body().getData();

                    // Gửi dữ liệu về LiveData
                    liveData.postValue(Result.success(groups));
                } else {
                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().isSuccess()) {
                            List<Group> groups = response.body().getData();
                            liveData.postValue(Result.success(groups));
                        } else {
                            liveData.postValue(Result.error(response.body().getMessage(), null));
                        }

                    } else {
                        liveData.postValue(Result.error("Không thể lấy danh sách nhóm", null));
                    }                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Group>>> call, Throwable t) {
                // Gửi thông báo lỗi về LiveData
                liveData.postValue(Result.error(t.getMessage(), null));
            }
        });
    }
    // ===== JOIN =====
    public void joinGroup(String id, MutableLiveData<Result<Object>> liveData) {
        if (apiService == null) {
            liveData.postValue(Result.error("API service not initialized", null));
            return;
        }

        liveData.postValue(Result.loading(null));

        apiService.joinGroup(id).enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    liveData.postValue(Result.success(null));
                } else {
                    liveData.postValue(Result.error("Join group failed", null));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                liveData.postValue(Result.error(t.getMessage(), null));
            }
        });
    }

    // ===== LEAVE GROUP =====
    public void leaveGroup(String id, MutableLiveData<Result<Object>> liveData) {
        if (apiService == null) {
            liveData.postValue(Result.error("API service not initialized", null));
            return;
        }

        liveData.postValue(Result.loading(null));

        apiService.leaveGroup(id).enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    liveData.postValue(Result.success(null));
                } else {
                    liveData.postValue(Result.error("Leave group failed", null));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                liveData.postValue(Result.error(t.getMessage(), null));
            }
        });
    }

    // ===== GET MEMBERS =====
    public void getMembers(String id, MutableLiveData<Result<List<Member>>> liveData) {
        if (apiService == null) {
            liveData.postValue(Result.error("API service not initialized", null));
            return;
        }

        liveData.postValue(Result.loading(null));

        apiService.getMembers(id).enqueue(new Callback<ApiResponse<List<Member>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Member>>> call, Response<ApiResponse<List<Member>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    liveData.postValue(Result.success(response.body().getData()));
                } else {
                    liveData.postValue(Result.error("Load members failed", null));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Member>>> call, Throwable t) {
                liveData.postValue(Result.error(t.getMessage(), null));
            }
        });
    }

    // ===== POST =====
    public void createPost(String content, String groupId,
                           MutableLiveData<Result<Object>> liveData) {
        if (apiService == null) {
            liveData.postValue(Result.error("API service not initialized", null));
            return;
        }

        liveData.postValue(Result.loading(null));

        apiService.createPost(new CreatePostRequest(content, groupId))
                .enqueue(new Callback<ApiResponse<Object>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            liveData.postValue(Result.success(null));
                        } else {
                            liveData.postValue(Result.error("Create post failed", null));
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                        liveData.postValue(Result.error(t.getMessage(), null));
                    }
                });
    }
}