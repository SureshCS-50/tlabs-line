package com.tlabs.line;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private String mProfileJson = "";
    private Profile mProfile;
    private ImageView mProfilePic;
    private TextView mTxtName, mTxtLocation, mTxtExperience;
    private FrameLayout mBtnCall;

    public static ProfileFragment newInstance(String strProfile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PROFILE, strProfile);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfileJson = getArguments().getString(Constants.KEY_PROFILE);
            mProfile = Utils.getObjectFromJson(mProfileJson, new TypeToken<Profile>() {
            }.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        try {
            mProfilePic = (ImageView) view.findViewById(R.id.imgDesign);
            mTxtName = (TextView) view.findViewById(R.id.txtName);
            mTxtLocation = (TextView) view.findViewById(R.id.txtLocation);
            mTxtExperience = (TextView) view.findViewById(R.id.txtExperience);
            mBtnCall = (FrameLayout) view.findViewById(R.id.btnDialer);

            Picasso.with(getActivity())
                    .load(mProfile.url)
                    .into(mProfilePic);

            mTxtName.setText(mProfile.name);
            mTxtLocation.setText(mProfile.location);
            mTxtExperience.setText(mProfile.exp);

            mBtnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+mProfile.mobile));
                    startActivity(intent);

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }

}