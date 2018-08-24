package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ScoreActivity extends AppCompatActivity {

    dataSource dataSource;
    TextView firstMainSymptom;
    TextView secondMainSymptom;
    TextView thirdMainSymptom;
    Button nextButton;
    String[] mainSymptomsArray;
    DateTimePicker dateTimePicker;
    ArrayList<String[]> allEntries;
    LinearLayout layoutFirstMainSymptom;
    ArrayList<Button> clickedButtonScore;
    ArrayList<Button> clickedButtonListFirstSymptom;
    ArrayList<Button> clickedButtonListSecondSymptom;
    ArrayList<Button> clickedButtonListThirdSymptom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        dateTimePicker = DateTimePicker.getInstance();
        allEntries = new ArrayList<>();
        clickedButtonScore = new ArrayList<>();
        clickedButtonListFirstSymptom = new ArrayList<>();
        clickedButtonListSecondSymptom = new ArrayList<>();
        clickedButtonListThirdSymptom = new ArrayList<>();
        dataSource = new dataSource(this);
        dataSource.open();
        LinearLayout layoutScore = (LinearLayout)findViewById(R.id.score_listentry);
        for(int j = 0; j<6; j++){
            Button buttonScore = new Button(this);
            buttonScore.setText(Integer.toString(j));
            buildButton(buttonScore);
            layoutScore.addView(buttonScore);
            buttonScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scoreButtonClicked(view);
                }
            });
        }

        String mainSymptomsString  = dataSource.getSettingViaName(getResources().getString(R.string.key_mainSymptomsinSettingsTable));
        if(mainSymptomsString==null || mainSymptomsString.equals("")) {
            mainSymptomsArray = new String[0];
        }
        else{
            mainSymptomsArray = mainSymptomsString.split(",");
        }

        getMainSymptoms();

        nextButton = (Button)findViewById(R.id.button_score_next);
        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextButtonClicked();
                    }
                });
        initializeButtonClickListener();
    }

    private void getMainSymptoms(){
        if(mainSymptomsArray.length>0){
            for(int i = 0; i < mainSymptomsArray.length; i++){
                if(i==0){
                    layoutFirstMainSymptom = (LinearLayout)findViewById(R.id.score_listentry_first_symptom);
                    firstMainSymptom = (TextView)findViewById(R.id.textView_firstMainSymptom);
                    firstMainSymptom.setText(mainSymptomsArray[i] + ": " + getResources().getString(R.string.score_textview));
                    firstMainSymptom.setVisibility(View.VISIBLE);
                    for(int j = 0; j<6; j++){
                        Button buttonFirstMainSymptom = new Button(this);
                        buttonFirstMainSymptom.setText(Integer.toString(j));
                        buildButton(buttonFirstMainSymptom);
                        layoutFirstMainSymptom.addView(buttonFirstMainSymptom);
                        buttonFirstMainSymptom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                firstMainSymptomButtonClicked(view);
                            }
                        });
                    }

                }
                if(i==1){
                    LinearLayout layoutSecondMainSymptom = (LinearLayout)findViewById(R.id.score_listentry_second_symptom);
                    secondMainSymptom = (TextView)findViewById(R.id.textView_secondMainSymptom);
                    secondMainSymptom.setText(mainSymptomsArray[i].substring(1) + ": " + getResources().getString(R.string.score_textview));
                    secondMainSymptom.setVisibility(View.VISIBLE);
                    for(int j = 0; j<6; j++){
                        Button buttonSecondMainSymptom = new Button(this);
                        buttonSecondMainSymptom.setText(Integer.toString(j));
                        buildButton(buttonSecondMainSymptom);
                        layoutSecondMainSymptom.addView(buttonSecondMainSymptom);
                        buttonSecondMainSymptom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                secondMainSymptomButtonClicked(view);
                            }
                        });
                    }
                }
                if(i==2){
                    LinearLayout layoutThirdMainSymptom = (LinearLayout)findViewById(R.id.score_listentry_third_symptom);
                    thirdMainSymptom = (TextView)findViewById(R.id.textView_thirdMainSymptom);
                    thirdMainSymptom.setText(mainSymptomsArray[i].substring(1) + ": " + getResources().getString(R.string.score_textview));
                    thirdMainSymptom.setVisibility(View.VISIBLE);
                    for(int j = 0; j<6; j++){
                        Button buttonThirdMainSymptom = new Button(this);
                        buttonThirdMainSymptom.setText(Integer.toString(j));
                        buildButton(buttonThirdMainSymptom);
                        layoutThirdMainSymptom.addView(buttonThirdMainSymptom);
                        buttonThirdMainSymptom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                thirdMainSymptomButtonClicked(view);
                            }
                        });
                    }
                }
            }
        }
    }

    private void nextButtonClicked(){
        if(allEntries.size()==mainSymptomsArray.length+1) {
            for (int i = 0; i < allEntries.size(); i++) {
                dataSource.createMainSymptomsEntry(allEntries.get(i)[0], allEntries.get(i)[1], allEntries.get(i)[2], allEntries.get(i)[3]);
            }
            Intent i = new Intent(this, SensitivitiesActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.missing_entry_score), Toast.LENGTH_LONG).show();
        }
    }

    private void initializeButtonClickListener(){

    }

    private void buildButton(Button button){
        LinearLayout.LayoutParams paramsForButton = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                (float)0.16
        );
        paramsForButton.setMargins(5, 5,5, 5);
        button.setLayoutParams(paramsForButton);
        button.setBackgroundColor(getResources().getColor(R.color.weightButtonColor));
        button.setTextSize(20);
    }

    private void firstMainSymptomButtonClicked(View view){
        Button clickedButton = (Button) view;
        if(clickedButtonListFirstSymptom.size()>0){
            clickedButtonListFirstSymptom.get(0).setBackgroundColor(getResources().getColor(R.color.weightButtonColor));
            clickedButtonListFirstSymptom.remove(0);
        }
        clickedButtonListFirstSymptom.add(clickedButton);
        String buttonText = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        String[] entry = {mainSymptomsArray[0], buttonText, dateTimePicker.getCurrentDate(), dateTimePicker.getCurrentTime()};
        allEntries.add(entry);

    }

    private void secondMainSymptomButtonClicked(View view){
        Button clickedButton = (Button) view;
        if(clickedButtonListSecondSymptom.size()>0){
            clickedButtonListSecondSymptom.get(0).setBackgroundColor(getResources().getColor(R.color.weightButtonColor));
            clickedButtonListSecondSymptom.remove(0);
        }
        clickedButtonListSecondSymptom.add(clickedButton);
        String buttonText = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        String[] entry = {mainSymptomsArray[1], buttonText, dateTimePicker.getCurrentDate(), dateTimePicker.getCurrentTime()};
        allEntries.add(entry);
    }

    private void thirdMainSymptomButtonClicked(View view){
        Button clickedButton = (Button) view;
        if(clickedButtonListThirdSymptom.size()>0){
            clickedButtonListThirdSymptom.get(0).setBackgroundColor(getResources().getColor(R.color.weightButtonColor));
            clickedButtonListThirdSymptom.remove(0);
        }
        clickedButtonListThirdSymptom.add(clickedButton);
        String buttonText = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        String[] entry = {mainSymptomsArray[2], buttonText, dateTimePicker.getCurrentDate(), dateTimePicker.getCurrentTime()};
        allEntries.add(entry);
    }

    private void scoreButtonClicked(View view){
        Button clickedButton = (Button) view;
        if(clickedButtonScore.size()>0){
            clickedButtonScore.get(0).setBackgroundColor(getResources().getColor(R.color.weightButtonColor));
            clickedButtonScore.remove(0);
        }
        clickedButtonScore.add(clickedButton);
        String buttonText = clickedButton.getText().toString();
        clickedButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        String[] entry = {getResources().getString(R.string.key_score), buttonText, dateTimePicker.getCurrentDate(), dateTimePicker.getCurrentTime()};
        allEntries.add(entry);
    }

}
