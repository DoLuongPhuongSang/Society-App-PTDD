package com.example.frontend.ui.group;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.frontend.data.model.Group;
import com.example.frontend.data.model.Member;
import com.example.frontend.data.repository.GroupRepository;
import com.example.frontend.utils.Result;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private GroupRepository repo;

    // ===== RESULT LIVE DATA =====
    public MutableLiveData<Result<Group>> createGroupResult = new MutableLiveData<>();
    public MutableLiveData<Result<Object>> actionResult = new MutableLiveData<>();
    public MutableLiveData<Result<List<Member>>> membersResult = new MutableLiveData<>();
    public MutableLiveData<Result<List<Group>>> myGroupsResult = new MutableLiveData<>();

    public GroupViewModel(@NonNull Application app) {
        super(app);

        try {
            repo = new GroupRepository(app);
        } catch (Exception e) {
            e.printStackTrace();
            repo = null;
        }
    }

    // ===== CREATE GROUP =====
    public void createGroup(String name, String desc, boolean isPrivate) {
        if (repo != null) {

            repo.createGroup(name, desc, new MutableLiveData<Result<Group>>() {
                @Override
                public void postValue(Result<Group> value) {
                    super.postValue(value);

                    createGroupResult.postValue(value);

                    // 🔥 AUTO RELOAD LIST AFTER CREATE SUCCESS
                    if (value != null && value.isSuccess()) {
                        loadMyGroups();
                    }
                }
            });

        } else {
            createGroupResult.postValue(Result.error("Repository not initialized", null));
        }
    }

    // ===== LOAD MY GROUPS =====
    public void loadMyGroups() {
        if (repo != null) {
            repo.getMyGroups(myGroupsResult);
        } else {
            myGroupsResult.postValue(Result.error("Repository not initialized", null));
        }
    }

    // ===== JOIN GROUP =====
    public void join(String id) {
        if (repo != null) {
            repo.joinGroup(id, new MutableLiveData<Result<Object>>() {
                @Override
                public void postValue(Result<Object> value) {
                    super.postValue(value);

                    actionResult.postValue(value);

                    // 🔥 reload sau khi join
                    if (value != null && value.isSuccess()) {
                        loadMyGroups();
                    }
                }
            });
        } else {
            actionResult.postValue(Result.error("Repository not initialized", null));
        }
    }

    // ===== LEAVE GROUP =====
    public void leave(String id) {
        if (repo != null) {
            repo.leaveGroup(id, new MutableLiveData<Result<Object>>() {
                @Override
                public void postValue(Result<Object> value) {
                    super.postValue(value);

                    actionResult.postValue(value);

                    // 🔥 reload sau khi leave
                    if (value != null && value.isSuccess()) {
                        loadMyGroups();
                    }
                }
            });
        } else {
            actionResult.postValue(Result.error("Repository not initialized", null));
        }
    }

    // ===== LOAD MEMBERS =====
    public void loadMembers(String id) {
        if (repo != null) {
            repo.getMembers(id, membersResult);
        } else {
            membersResult.postValue(Result.error("Repository not initialized", null));
        }
    }

    // ===== CREATE POST =====
    public void post(String content, String groupId) {
        if (repo != null) {
            repo.createPost(content, groupId, new MutableLiveData<Result<Object>>() {
                @Override
                public void postValue(Result<Object> value) {
                    super.postValue(value);
                    actionResult.postValue(value);
                }
            });
        } else {
            actionResult.postValue(Result.error("Repository not initialized", null));
        }
    }
}