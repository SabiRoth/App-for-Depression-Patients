package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
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

public class PopUp_MailRecipient extends DialogFragment {

    EditText mailInput;
    TextView textView_recipient;
    Button saveButton;
    Button deleteButton;
    dataSource dataSource;
    View layout;

    public static PopUp_MailRecipient newInstance(){
        return new PopUp_MailRecipient();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_mail_recipient, null);
        layout = inflater.inflate(R.layout.toast, (ViewGroup) dialogView.findViewById(R.id.toast_layout));
        mailInput = dialogView.findViewById(R.id.inputMail);
        textView_recipient = dialogView.findViewById(R.id.textView_recipient);
        saveButton = dialogView.findViewById(R.id.saveButton_mail);
        deleteButton = dialogView.findViewById(R.id.deleteButton_mail);
        dataSource = new dataSource(getContext());
        dataSource.open();
        showLastRecipient();
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
        String input = mailInput.getText().toString();
        TextView toastTextView = (TextView) layout.findViewById(R.id.textView_toast);
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CLIP_VERTICAL, 0, 0);
        toast.setView(layout);
        if(!(input.equals(""))){
            if(checkEmail(input)){
                dataSource.createSettingsEntry(getResources().getString(R.string.key_mailRecipient), mailInput.getText().toString());
                CharSequence text = mailInput.getText().toString() +  " " + getResources().getString(R.string.toast_end);
                Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                this.dismiss();
            }
            else{
                toastTextView.setText(getResources().getString(R.string.toast_noValideAddress));
                toast.show();
            }
        }
        else{
            toastTextView.setText(getResources().getString(R.string.toast_noRecipient));
            toast.show();
        }
    }

    private void deleteEntry(){
        dataSource.deleteSettingsEntry(getResources().getString(R.string.key_mailRecipient));
        Toast.makeText(getContext(), getResources().getString(R.string.toast_deleted), Toast.LENGTH_LONG).show();
        this.dismiss();
    }

    private void showLastRecipient(){
        String recipient = dataSource.getSettingViaName(getResources().getString(R.string.key_mailRecipient));
        if(recipient != null){
            textView_recipient.setVisibility(View.VISIBLE);
            textView_recipient.setText(getResources().getString(R.string.pop_up_mail_already_entry) + " " + recipient);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteEntry();
                }
            });
        }
        else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    //Source: https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    );
}
