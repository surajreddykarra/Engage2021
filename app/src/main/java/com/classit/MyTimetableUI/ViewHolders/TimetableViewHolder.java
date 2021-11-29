package com.classit.MyTimetableUI.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;

public class TimetableViewHolder extends RecyclerView.ViewHolder {

    public TextView courseId, courseName, courseFaculty, courseTime;
    public Button joinClass;
    public CardView timetableCard;

    public TimetableViewHolder(@NonNull View itemView) {
        super(itemView);

        courseId = itemView.findViewById(R.id.courseId);
        courseName = itemView.findViewById(R.id.courseName);
        courseFaculty = itemView.findViewById(R.id.courseFaculty);
        courseTime = itemView.findViewById(R.id.courseTime);
        joinClass = itemView.findViewById(R.id.joinClass);
        timetableCard = itemView.findViewById(R.id.timetableCard);
    }
}
