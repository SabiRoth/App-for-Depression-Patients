package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PopUp_MailRecipient extends DialogFragment {

    EditText mailInput;
    TextView textView_recipient;
    Button saveButton;
    dataSource dataSource;

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
        textView_recipient = dialogView.findViewById(R.id.textView_recipient);
        saveButton = dialogView.findViewById(R.id.saveButton_mail);
        dataSource = new dataSource(getContext());
        dataSource.open();
        proofAlreadySaved();
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
        if(!(mailInput.getText().toString().equals(""))){
            dataSource.createSettingsEntry("mailRecipient", mailInput.getText().toString());
            CharSequence text = mailInput.getText().toString() +  " " + getResources().getString(R.string.toast_end);
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        }
        //TODO: FRAGMENT AUSBLENDEN MÖGLICH?
        Intent i = new Intent(getActivity(), SettingsActivity.class);
        startActivity(i);
    }

    private void proofAlreadySaved(){
        String recipient = dataSource.getSettingViaName("mailRecipient");
        if(recipient != null){
            textView_recipient.setVisibility(View.VISIBLE);
            textView_recipient.setText(getResources().getString(R.string.pop_up_mail_already_entry) + " " + recipient);
        }
    }
}
