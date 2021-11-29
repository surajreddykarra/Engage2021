package com.classit.AskAndAnswerQuestionsUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.classit.CourseDiscussionsUI.Models.CourseQuestionsModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AskQuestionFragment extends Fragment {

    Button sendQuestion;
    EditText question;

    CoursesModel coursesModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ask_question, container, false);

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("course");

        question = view.findViewById(R.id.question_edit);
        sendQuestion = view.findViewById(R.id.submit);

        sendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String questionAsked = question.getText().toString().trim();

                if (TextUtils.isEmpty(questionAsked)) {
                    Toast.makeText(getContext(), "Please enter a question to continue", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("numberOfUpvotes", "0");
                    data.put("numberOfAnswers", "0");
                    data.put("studentName", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    data.put("studentPhoto", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()));
                    data.put("question", questionAsked);

                    db.collection("AllCourses").document(coursesModel.getCourseId())
                            .collection("Questions").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getContext(), "Question posted successfully", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                }
            }
        });

        return view;
    }
}