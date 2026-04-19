package com.example.frontend.ui.group;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GroupPagerAdapter extends FragmentStateAdapter {

    public GroupPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyGroupFragment();
            case 1:
                return new DiscoverFragment();
            case 2:
                return new PostFragment();
            default:
                return new MyGroupFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}