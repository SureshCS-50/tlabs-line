package com.tlabs.line;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    LinePreferenceManager mPreferenceManager;
    private TypefaceTextView mBtnLogin;
    private EditText mEtEmail, mEtPassword;
    private String mStrEmail = "", mStrPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPreferenceManager = LinePreferenceManager.getInstance(this);

        mBtnLogin = (TypefaceTextView) findViewById(R.id.btnLogin);
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mEtPassword = (EditText) findViewById(R.id.etPassword);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });

        mEtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doLogin();
                    return true;
                }
                return false;
            }
        });

        if (mPreferenceManager.isLoggedIn()) {
            showHomeActivity();
        }
    }

    private boolean validateFields() {
        mStrEmail = mEtEmail.getText().toString().trim();
        mStrPassword = mEtPassword.getText().toString().trim();
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if (mStrEmail.isEmpty()) {
            mEtEmail.setError("Please enter your Email");
            mEtEmail.requestFocus();
            return false;
        }

        if (mStrPassword.isEmpty()) {
            mEtPassword.setError("Please enter Password");
            mEtPassword.requestFocus();
            return false;
        }

        if (!pattern.matcher(mStrEmail).matches()) {
            mEtEmail.setError("Please enter valid Email");
            mEtEmail.requestFocus();
            return false;
        }
        return true;
    }

    private void doLogin() {
        mStrEmail = mEtEmail.getText().toString().trim();
        mStrPassword = mEtPassword.getText().toString();
        try {
            InputMethodManager imm = (InputMethodManager) LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEtPassword.getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (validateFields()) {
            if (mStrEmail.equalsIgnoreCase(BuildConfig.EMAIL) && mStrPassword.equalsIgnoreCase(BuildConfig.PASSWORD)) {
                mPreferenceManager.setIsLoggedIn(true);
                showHomeActivity();
            } else {
                Toast.makeText(this, "Email or Password is invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showHomeActivity() {
        Intent iHome = new Intent(this, HomeActivity.class);
        startActivity(iHome);
        finish();
    }
}
