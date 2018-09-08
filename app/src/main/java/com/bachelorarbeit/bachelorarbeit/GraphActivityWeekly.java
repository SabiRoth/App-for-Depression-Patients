package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.softmoore.android.graphlib.*;

import com.softmoore.android.graphlib.Label;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GraphActivityWeekly extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private String[] spinnerList, mainSymptoms;
    private Spinner spinnerView, spinnerGraph;
    private DateTimePicker dateTimePicker;
    int check = 0;
    private dataSource dataSource;
    final int DAYS_OF_WEEK = 7;
    private String newDate, contentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Button datePickerButton = (Button) findViewById(R.id.button_date_picker_calendar);
        datePickerButton.setVisibility(View.GONE);
        spinnerList = getResources().getStringArray(R.array.spinner_view_weekly);
        spinnerView = (Spinner) findViewById(R.id.spinner);
        spinnerGraph = (Spinner) findViewById(R.id.spinner_graph);
        dateTimePicker = DateTimePicker.getInstance();
        dataSource = new dataSource(this);
        dataSource.open();
        contentIntent = getIntent().getStringExtra(getResources().getString(R.string.key_spinner_graph));
        if(dataSource.getSettingViaName(getResources().getString(R.string.key_mainSymptoms))!=null){
            mainSymptoms = (getResources().getString(R.string.key_score) + "," + dataSource.getSettingViaName(getResources().getString(R.string.key_mainSymptoms))).split(",");
        }
        else{
            mainSymptoms = new String[1];
            mainSymptoms[0] = getResources().getString(R.string.key_score);
        }
        newDate = dateTimePicker.getCurrentDate();
        createGraph();
        initializeSpinner();
        dataSource.close();
    }

    private void createGraph(){
        Calendar calendar = Calendar.getInstance();
        Point[] activityPoints, scorePoints;
        Label[] xLabels = new Label[7];
        ArrayList<Point> activityList = new ArrayList<>(), scoreList = new ArrayList<>();
        ArrayList<String[]> scoreEntries;
        for(int i = 0; i<DAYS_OF_WEEK; i++){
            xLabels[i] = new Label(i + 1, (calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMANY)).substring(0,2));
            if(i==0){
                xLabels[i] = new Label(i+1, getResources().getString(R.string.today));
            }

            if(contentIntent.equals(getResources().getString(R.string.spinner_overview))) {
                int activityCounter = 0;
                ArrayList<Entry> entries = dataSource.getAllEntries(newDate);
                if (entries.size() != 0) {
                    for (int j = 0; j < entries.size(); j++) {
                        if (entries.get(j).getActivities() != null) {
                            String[] temp = entries.get(j).getActivities().split(",");
                            activityCounter += temp.length;
                        }
                    }
                    if(activityCounter!=0){
                        activityList.add(new Point(i + 1, activityCounter));
                    }
                }
                scoreEntries = dataSource.getMainSymptomScoresViaNameAndDate(getResources().getString(R.string.key_score), newDate);
            }
            else{
                scoreEntries = dataSource.getMainSymptomScoresViaNameAndDate(contentIntent, newDate);
            }
            int scoreSum = 0;
            if(scoreEntries.size()!=0){
                for(int k=0; k<scoreEntries.size();k++){
                    scoreSum += Integer.parseInt(scoreEntries.get(k)[1]);
                }
                scoreList.add(new Point(i+1, calculateAverage(scoreSum, scoreEntries.size())));
            }
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        newDate = dateTimePicker.getTheDayBefore(newDate);
        }


        activityPoints = new Point[activityList.size()];
        for (int k = 0; k < activityList.size(); k++) {
            activityPoints[k] = activityList.get(k);
        }

        scorePoints = new Point[scoreList.size()];
        for (int k = 0; k < scoreList.size(); k++) {
            scorePoints[k] = scoreList.get(k);
        }
        double[] yTicks = new double[]{1, 2, 3, 4, 5};

        if(scorePoints.length==0 && activityPoints.length==0) {
            TextView textView = (TextView) findViewById(R.id.noDataTextView);
            textView.setVisibility(View.VISIBLE);
            Graph graph = new Graph.Builder()
                    .setWorldCoordinates(-0.4, 8, -1, 6)
                    .setXLabels(xLabels)
                    .setYTicks(yTicks)
                    .build();
            GraphView graphView = findViewById(R.id.graph_view);
            graphView.setGraph(graph);
        }

        else if(scorePoints.length!=0 && activityPoints.length!=0) {

            Graph graph = new Graph.Builder()
                    .setWorldCoordinates(-0.4, 8, -1, 6)
                    .addLineGraph(scorePoints, getResources().getColor(R.color.colorPrimary))
                    .addLineGraph(activityPoints, getResources().getColor(R.color.colorActivites))
                    .setXLabels(xLabels)
                    .setYTicks(yTicks)
                    .build();
            GraphView graphView = findViewById(R.id.graph_view);
            graphView.setGraph(graph);
            TextView textViewSymptom = findViewById(R.id.graph_view_label_symptom);
            textViewSymptom.setTextColor(getResources().getColor(R.color.colorPrimary));
            textViewSymptom.setText(contentIntent);
            TextView textViewActivity = findViewById(R.id.graph_view_label_activity);
            textViewActivity.setTextColor(getResources().getColor(R.color.colorActivites));
            textViewActivity.setVisibility(View.VISIBLE);

            if(activityPoints.length==scorePoints.length) {
                boolean same = false;
                for (int t = 0; t < activityPoints.length; t++) {
                    if(activityPoints[t].equals(scorePoints[t])){
                        same = true;
                    }
                    else{
                        same = false;
                    }
                }
                if(same){
                    TextView textViewSameGraph = (TextView)findViewById(R.id.noDataTextView);
                    textViewSameGraph.setVisibility(View.VISIBLE);
                    textViewSameGraph.setText(getResources().getString(R.string.graph_sameGraph));
                }
            }
        }
        else if(scorePoints.length==0){
            Graph graph = new Graph.Builder()
                    .setWorldCoordinates(-0.4, 8, -1, 6)
                    .addLineGraph(activityPoints, getResources().getColor(R.color.colorActivites))
                    .setXLabels(xLabels)
                    .setYTicks(yTicks)
                    .build();
            GraphView graphView = findViewById(R.id.graph_view);
            graphView.setGraph(graph);
            TextView textView = findViewById(R.id.graph_view_label_symptom);
            textView.setVisibility(View.GONE);
            TextView textViewActivity = findViewById(R.id.graph_view_label_activity);
            textViewActivity.setTextColor(getResources().getColor(R.color.colorActivites));
            textViewActivity.setVisibility(View.VISIBLE);
        }

        else if(activityPoints.length==0) {
            Graph graph = new Graph.Builder()
                    .setWorldCoordinates(-0.4, 8, -1, 6)
                    .addLineGraph(scorePoints, getResources().getColor(R.color.colorPrimary))
                    .setXLabels(xLabels)
                    .setYTicks(yTicks)
                    .build();
            GraphView graphView = findViewById(R.id.graph_view);
            graphView.setGraph(graph);
            TextView textView = findViewById(R.id.graph_view_label_symptom);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setText(contentIntent);
        }
    }

    private int calculateAverage(float sum, float divisor){
        return Math.round(sum / divisor);
    }

    private void initializeSpinner(){
        String[] allSpinnerEntries = new String[mainSymptoms.length+1];
        int overviewNumber = 0;
        allSpinnerEntries[0] = contentIntent;
        for(int i = 0; i < mainSymptoms.length; i++) {
            allSpinnerEntries[i + 1] = mainSymptoms[i];
            if (mainSymptoms[i].equals(contentIntent)) {
                overviewNumber = i+1;
            }
        }
        if(!(contentIntent.equals(getResources().getString(R.string.spinner_overview)))){
            allSpinnerEntries[overviewNumber] = getResources().getString(R.string.spinner_overview);
        }

        ArrayAdapter<String> spinnerViewAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerList);
        spinnerView.setAdapter(spinnerViewAdapter);
        spinnerView.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerGraphAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, allSpinnerEntries);
        spinnerGraph.setAdapter(spinnerGraphAdapter);
        spinnerGraph.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(++check > 2) {
            if(parent.getItemAtPosition(position).toString().equals(getResources().getString(R.string.key_weekView))){
                return;
            }
            if(parent.getItemAtPosition(position).toString().equals(getResources().getString(R.string.key_dayView))){
                Intent i = new Intent(getApplicationContext(), GraphActivityDaily.class);
                i.putExtra(getResources().getString(R.string.key_spinner_graph), getIntent().getStringExtra(getResources().getString(R.string.key_spinner_graph)));
                this.finish();
                startActivity(i);
            }
            else {
                Intent i = new Intent(getApplicationContext(), GraphActivityWeekly.class);
                i.putExtra(getResources().getString(R.string.key_spinner_graph), parent.getItemAtPosition(position).toString());
                this.finish();
                startActivity(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }
}
