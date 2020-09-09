package com.samoylov.gameproject.character.profile.adapers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.samoylov.gameproject.character.profile.MyBagFragment;
import com.samoylov.gameproject.character.profile.MyInfoFragment;
import com.samoylov.gameproject.character.profile.MySkillsFragme;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyInfoFragment();
            case 1:
                return new MySkillsFragme();
            case 2:
                return new MyBagFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
