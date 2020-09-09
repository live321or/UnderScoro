package com.samoylov.gameproject.character.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.samoylov.gameproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySkillsFragme extends Fragment {

    public MySkillsFragme() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_skills, container, false);
    }
}
