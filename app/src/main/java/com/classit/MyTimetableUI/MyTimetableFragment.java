package com.classit.MyTimetableUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classit.R;
import com.classit.Utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MyTimetableFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    DayTimetableFragment mondayFragment = new DayTimetableFragment();
    DayTimetableFragment tuesdayFragment = new DayTimetableFragment();
    DayTimetableFragment wednesdayFragment = new DayTimetableFragment();
    DayTimetableFragment thursdayFragment = new DayTimetableFragment();
    DayTimetableFragment fridayFragment = new DayTimetableFragment();
    DayTimetableFragment saturdayFragment = new DayTimetableFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_timetable, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.view_pager);

        tabLayout.setupWithViewPager(viewPager);

        mondayFragment.setArguments(getArguments("Monday"));
        tuesdayFragment.setArguments(getArguments("Tuesday"));
        wednesdayFragment.setArguments(getArguments("Wednesday"));
        thursdayFragment.setArguments(getArguments("Thursday"));
        fridayFragment.setArguments(getArguments("Friday"));
        saturdayFragment.setArguments(getArguments("Saturday"));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        viewPagerAdapter.addFragment(mondayFragment,"Monday");
        viewPagerAdapter.addFragment(tuesdayFragment,"Tuesday");
        viewPagerAdapter.addFragment(wednesdayFragment,"Wednesday");
        viewPagerAdapter.addFragment(thursdayFragment,"Thursday");
        viewPagerAdapter.addFragment(fridayFragment,"Friday");
        viewPagerAdapter.addFragment(saturdayFragment,"Saturday");

        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }

    private Bundle getArguments(String day){
        Bundle bundle = new Bundle();
        bundle.putString("day", day);
        return  bundle;
    }
}