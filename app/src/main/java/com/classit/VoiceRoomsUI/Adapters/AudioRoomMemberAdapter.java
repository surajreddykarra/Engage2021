package com.classit.VoiceRoomsUI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classit.R;
import com.classit.VoiceRoomsUI.Models.AudioRoomMemberModel;
import com.classit.VoiceRoomsUI.ViewHolders.AudioRoomMemberViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AudioRoomMemberAdapter extends RecyclerView.Adapter<AudioRoomMemberViewHolder> {

    Context context;
    ArrayList<AudioRoomMemberModel> audioRoomMemberModelArrayList;

    public AudioRoomMemberAdapter(Context context, ArrayList<AudioRoomMemberModel> audioRoomMemberModelArrayList) {
        this.context = context;
        this.audioRoomMemberModelArrayList = audioRoomMemberModelArrayList;
    }

    @NonNull
    @Override
    public AudioRoomMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_audio_room_member, parent, false);
        return new AudioRoomMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioRoomMemberViewHolder holder, int position) {
        if(holder.getAdapterPosition() != -1) {
            Picasso.get()
                    .load(audioRoomMemberModelArrayList.get(position).getMemberPhoto())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.memberImage, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get()
                                    .load(audioRoomMemberModelArrayList.get(position).getMemberPhoto())
                                    .into(holder.memberImage, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return audioRoomMemberModelArrayList.size();
    }
}
