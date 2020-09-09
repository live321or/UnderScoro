package com.samoylov.gameproject.character.profile;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.samoylov.gameproject.R;
import com.samoylov.gameproject.character.profile.adapers.PagerAdapter;
import com.samoylov.gameproject.locations.Test2;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {
    private TabLayout mTabLayout;
    private TabItem firsItem, secondItem, thirdItem;
    private ViewPager viewPager;
    private Test2 test;



    public MyProfileFragment() {
        // Required empty public constructor
    }

    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile, container, false);
        mTabLayout = view.findViewById(R.id.tablayout);



        firsItem = view.findViewById(R.id.firstItem);
        secondItem = view.findViewById(R.id.secondItem);
        thirdItem = view.findViewById(R.id.thirditem);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount()));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Test2) {
            test = (Test2) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        test.test(true);
    }
}
