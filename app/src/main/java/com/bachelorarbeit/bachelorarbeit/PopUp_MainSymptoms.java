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

import java.util.ArrayList;
import java.util.Arrays;

public class PopUp_MainSymptoms extends DialogFragment {


    //TODO: Aus DB Werte holen und anzeigen (als Hint oder Textview mit ändern-Funktion)

    EditText input1, input2, input3;
    TextView textView_mainSymptoms;
    Button saveButton;
    dataSource dataSource;

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
        textView_mainSymptoms = dialogView.findViewById(R.id.textView_mainSymptoms);
        saveButton = dialogView.findViewById(R.id.saveButton_main_symptoms);
        builder.setView(dialogView);
        dataSource = new dataSource(getContext());
        dataSource.open();
        proofAlreadySaved();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInDb();
            }
        });
        return builder.create();
    }

    private void saveInDb(){
        ArrayList<String> inputs= new ArrayList<>();
        if(!input1.getText().toString().equals("")){
          inputs.add(input1.getText().toString());
        }
        if(!input2.getText().toString().equals("")){
            inputs.add(input2.getText().toString());
        }
        if(!input3.getText().toString().equals("")){
            inputs.add(input3.getText().toString());
        }

        String[] temp = new String[inputs.size()];
        String inputsString = Arrays.toString(inputs.toArray(temp));
        dataSource.createSettingsEntry("mainSymptoms", inputsString.substring(1, inputsString.length()-1));

            Toast.makeText(getContext(), getResources().getString(R.string.toast), Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), SettingsActivity.class);
            startActivity(i);

    }

    private void proofAlreadySaved(){
        String mainSymptomsString = dataSource.getSettingViaName("mainSymptoms");
        if(!mainSymptomsString.equals("")){
            textView_mainSymptoms.setVisibility(View.VISIBLE);
            textView_mainSymptoms.setText(getResources().getString(R.string.pop_up_mail_already_entry) + " " + mainSymptomsString);
        }
    }
}
