package com.classit.CourseInsideUI.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.classit.CourseInsideUI.Adapters.CourseEnrolledStudentsAdapter;
import com.classit.CourseInsideUI.Models.CourseEnrolledStudentsModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CourseInfoFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CourseEnrolledStudentsModel> courseEnrolledStudentsModelArrayList = new ArrayList<>();
    RecyclerView courseEnrolledStudentsRecycler;

    TextView courseName, courseId, courseFaculty;
    CardView courseCard;
    Button enrollCourse;

    CoursesModel coursesModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        courseName = view.findViewById(R.id.courseName);
        courseId = view.findViewById(R.id.courseId);
        courseFaculty = view.findViewById(R.id.courseFaculty);

        courseCard = view.findViewById(R.id.courseCard);
        enrollCourse = view.findViewById(R.id.enrollCourse);

        courseCard.setBackground(getContext().getDrawable(R.drawable.glass_card_bg));

        courseName.setText(coursesModel.getCourseName());
        courseId.setText(coursesModel.getCourseId());
        courseFaculty.setText(coursesModel.getCourseFaculty());

        courseEnrolledStudentsRecycler = view.findViewById(R.id.courseEnrolledStudentsRecycler);
        courseEnrolledStudentsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        courseEnrolledStudentsRecycler.setHasFixedSize(true);
        courseEnrolledStudentsRecycler.setNestedScrollingEnabled(false);

        db.collection("CourseStudents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                courseEnrolledStudentsModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    CourseEnrolledStudentsModel courseEnrolledStudentsModel = new CourseEnrolledStudentsModel();
                    courseEnrolledStudentsModel.setStudentId(documentSnapshot.getString("studentId"));
                    courseEnrolledStudentsModel.setStudentName(documentSnapshot.getString("studentName"));
                    courseEnrolledStudentsModel.setStudentPhoto(documentSnapshot.getString("studentPhoto"));

                    courseEnrolledStudentsModelArrayList.add(courseEnrolledStudentsModel);
                }

                CourseEnrolledStudentsAdapter courseEnrolledStudentsAdapter = new CourseEnrolledStudentsAdapter(getContext(), courseEnrolledStudentsModelArrayList);
                courseEnrolledStudentsRecycler.setAdapter(courseEnrolledStudentsAdapter);
            }
        });

        return view;
    }
}