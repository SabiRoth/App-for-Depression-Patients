package com.bachelorarbeit.bachelorarbeit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PopUp_MainSymptoms extends DialogFragment {


    //TODO: Aus DB Werte holen und anzeigen (als Hint oder Textview mit ändern-Funktion)

    EditText input1, input2, input3;
    Button saveButton;

    public static PopUp_MainSymptoms newInstance(){
        PopUp_MainSymptoms popUp_mainSymptoms = new PopUp_MainSymptoms();
        Bundle args = new Bundle();
        // args.putString("time", time);
        // args.putString("daytime", daytime);
        popUp_mainSymptoms.setArguments(args);
        return popUp_mainSymptoms;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_main_symptoms, null);
        input1 = dialogView.findViewById(R.id.input_main_symptoms1);
        input2 = dialogView.findViewById(R.id.input_main_symptoms2);
        input3 = dialogView.findViewById(R.id.input_main_symptoms3);
        saveButton = dialogView.findViewById(R.id.saveButton_main_symptoms);
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
        if(!input1.getText().toString().equals("")){
            //TODO save in DB
        }
        if(!input2.getText().toString().equals("")){
            //TODO save in DB
        }
        if(!input3.getText().toString().equals("")){
            //TODO save in DB
        }
    }
}
