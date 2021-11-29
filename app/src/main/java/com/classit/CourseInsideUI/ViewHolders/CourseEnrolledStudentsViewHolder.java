package com.classit.CourseInsideUI.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseEnrolledStudentsViewHolder extends RecyclerView.ViewHolder {

    public TextView studentName;
    public CircleImageView studentImage;
    public CardView studentCard;

    public CourseEnrolledStudentsViewHolder(@NonNull View itemView) {
        super(itemView);

        studentName = itemView.findViewById(R.id.studentName);
        studentImage = itemView.findViewById(R.id.studentPhoto);
        studentCard = itemView.findViewById(R.id.studentCard);

    }
}
