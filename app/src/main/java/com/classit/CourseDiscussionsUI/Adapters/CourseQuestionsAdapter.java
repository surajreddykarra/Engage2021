package com.classit.CourseDiscussionsUI.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.CourseDiscussionsUI.CourseQuestionAnswersFragment;
import com.classit.CourseDiscussionsUI.Models.CourseQuestionsModel;
import com.classit.CourseDiscussionsUI.ViewHolders.CourseQuestionsViewHolder;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseQuestionsAdapter extends RecyclerView.Adapter<CourseQuestionsViewHolder> {

    Context context;
    ArrayList<CourseQuestionsModel> courseQuestionsModelArrayList;
    CoursesModel coursesModel;

    public CourseQuestionsAdapter(Context context, ArrayList<CourseQuestionsModel> courseQuestionsModelArrayList, CoursesModel coursesModel) {
        this.context = context;
        this.courseQuestionsModelArrayList = courseQuestionsModelArrayList;
        this.coursesModel = coursesModel;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public CourseQuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_course_question, parent, false);
        return new CourseQuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseQuestionsViewHolder holder, int position) {

        holder.question.setText(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getQuestion());
        holder.numberOfAnswers.setText(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfAnswers() + " Answers");
        holder.numberOfUpvotes.setText(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes());

        holder.exploreCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));
        holder.exploreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("questionItem", courseQuestionsModelArrayList.get(holder.getAdapterPosition()));
                bundle.putSerializable("course", coursesModel);

                CourseQuestionAnswersFragment courseQuestionAnswersFragment = new CourseQuestionAnswersFragment();
                courseQuestionAnswersFragment.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,courseQuestionAnswersFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("UpvotedQuestions").document(courseQuestionsModelArrayList.get(position).getId())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();

                if(documentSnapshot.exists()){
                    courseQuestionsModelArrayList.get(position).setUpvoted(true);
                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvoted));
                    holder.upvoteButton.setVisibility(View.VISIBLE);
                }
                else{
                    courseQuestionsModelArrayList.get(position).setUpvoted(false);
                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvote));
                    holder.upvoteButton.setVisibility(View.VISIBLE);
                }
            }
        });


        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getUpvoted()){

                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvote));
                    holder.numberOfUpvotes.setText(String.valueOf(Integer.parseInt(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) - 1));
                    courseQuestionsModelArrayList.get(holder.getAdapterPosition()).setUpvoted(false);

                    courseQuestionsModelArrayList.get(holder.getAdapterPosition()).setNumberOfUpvotes(
                            String.valueOf(Integer.parseInt(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) - 1)
                    );

                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("UpvotedQuestions").document(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getId())
                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("numberOfUpvotes", courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes());

                            db.collection("AllCourses").document(coursesModel.getCourseId()).collection("Questions")
                                    .document(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getId())
                                    .update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                }
                else {

                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvoted));
                    holder.numberOfUpvotes.setText(String.valueOf(Integer.parseInt(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) + 1));
                    courseQuestionsModelArrayList.get(holder.getAdapterPosition()).setUpvoted(true);

                    courseQuestionsModelArrayList.get(holder.getAdapterPosition()).setNumberOfUpvotes(
                            String.valueOf(Integer.parseInt(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) + 1)
                    );

                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("UpvotedQuestions").document(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getId())
                            .set(courseQuestionsModelArrayList.get(holder.getAdapterPosition())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("numberOfUpvotes", courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes());

                            db.collection("AllCourses").document(coursesModel.getCourseId()).collection("Questions")
                                    .document(courseQuestionsModelArrayList.get(holder.getAdapterPosition()).getId())
                                    .update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseQuestionsModelArrayList.size();
    }
}
