package com.classit.ProfileUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classit.LoginUI.LoginMainActivity;
import com.classit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView userImage;
    TextView userName;
    ConstraintLayout logout;

    CardView allOptionsCard;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userImage = view.findViewById(R.id.userProfileImage);
        userName = view.findViewById(R.id.userName);
        logout = view.findViewById(R.id.logout);

        allOptionsCard = view.findViewById(R.id.allOptionsCard);
        allOptionsCard.setBackground(getActivity().getDrawable(R.drawable.glass_card_bg));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Picasso.get()
                .load(firebaseUser.getPhotoUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(userImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(firebaseUser.getPhotoUrl())
                                .into(userImage);
                    }
                });

        userName.setText(firebaseUser.getDisplayName());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginMainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}