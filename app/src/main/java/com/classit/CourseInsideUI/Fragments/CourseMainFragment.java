package com.classit.CourseInsideUI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classit.CourseAnnouncementsUI.CourseAnnouncementsFragment;
import com.classit.CourseChatUI.CourseChatFragement;
import com.classit.CourseDiscussionsUI.CourseQuestionsFragment;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.classit.Utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class CourseMainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    CourseQuestionsFragment courseQuestionsFragment = new CourseQuestionsFragment();
    CourseChatFragement courseChatFragement = new CourseChatFragement();
    CourseAnnouncementsFragment courseAnnouncementsFragment = new CourseAnnouncementsFragment();
    CourseInfoFragment courseInfoFragment = new CourseInfoFragment();

    CoursesModel coursesModel;
    TextView courseName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_main, container, false);

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        courseQuestionsFragment.setArguments(bundle);
        courseChatFragement.setArguments(bundle);
        courseAnnouncementsFragment.setArguments(bundle);
        courseInfoFragment.setArguments(bundle);

        courseName = view.findViewById(R.id.courseName);
        courseName.setText(coursesModel.getCourseId());

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.view_pager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        viewPagerAdapter.addFragment(courseQuestionsFragment,"Discussions");
        viewPagerAdapter.addFragment(courseChatFragement,"Chat");
        viewPagerAdapter.addFragment(courseAnnouncementsFragment,"Announcements");
        viewPagerAdapter.addFragment(courseInfoFragment,"Info");

        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }
}