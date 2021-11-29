package com.classit.AskAndAnswerQuestionsUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classit.CourseDiscussionsUI.Models.CourseQuestionsModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerQuestionFragment extends Fragment {

    TextView question, numberOfAnswers;
    TextView userName;
    CircleImageView userPhoto;
    CardView questionCard;

    CourseQuestionsModel courseQuestionsModel;
    CoursesModel coursesModel;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText answerEdit;
    Button submit;

    String answer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer_question, container, false);

        Bundle bundle = getArguments();
        courseQuestionsModel = (CourseQuestionsModel) bundle.getSerializable("questionItem");
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        question = view.findViewById(R.id.question);
        numberOfAnswers = view.findViewById(R.id.numberOfAnswers);
        userName = view.findViewById(R.id.userName);
        userPhoto = view.findViewById(R.id.userPhoto);

        question.setText(courseQuestionsModel.getQuestion());
        numberOfAnswers.setText(courseQuestionsModel.getNumberOfAnswers() + " Answers");
        userName.setText(courseQuestionsModel.getStudentName());
        Picasso.get().load(courseQuestionsModel.getStudentPhoto()).into(userPhoto);

        questionCard = view.findViewById(R.id.exploreCard);
        questionCard.setBackground(getActivity().getDrawable(R.drawable.glass_card_bg));


        answerEdit = view.findViewById(R.id.answer_edit);
        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answer = answerEdit.getText().toString();

                if(TextUtils.isEmpty(answer)){
                    Toast.makeText(getContext(), "Please enter an answer to submit", Toast.LENGTH_SHORT).show();
                }
                else{

                    HashMap<String, Object> answerObject = new HashMap<>();
                    answerObject.put("answer", answer);
                    answerObject.put("courseId", coursesModel.getCourseId());
                    answerObject.put("numberOfUpvotes", "0");
                    answerObject.put("questionId", courseQuestionsModel.getQuestion());
                    answerObject.put("userName", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    answerObject.put("userPhoto", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()));

                    db.collection("AllCourses").document(coursesModel.getCourseId())
                            .collection("Questions").document(courseQuestionsModel.getId())
                            .collection("Answers").add(answerObject).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getContext(), "Answer posted successfully", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                }
            }
        });


        return view;
    }
}