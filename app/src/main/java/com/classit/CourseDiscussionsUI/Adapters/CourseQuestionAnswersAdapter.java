package com.classit.CourseDiscussionsUI.Adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.CourseDiscussionsUI.Models.CourseQuestionAnswerModel;
import com.classit.CourseDiscussionsUI.ViewHolders.CourseQuestionAnswerViewHolder;
import com.classit.CourseDiscussionsUI.Models.CourseQuestionsModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseQuestionAnswersAdapter extends RecyclerView.Adapter<CourseQuestionAnswerViewHolder> {

    Context context;
    ArrayList<CourseQuestionAnswerModel> courseQuestionAnswerModelArrayList;
    CourseQuestionsModel courseQuestionsModel;
    CoursesModel coursesModel;

    public CourseQuestionAnswersAdapter(Context context, ArrayList<CourseQuestionAnswerModel> courseQuestionAnswerModelArrayList, CourseQuestionsModel courseQuestionsModel, CoursesModel coursesModel) {
        this.context = context;
        this.courseQuestionAnswerModelArrayList = courseQuestionAnswerModelArrayList;
        this.courseQuestionsModel = courseQuestionsModel;
        this.coursesModel = coursesModel;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public CourseQuestionAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_course_question_answer, parent, false);
        return new CourseQuestionAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseQuestionAnswerViewHolder holder, int position) {

        Picasso.get()
                .load(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getUserPhoto())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.userImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getUserPhoto())
                                .into(holder.userImage);
                    }
                });

        holder.questionCard.setBackground(context.getDrawable(R.drawable.glass_card_bg));
        holder.userName.setText(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getUserName());
        holder.answer.setText(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getAnswer());
        holder.numberOfUpvotes.setText(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes());

        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("UpvotedAnswers").document(courseQuestionAnswerModelArrayList.get(position).getAnswerId())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();

                if(documentSnapshot.exists()){
                    courseQuestionAnswerModelArrayList.get(position).setUpvoted(true);
                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvoted));
                    holder.upvoteButton.setVisibility(View.VISIBLE);
                }
                else{
                    courseQuestionAnswerModelArrayList.get(position).setUpvoted(false);
                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvote));
                    holder.upvoteButton.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getUpvoted()){

                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvote));
                    holder.numberOfUpvotes.setText(String.valueOf(Integer.parseInt(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) - 1));
                    courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).setUpvoted(false);

                    courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).setNumberOfUpvotes(
                            String.valueOf(Integer.parseInt(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) - 1)
                    );

                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("UpvotedAnswers").document(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getAnswerId())
                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("numberOfUpvotes", courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes());

                            db.collection("AllCourses").document(coursesModel.getCourseId()).collection("Questions")
                            .document(courseQuestionsModel.getId()).collection("Answers").document(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getAnswerId()).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                }
                else {

                    holder.upvoteButton.setImageDrawable(context.getDrawable(R.drawable.ic_upvoted));
                    holder.numberOfUpvotes.setText(String.valueOf(Integer.parseInt(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) + 1));
                    courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).setUpvoted(true);

                    courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).setNumberOfUpvotes(
                            String.valueOf(Integer.parseInt(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes()) + 1)
                    );

                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("UpvotedAnswers").document(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getAnswerId())
                            .set(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("numberOfUpvotes", courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getNumberOfUpvotes());

                            db.collection("AllCourses").document(coursesModel.getCourseId()).collection("Questions")
                                    .document(courseQuestionsModel.getId()).collection("Answers").document(courseQuestionAnswerModelArrayList.get(holder.getAdapterPosition()).getAnswerId()).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        return courseQuestionAnswerModelArrayList.size();
    }
}
