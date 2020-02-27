package com.NRB.gpas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpAdminDialog extends AppCompatDialogFragment {
    private EditText editEmail;
    private EditText editPassword;
    private EditText editConfirmPassword;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private SignUpAdminDialog.SignUpAdminDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.sign_up_admin_dialog, null);

        editConfirmPassword = view.findViewById(R.id.sign_up_confirm_password);
        editEmail = view.findViewById(R.id.sign_up_email);
        editPassword = view.findViewById(R.id.sign_up_password);
        builder.setView(view)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String confirmPassword = editConfirmPassword.getText().toString();
                        String password = editPassword.getText().toString();
                        String email = editEmail.getText().toString();

                        if (!email.matches(emailPattern)) {
                            Toast.makeText(getContext(), "Please provide a valid Email address", Toast.LENGTH_SHORT).show();

                        } else if (password.isEmpty()) {
                            Toast.makeText(getContext(), "Enter a valid Password", Toast.LENGTH_SHORT).show();


                        } else if (password.length() < 8) {
                            Toast.makeText(view.getContext(), "Password should contain at least 8 characters", Toast.LENGTH_SHORT).show();
                        } else if (confirmPassword.isEmpty()) {
                            Toast.makeText(view.getContext(), "Confirm password", Toast.LENGTH_SHORT).show();
                        } else if (!password.matches(confirmPassword)) {
                            Toast.makeText(view.getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                        } else {
                            listener.applyTexts(email, confirmPassword);
                        }



                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SignUpAdminDialog.SignUpAdminDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement SignUpAdminDialogListener");
        }
    }

    public interface SignUpAdminDialogListener {
        void applyTexts(String email, String password);
    }

}
