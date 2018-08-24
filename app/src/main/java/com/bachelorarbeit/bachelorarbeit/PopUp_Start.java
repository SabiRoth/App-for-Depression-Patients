package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class PopUp_Start extends DialogFragment{

    public static PopUp_Start newInstance(String time, String daytime){
        PopUp_Start sd = new PopUp_Start();
        Bundle args = new Bundle();
        args.putString("time", time);
        args.putString("daytime", daytime);
        sd.setArguments(args);
        return sd;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.pop_up_start_part1)+ " " + getArguments().getString("time") + " " + getResources().getString(R.string.pop_up_start_part2) + " " + getArguments().getString("daytime") + " " + getResources().getString(R.string.pop_up_start_part3))
                //.setView()
                .setPositiveButton(getResources().getString(R.string.input_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), ScoreActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.close_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        startActivity(i);
                    }
                });

        return builder.create();
    }
}