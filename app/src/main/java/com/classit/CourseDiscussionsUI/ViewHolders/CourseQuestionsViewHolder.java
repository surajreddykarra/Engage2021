package com.classit.CourseDiscussionsUI.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

public class CourseQuestionsViewHolder extends RecyclerView.ViewHolder{

    public TextView question, numberOfAnswers, numberOfUpvotes;
    public ImageButton upvoteButton;
    public CardView exploreCard;

    public CourseQuestionsViewHolder(@NonNull View itemView) {
        super(itemView);

        question = itemView.findViewById(R.id.question);
        numberOfAnswers = itemView.findViewById(R.id.numberOfAnswers);
        numberOfUpvotes = itemView.findViewById(R.id.numberOfUpvotes);
        upvoteButton = itemView.findViewById(R.id.upvoteButton);
        exploreCard = itemView.findViewById(R.id.questionCard);

    }
}
