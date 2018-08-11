package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class MyAlertDialogFragment extends DialogFragment{

    public static MyAlertDialogFragment newInstance(String time, String daytime){
        MyAlertDialogFragment sd = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("time", time);
        args.putString("daytime", daytime);
        sd.setArguments(args);
        return sd;
    }

    //TODO: Strings aulagern

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Es ist " + getArguments().getString("time") + " Uhr. Gib deine Befindlichkeit und deine Aktivitäten von heute " + getArguments().getString("daytime") + " ein!" )
                //.setView()
                .setPositiveButton("Zur Eingabe", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), SensitivitiesActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        startActivity(i);
                    }
                });

        return builder.create();
    }
}