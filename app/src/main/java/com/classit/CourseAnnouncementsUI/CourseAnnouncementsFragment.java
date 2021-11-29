package com.classit.CourseAnnouncementsUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classit.CourseAnnouncementsUI.Adapters.AnnouncementsAdapter;
import com.classit.CourseAnnouncementsUI.Models.AnnouncementModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CourseAnnouncementsFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<AnnouncementModel> announcementModelArrayList = new ArrayList<>();
    RecyclerView announcementsRecycler;

    CoursesModel coursesModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_announcements, container, false);

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        announcementsRecycler = view.findViewById(R.id.announcementsRecycler);
        announcementsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db.collection("AllCourses").document(coursesModel.getCourseId())
                .collection("Announcements").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                announcementModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    AnnouncementModel announcementModel = new AnnouncementModel();
                    announcementModel.setTitle(documentSnapshot.getString("title"));
                    announcementModel.setContent(documentSnapshot.getString("content"));

                    announcementModelArrayList.add(announcementModel);
                }

                AnnouncementsAdapter announcementsAdapter = new AnnouncementsAdapter(getContext(), announcementModelArrayList);
                announcementsRecycler.setAdapter(announcementsAdapter);

            }
        });

        return view;
    }
}