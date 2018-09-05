package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;


public class PopUp_Restart extends DialogFragment {

    public static PopUp_Restart newInstance(){
        return new PopUp_Restart();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.pop_up_restart))
                .setPositiveButton(getResources().getString(R.string.ok_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().dismiss();
                    }
                });
        return builder.create();
    }
}
