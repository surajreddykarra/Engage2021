package com.classit.CourseInsideUI.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;

import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class CourseEnrolledFragment extends Fragment {

    LottieAnimationView lottieAnimationView;

    CoursesModel coursesModel;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_joined, container, false);

        MixpanelAPI mixpanel = MixpanelAPI.getInstance(getContext(), "7ae8227e35f5d23259e2acff044ab43c");
        mixpanel.getPeople().identify(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("Course");

        lottieAnimationView = view.findViewById(R.id.animationView);
        lottieAnimationView.setAnimation(R.raw.joined);

        Uri uri = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String, Object> data = new HashMap<>();
        data.put("studentId", user.getUid());
        data.put("studentName", user.getDisplayName());
        data.put("studentPhoto", String.valueOf(uri));

        db.collection("CourseStudents").document(user.getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        int secondsDelayed = 1;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                CourseMainFragment courseMainFragment = new CourseMainFragment();

                                courseMainFragment.setArguments(bundle);

                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_frame,courseMainFragment);
                                fragmentTransaction.commit();
                            }
                        }, secondsDelayed * 3000);

                    }
                });

        return view;
    }
}