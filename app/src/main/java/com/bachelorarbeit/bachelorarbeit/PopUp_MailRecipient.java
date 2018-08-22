package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PopUp_MailRecipient extends DialogFragment {

    EditText mailInput;
    Button saveButton;

    public static PopUp_MailRecipient newInstance(){
        PopUp_MailRecipient popUp_mailRecipient = new PopUp_MailRecipient();
        Bundle args = new Bundle();
       // args.putString("time", time);
       // args.putString("daytime", daytime);
        popUp_mailRecipient.setArguments(args);
        return popUp_mailRecipient;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_mail_recipient, null);
        mailInput = dialogView.findViewById(R.id.inputMail);
        saveButton = dialogView.findViewById(R.id.saveButton_mail);
        builder.setView(dialogView);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInDb();
            }
        });
        return builder.create();
    }

    private void saveInDb(){
        if(mailInput.getText().toString().equals("")){
            //TODO Save in Db
        }
    }
}
