package app.appsmatic.com.driversapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.appsmatic.com.driversapp.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {

    CircleImageView profileImg;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImg=(CircleImageView)getActivity().findViewById(R.id.profilepic);
        profileImg.setBorderColor(R.color.colorPrimary);
        profileImg.setBorderWidth(3);

        Picasso.with(getContext()).load("http://steezo.com/wp-content/uploads/2012/12/man-in-suit2.jpg").fit().placeholder(R.drawable.photo_circle).into(profileImg);



    }
}
