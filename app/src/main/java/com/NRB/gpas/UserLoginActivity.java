package com.NRB.gpas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class UserLoginActivity extends AppCompatActivity {

    private EditText contactEt, otpEt;
    private Button sendOtp, userlogin;
    private String contact, otp, verificationCode;
    private TextInputLayout contactTil, otpTil;
    private ProgressDialog mProLogin;
    private Intent intent;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        intent = new Intent(this, UserHome.class);
        auth = FirebaseAuth.getInstance();
        mProLogin = new ProgressDialog(this);
        contactEt = findViewById(R.id.user_login_contact);
        otpEt = findViewById(R.id.user_login_otp);

        contactTil = findViewById(R.id.contact_til);
        otpTil = findViewById(R.id.otp_til);

        sendOtp = findViewById(R.id.btn_send_otp);
        userlogin = findViewById(R.id.btn_user_login);

    }

    public void sendOtp(View view) {

        contact = contactEt.getText().toString();

        if (contact.isEmpty()) {
            Toast.makeText(this, "Enter your contact number", Toast.LENGTH_SHORT).show();

        } else if (contact.length() != 10) {
            Toast.makeText(this, "Invalid contact number", Toast.LENGTH_SHORT).show();
        } else {
            mProLogin.setTitle("Signing you in");


            mProLogin.setCanceledOnTouchOutside(false);

            mProLogin.show();

            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    mProLogin.dismiss();

                    auth.signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mProLogin.dismiss();
                                        startActivity(intent);
                                        otpEt.setText("");
                                        contactEt.setText("");
                                        Toast.makeText(UserLoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(UserLoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    mProLogin.dismiss();
                    Toast.makeText(UserLoginActivity.this, "Verification failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCode = s;
                    Toast.makeText(UserLoginActivity.this, "OTP sent to +91" + contact, Toast.LENGTH_SHORT).show();
                    userlogin.setVisibility(View.VISIBLE);
                    sendOtp.setVisibility(View.INVISIBLE);
                    contactTil.setVisibility(View.INVISIBLE);
                    otpTil.setVisibility(View.VISIBLE);
                    mProLogin.dismiss();
                }
            };

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + contact,                     // Phone number to verify
                    120,                           // Timeout duration
                    TimeUnit.SECONDS,                // Unit of timeout
                    UserLoginActivity.this,        // Activity (for callback binding)
                    mCallback);
        }


    }

    public void login(View view) {


        otp = otpEt.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(getApplicationContext(), "Enter OTP", Toast.LENGTH_SHORT).show();

            return;
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
            mProLogin.setTitle("Verifying your OTP");

            mProLogin.setCanceledOnTouchOutside(false);

            mProLogin.show();


            auth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mProLogin.dismiss();
                                startActivity(intent);
                                otpEt.setText("");
                                contactEt.setText("");
                                Toast.makeText(UserLoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserLoginActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void gotoAdmin(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}
