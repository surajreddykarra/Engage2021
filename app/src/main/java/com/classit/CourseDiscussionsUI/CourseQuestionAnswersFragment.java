package com.classit.CourseDiscussionsUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.AskAndAnswerQuestionsUI.AnswerQuestionFragment;
import com.classit.CourseDiscussionsUI.Adapters.CourseQuestionAnswersAdapter;
import com.classit.CourseDiscussionsUI.Models.CourseQuestionAnswerModel;
import com.classit.CourseDiscussionsUI.Models.CourseQuestionsModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseQuestionAnswersFragment extends Fragment {

    TextView question, numberOfAnswers, numberOfUpvotes;
    TextView userName;
    CircleImageView userPhoto;
    ImageView upvoteButton;

    CardView questionCard, answerQuestionCard;
    CourseQuestionsModel courseQuestionsModel;
    CoursesModel coursesModel;

    RecyclerView courseQuestionAnswersRecycler;
    ArrayList<CourseQuestionAnswerModel> courseQuestionAnswerModelArrayList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_question_answers_fragment, container, false);

        Bundle bundle = getArguments();
        courseQuestionsModel = (CourseQuestionsModel) bundle.getSerializable("questionItem");
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        question = view.findViewById(R.id.question);
        numberOfAnswers = view.findViewById(R.id.numberOfAnswers);
        numberOfUpvotes = view.findViewById(R.id.numberOfUpvotes);
        userName = view.findViewById(R.id.userName);
        userPhoto = view.findViewById(R.id.userPhoto);
        upvoteButton = view.findViewById(R.id.upvoteButton);

        question.setText(courseQuestionsModel.getQuestion());
        numberOfAnswers.setText(courseQuestionsModel.getNumberOfAnswers() + " Answers");
        numberOfUpvotes.setText(courseQuestionsModel.getNumberOfUpvotes());
        userName.setText(courseQuestionsModel.getStudentName());
        Picasso.get().load(courseQuestionsModel.getStudentPhoto()).into(userPhoto);

        questionCard = view.findViewById(R.id.exploreCard);
        answerQuestionCard = view.findViewById(R.id.recordAudioCard);

        questionCard.setBackground(getActivity().getDrawable(R.drawable.glass_card_bg));
        answerQuestionCard.setBackground(getActivity().getDrawable(R.drawable.glass_card_bg));

        courseQuestionAnswersRecycler = view.findViewById(R.id.courseQuestionAnswersRecycler);
        courseQuestionAnswersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        courseQuestionAnswersRecycler.setHasFixedSize(true);
        courseQuestionAnswersRecycler.setNestedScrollingEnabled(false);

        db.collection("AllCourses").document(coursesModel.getCourseId())
                .collection("Questions").document(courseQuestionsModel.getId())
                .collection("Answers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                courseQuestionAnswerModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){

                    CourseQuestionAnswerModel courseQuestionAnswerModel = new CourseQuestionAnswerModel();
                    courseQuestionAnswerModel.setAnswerId(documentSnapshot.getId());
                    courseQuestionAnswerModel.setAnswer(documentSnapshot.getString("answer"));
                    courseQuestionAnswerModel.setUpvoted(false);
                    courseQuestionAnswerModel.setNumberOfUpvotes(documentSnapshot.getString("numberOfUpvotes"));
                    courseQuestionAnswerModel.setUserName(documentSnapshot.getString("userName"));
                    courseQuestionAnswerModel.setUserPhoto(documentSnapshot.getString("userPhoto"));

                    courseQuestionAnswerModelArrayList.add(courseQuestionAnswerModel);
                }

                CourseQuestionAnswersAdapter courseQuestionAnswersAdapter = new CourseQuestionAnswersAdapter(getContext(), courseQuestionAnswerModelArrayList, courseQuestionsModel, coursesModel);
                courseQuestionAnswersRecycler.setAdapter(courseQuestionAnswersAdapter);
            }
        });

        answerQuestionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnswerQuestionFragment answerQuestionFragment = new AnswerQuestionFragment();
                answerQuestionFragment.setArguments(bundle);

                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.main_frame,answerQuestionFragment);
                fragmentTransaction3.addToBackStack(null);
                fragmentTransaction3.commit();

            }
        });

        return view;
    }
}