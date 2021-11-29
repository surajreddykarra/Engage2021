package com.classit.MyCoursesUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classit.CoursesHelper.Adapters.AllCoursesAdapter;
import com.classit.CoursesHelper.Adapters.MyCoursesAdapter;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MyCoursesFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CoursesModel> coursesModelArrayList = new ArrayList<>();
    RecyclerView myCoursesRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_courses, container, false);

        myCoursesRecycler = view.findViewById(R.id.myCoursesRecycler);
        myCoursesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        myCoursesRecycler.setHasFixedSize(true);
        myCoursesRecycler.setNestedScrollingEnabled(false);

        db.collection("Users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).collection("MyCourses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                coursesModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    CoursesModel coursesModel = new CoursesModel();

                    coursesModel.setCourseId(documentSnapshot.getString("courseId"));
                    coursesModel.setCourseName(documentSnapshot.getString("courseName"));
                    coursesModel.setCourseFaculty(documentSnapshot.getString("courseFaculty"));

                    coursesModelArrayList.add(coursesModel);
                }

                MyCoursesAdapter myCoursesAdapter = new MyCoursesAdapter(getContext(), coursesModelArrayList);
                myCoursesRecycler.setAdapter(myCoursesAdapter);
            }
        });

        return view;
    }
}