package com.classit.CourseDiscussionsUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classit.AskAndAnswerQuestionsUI.AskQuestionFragment;
import com.classit.CourseDiscussionsUI.Adapters.CourseQuestionsAdapter;
import com.classit.CourseDiscussionsUI.Models.CourseQuestionsModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CourseQuestionsFragment extends Fragment {

    RecyclerView courseQuestionsRecycler;
    ArrayList<CourseQuestionsModel> courseQuestionsModelArrayList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CoursesModel coursesModel;
    String courseId;

    ShimmerFrameLayout shimmerFrameLayout;

    ExtendedFloatingActionButton askQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_questions, container, false);

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        courseId = coursesModel.getCourseId();

        courseQuestionsRecycler = view.findViewById(R.id.courseQuestionsRecycler);
        courseQuestionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        courseQuestionsRecycler.setNestedScrollingEnabled(false);
        courseQuestionsRecycler.setHasFixedSize(true);

        getData();

        askQuestion = view.findViewById(R.id.askQuestion);
        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AskQuestionFragment askQuestionFragment = new AskQuestionFragment();
                askQuestionFragment.setArguments(bundle);

                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.main_frame,askQuestionFragment);
                fragmentTransaction3.addToBackStack(null);
                fragmentTransaction3.commit();

            }
        });

        return view;
    }

    private void getData(){
        db.collection("AllCourses").document(courseId).collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                courseQuestionsModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    CourseQuestionsModel courseQuestionsModel = new CourseQuestionsModel();
                    courseQuestionsModel.setQuestion(documentSnapshot.getString("question"));
                    courseQuestionsModel.setNumberOfAnswers(documentSnapshot.getString("numberOfAnswers"));
                    courseQuestionsModel.setNumberOfUpvotes(documentSnapshot.getString("numberOfUpvotes"));
                    courseQuestionsModel.setId(documentSnapshot.getId());
                    courseQuestionsModel.setStudentName(documentSnapshot.getString("studentName"));
                    courseQuestionsModel.setStudentPhoto(documentSnapshot.getString("studentPhoto"));

                    courseQuestionsModelArrayList.add(courseQuestionsModel);
                }

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                CourseQuestionsAdapter courseQuestionsAdapter = new CourseQuestionsAdapter(getContext(), courseQuestionsModelArrayList, coursesModel);
                courseQuestionsRecycler.setAdapter(courseQuestionsAdapter);
            }
        });
    }

}