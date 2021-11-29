package com.classit.CourseChatUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.classit.CourseChatUI.Adapters.MessageAdapter;
import com.classit.CourseChatUI.Models.MessageModel;
import com.classit.CoursesHelper.Models.CoursesModel;
import com.classit.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class CourseChatFragement extends Fragment {

    ImageButton sendButtom;
    EditText txtMessage;

    RecyclerView recyclerView;
    ArrayList<MessageModel> messageModelArrayList = new ArrayList<>();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    CoursesModel coursesModel;
    String courseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_chat, container, false);

        Bundle bundle = getArguments();
        coursesModel = (CoursesModel) bundle.getSerializable("course");
        courseId = coursesModel.getCourseId();

        sendButtom = view.findViewById(R.id.sendButton);
        txtMessage = view.findViewById(R.id.message_edit);

        messageModelArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.message_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Chats").child(courseId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messageModel = new MessageModel();
                    messageModel.setMessage(dataSnapshot.child("message").getValue().toString());
                    messageModel.setSentUserId(dataSnapshot.child("sentUserId").getValue().toString());
                    messageModel.setSentUserName(dataSnapshot.child("sentUserName").getValue().toString());

                    messageModelArrayList.add(messageModel);
                }

                MessageAdapter messageAdapter = new MessageAdapter(getContext(),messageModelArrayList);
                recyclerView.setAdapter(messageAdapter);
                recyclerView.getLayoutManager().scrollToPosition(messageModelArrayList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        return view;
    }

    private void sendMessage() {

        String messageSent = txtMessage.getText().toString().trim();
        String sentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String sentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        if(messageSent.equals("")){
            Toast.makeText(getContext(),"Enter a message",Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = firebaseDatabase.getReference();

        HashMap<String , Object> message = new HashMap<>();
        message.put("message",messageSent);
        message.put("sentUserId",sentUserId);
        message.put("sentUserName",sentUserName);

        databaseReference.child("Chats").child(courseId).push().setValue(message).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error", String.valueOf(e));
            }
        });

        txtMessage.setText("");

        recyclerView.getLayoutManager().scrollToPosition(messageModelArrayList.size()-1);
    }

}