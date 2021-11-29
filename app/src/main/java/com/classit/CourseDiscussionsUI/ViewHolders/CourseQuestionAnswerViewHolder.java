package com.classit.CourseDiscussionsUI.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseQuestionAnswerViewHolder extends RecyclerView.ViewHolder {

    public CardView questionCard;
    public CircleImageView userImage;
    public TextView userName, answer;
    public ImageButton upvoteButton;
    public TextView numberOfUpvotes;

    public CourseQuestionAnswerViewHolder(@NonNull View itemView) {
        super(itemView);

        questionCard = itemView.findViewById(R.id.questionCard);
        userImage = itemView.findViewById(R.id.userPhoto);
        userName = itemView.findViewById(R.id.userName);
        answer = itemView.findViewById(R.id.answer);
        upvoteButton = itemView.findViewById(R.id.upvoteButton);
        numberOfUpvotes = itemView.findViewById(R.id.numberOfUpvotes);


    }
}
