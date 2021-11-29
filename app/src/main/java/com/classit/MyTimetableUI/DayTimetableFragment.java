package com.classit.MyTimetableUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classit.MyTimetableUI.Adapters.TimetableAdapter;
import com.classit.MyTimetableUI.Models.TimetableModel;
import com.classit.R;
import com.classit.Utils.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DayTimetableFragment extends Fragment {

    String day;

    RecyclerView timetableRecycler;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<TimetableModel> timetableModelArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day_timetable, container, false);

        Bundle bundle = getArguments();
        day = bundle.getString("day");

        timetableRecycler = view.findViewById(R.id.timetableRecycler);
        timetableRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        timetableRecycler.setHasFixedSize(true);
        timetableRecycler.setNestedScrollingEnabled(false);

        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("MyCourses").whereArrayContains("courseDays", day)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                timetableModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    TimetableModel timetableModel = new TimetableModel();

                    timetableModel.setCourseId(documentSnapshot.getString("courseId"));
                    timetableModel.setCourseTime(documentSnapshot.getString("courseTime"));
                    timetableModel.setCourseFaculty(documentSnapshot.getString("courseFaculty"));
                    timetableModel.setCourseName(documentSnapshot.getString("courseName"));
                    timetableModel.setCourseSlot(Integer.parseInt(documentSnapshot.getString("courseSlot")));

                    timetableModelArrayList.add(timetableModel);
                }

                Collections.sort(timetableModelArrayList, new Comparator<TimetableModel>() {
                    @Override public int compare(TimetableModel t1, TimetableModel t2) {
                        return t1.getCourseSlot()- t2.getCourseSlot();
                    }
                });

                TimetableAdapter timetableAdapter = new TimetableAdapter(getContext(), timetableModelArrayList);
                timetableRecycler.setAdapter(timetableAdapter);
            }
        });

        return view;
    }

}