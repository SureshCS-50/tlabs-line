package com.tlabs.line;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private List<Profile> mProfiles;
    private ViewPager mPager;
    private ProgressBar mProgressBar;
    private ProfilePagerAdapter mPagerAdapter;
    private int tabPosition = 0;

    private AlertDialog mErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mPager = (ViewPager) findViewById(R.id.viewpager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Home");
        }

        if (!Utils.hasConnection(this)) {
            showErrorDialog("Network Error!", "Unable to connect to internet, Please try again");
        } else {
            getProfiles();
        }

    }

    private void getProfiles() {
        mProgressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(BuildConfig.TEST_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mProgressBar.setVisibility(View.GONE);
                mProfiles = Utils.getObjectFromJson(response.toString(), new TypeToken<List<Profile>>(){}.getType());
                if(mProfiles == null)
                    mProfiles = new ArrayList<>();
                mPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager(),
                        HomeActivity.this,
                        mProfiles);
                mPager.setAdapter(mPagerAdapter);
                mPager.setCurrentItem(tabPosition, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                showErrorDialog("Error", "Error Occurred, Please try again");
            }
        });
        request.setTag(HomeActivity.class);
        queue.add(request);
    }

    private void showErrorDialog(String title, String message) {
        if(mErrorDialog != null) {
            mErrorDialog.dismiss();
            mErrorDialog = null;
        }
        mErrorDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getProfiles();
                    }
                })
                .show();
        mErrorDialog.setCanceledOnTouchOutside(false);
        mErrorDialog.setCancelable(false);
    }

}
