package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class PopUp_Send extends DialogFragment {

    private String recipient;
    private EditText input;
    private View layout;
    private String content;

    public static PopUp_Send newInstance(String content){
        PopUp_Send popUp_send = new PopUp_Send();
        Bundle args = new Bundle();
        args.putString("content", content);
        popUp_send.setArguments(args);
        return popUp_send;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_send, null);
        Button sendButtonStandard = dialogView.findViewById(R.id.sendButtonStandard);
        input = dialogView.findViewById(R.id.input_send);
        TextView hinttext = dialogView.findViewById(R.id.hinttext_sendMail);
        TextView sendAlternative = dialogView.findViewById(R.id.TextView_send_alternative);
        layout = inflater.inflate(R.layout.toast, (ViewGroup) dialogView.findViewById(R.id.toast_layout));
        content = getArguments().getString(getResources().getString(R.string.key_content));
        dataSource dataSource = new dataSource(getContext());
        dataSource.open();
        recipient =  dataSource.getSettingViaName(getResources().getString(R.string.key_mailRecipient));
        dataSource.close();
        if(recipient==null || recipient.equals("")){
           sendButtonStandard.setVisibility(View.GONE);
           sendAlternative.setText(getResources().getString(R.string.pop_up_send_AlternativeNoStandard));
           hinttext.setVisibility(View.VISIBLE);
        }
        else{
            sendButtonStandard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendEmail();
                }
            });
        }
        Button sendButtonManual = dialogView.findViewById(R.id.sendButtonManual);
        sendButtonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendButtonManualClicked();
            }
        });
        builder.setView(dialogView);
        return builder.create();
    }

    private void sendButtonManualClicked(){
        String inputText = input.getText().toString();
        TextView toastTextView = (TextView) layout.findViewById(R.id.textView_toast);
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CLIP_VERTICAL, 0, 0);
        toast.setView(layout);
        if(!inputText.equals("")){
            if(checkEmail(inputText)){
                recipient = inputText;
                sendEmail();
            }
            else {
                toastTextView.setText(getResources().getString(R.string.toast_noValideAddress));
                toast.show();
            }
        }
        else{
            toastTextView.setText(getResources().getString(R.string.toast_noRecipient));
            toast.show();
        }
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    //Source: https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
       "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    );

    /*
       Sending an email with the entries of the week via email-client of the smartphone
     */
    private void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,  Uri.fromParts("mailto",recipient, null));
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
        try {
            startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.toast_chooseClient)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), getResources().getString(R.string.toast_noClient), Toast.LENGTH_SHORT).show();
        }
        this.dismiss();
    }
}
