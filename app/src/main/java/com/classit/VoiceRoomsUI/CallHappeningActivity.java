package com.classit.VoiceRoomsUI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;
import com.classit.VoiceRoomsUI.Adapters.AudioRoomMemberAdapter;
import com.classit.VoiceRoomsUI.Models.AudioRoomMemberModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class CallHappeningActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;

    ProgressDialog progressDialog;
    ImageButton speaker, mute,endCall;
    private RtcEngine mRtcEngine;
    Boolean muted = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<AudioRoomMemberModel> audioRoomMemberModelArrayList = new ArrayList<>();
    RecyclerView roomMembersRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_happening);

        speaker = findViewById(R.id.speaker);
        mute = findViewById(R.id.mute);
        endCall = findViewById(R.id.endCall);

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRtcEngine.isSpeakerphoneEnabled()){
                    mRtcEngine.setEnableSpeakerphone(false);
                    speaker.setImageResource(R.drawable.ic_speaker);
                }
                else{
                    mRtcEngine.setEnableSpeakerphone(true);
                    speaker.setImageResource(R.drawable.ic_speaker_e);
                }
            }
        });

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!muted){
                    mRtcEngine.muteLocalAudioStream(true);
                    muted = true;
                    mute.setImageResource(R.drawable.ic_mute_e);
                }
                else {
                    mRtcEngine.muteLocalAudioStream(false);
                    muted = false;
                    mute.setImageResource(R.drawable.ic_mute);
                }
            }
        });

        endCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("RoomMembers").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRtcEngine.leaveChannel();
                        finish();                    }
                });
            }
        });

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)) {
            initAgoraEngineAndJoinChannel();
        }

        roomMembersRecycler = findViewById(R.id.roomMembersRecycler);
        roomMembersRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        roomMembersRecycler.setHasFixedSize(true);

        db.collection("RoomMembers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                audioRoomMemberModelArrayList.clear();

                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    AudioRoomMemberModel audioRoomMemberModel = new AudioRoomMemberModel();

                    audioRoomMemberModel.setMemberName(documentSnapshot.getString("name"));
                    audioRoomMemberModel.setMemberPhoto(documentSnapshot.getString("photo"));

                    audioRoomMemberModelArrayList.add(audioRoomMemberModel);
                }

                AudioRoomMemberAdapter audioRoomMemberAdapter = new AudioRoomMemberAdapter(getApplicationContext(), audioRoomMemberModelArrayList);
                roomMembersRecycler.setAdapter(audioRoomMemberAdapter);
            }
        });

    }

    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();
        joinChannel();
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }
        return true;
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {

        // Listen for the onUserOffline callback.
        // This callback occurs when the remote user leaves the channel or drops offline.
        @Override
        public void onUserOffline(final int uid, final int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }

        // Listen for the onUserMuterAudio callback.
        // This callback occurs when a remote user stops sending the audio stream.
        @Override
        public void onUserMuteAudio(final int uid, final boolean muted) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    };

    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private void joinChannel() {
        mRtcEngine.joinChannel("006491fda563b0044708be014d6a05fd483IAD8eLxwNIWkMnSgxsAZ3W9e0aKKoOLArL6149oR3tA/yZtRn3IAAAAAEABf0riprIikYQEAAQCriKRh", "room", "Extra Optional Data", 0);
        mRtcEngine.adjustPlaybackSignalVolume(400);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        data.put("photo", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()));

        db.collection("RoomMembers").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Call joined", Toast.LENGTH_SHORT).show();
            }
        });
    }

}