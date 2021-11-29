package com.classit.AllCoursesUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classit.CoursesHelper.Adapters.AllCoursesAdapter;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllCoursesFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CoursesModel> coursesModelArrayList = new ArrayList<>();
    RecyclerView allCoursesRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_courses, container, false);

        allCoursesRecycler = view.findViewById(R.id.allCoursesRecycler);
        allCoursesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        allCoursesRecycler.setHasFixedSize(true);
        allCoursesRecycler.setNestedScrollingEnabled(false);

        db.collection("AllCourses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                coursesModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    CoursesModel coursesModel = new CoursesModel();

                    coursesModel.setCourseId(documentSnapshot.getString("courseId"));
                    coursesModel.setCourseName(documentSnapshot.getString("courseName"));
                    coursesModel.setCourseFaculty(documentSnapshot.getString("courseFaculty"));
                    coursesModel.setCourseDays((ArrayList<String>) documentSnapshot.get("day"));
                    coursesModel.setCourseSlot(documentSnapshot.getString("slot"));
                    coursesModel.setCourseTime(documentSnapshot.getString("courseTime"));

                    coursesModelArrayList.add(coursesModel);
                }

                AllCoursesAdapter allCoursesAdapter = new AllCoursesAdapter(getContext(), coursesModelArrayList);
                allCoursesRecycler.setAdapter(allCoursesAdapter);
            }
        });
        
        return view;
    }
}