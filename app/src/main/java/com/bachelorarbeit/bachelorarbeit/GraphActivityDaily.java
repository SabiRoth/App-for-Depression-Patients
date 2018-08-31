package com.bachelorarbeit.bachelorarbeit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.softmoore.android.graphlib.*;

import java.util.ArrayList;
import java.util.Calendar;

public class GraphActivityDaily extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    Button datePickerButton;
    String[] spinnerList;
    Spinner spinnerView, spinnerGraph;
    String pickedDate;
    DateTimePicker dateTimePicker;
    ArrayList<String> symptomListInDb;
    int check = 0;
    dataSource dataSource;
    String[] mainSymptoms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_daily);
        datePickerButton = (Button) findViewById(R.id.button_date_picker_calendar);
        spinnerList = getResources().getStringArray(R.array.spinner_view_daily);
        spinnerView = (Spinner) findViewById(R.id.spinner);
        spinnerGraph = (Spinner) findViewById(R.id.spinner_graph);
        dateTimePicker = DateTimePicker.getInstance();
        symptomListInDb = new ArrayList<>();
        dataSource = new dataSource(this);
        dataSource.open();
        mainSymptoms = (getResources().getString(R.string.key_score) + "," + dataSource.getSettingViaName(getResources().getString(R.string.key_mainSymptoms))).split(",");
        pickedDate = getIntent().getStringExtra(getResources().getString(R.string.key_date));
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        datePickerButton.setText(pickedDate);
        initializeClickListenerForDatePickerButton();
        createGraph();
        initializeSpinner();
    }


    private void createGraph(){
        String contentIntent = getIntent().getStringExtra(getResources().getString(R.string.key_spinner_graph));
        Label[] xLabels = {new Label(1, getResources().getString(R.string.morning)), new Label(2, getResources().getString(R.string.midday)), new Label(3, getResources().getString(R.string.evening))};

        if(contentIntent.equals("Übersicht")){
            Point[] activityPoints = new Point[0];
            Point[] scorePoints;
            ArrayList<Entry> entries  = dataSource.getAllEntries(pickedDate);
            Integer activitiesEntriesMorning = 0, activitiesEntriesMidday = 0, activitiesEntriesEvening =0;
            if(entries.size()!=0) {
                for (int i = 0; i < entries.size(); i++) {
                    if (entries.get(i).getActivities() != null) {
                        if (entries.get(i).getDaytime().equals(getResources().getString(R.string.morning))) {
                            activitiesEntriesMorning++;
                        }
                        if (entries.get(i).getDaytime().equals(getResources().getString(R.string.midday))) {
                            activitiesEntriesMidday++;
                        }
                        if (entries.get(i).getDaytime().equals(getResources().getString(R.string.evening))) {
                            activitiesEntriesEvening++;
                        }
                    }
                }
                ArrayList<Point> temp = new ArrayList<>();
                if (activitiesEntriesMorning > 0) {
                    temp.add(new Point(1, activitiesEntriesMorning));
                }
                if (activitiesEntriesMidday > 0) {
                    temp.add(new Point(2, activitiesEntriesMidday));
                }
                if (activitiesEntriesEvening > 0) {
                    temp.add(new Point(3, activitiesEntriesEvening));
                }
                activityPoints = new Point[temp.size()];
                for (int k = 0; k < temp.size(); k++) {
                    activityPoints[k] = temp.get(k);
                }
            }

            ArrayList<String[]> scoreEntries = dataSource.getMainSymptomScoresViaNameAndDate(getResources().getString(R.string.key_score), pickedDate);
            scorePoints = getPoints(scoreEntries);

            if(scorePoints.length==0 && activityPoints.length==0){
                TextView textView = (TextView)findViewById(R.id.noDataTextView);
                textView.setVisibility(View.VISIBLE);
                Graph graph = new Graph.Builder()
                        .setWorldCoordinates(-0.4, 4, -1, 6)
                        .setXLabels(xLabels)
                        .setYTicks(new double[] {0, 1, 2, 3, 4, 5})
                        .build();
                GraphView graphView = findViewById(R.id.graph_view);
                graphView.setGraph(graph);
            }

            if(scorePoints.length==0){
                Graph graph = new Graph.Builder()
                        .setWorldCoordinates(-0.4, 4, -1, 6)
                        .addLineGraph(activityPoints, getResources().getColor(R.color.colorActivites))
                        .setXLabels(xLabels)
                        .setYTicks(new double[]{0, 1, 2, 3, 4, 5})
                        .build();
                GraphView graphView = findViewById(R.id.graph_view);
                graphView.setGraph(graph);
                TextView textView = findViewById(R.id.graph_view_label_symptom);
                textView.setVisibility(View.GONE);
                TextView textViewActivity = findViewById(R.id.graph_view_label_activity);
                textViewActivity.setTextColor(getResources().getColor(R.color.colorActivites));
                textViewActivity.setVisibility(View.VISIBLE);
            }

            if(activityPoints.length==0) {
                Graph graph = new Graph.Builder()
                        .setWorldCoordinates(-0.4, 4, -1, 6)
                        .addLineGraph(scorePoints, getResources().getColor(R.color.colorPrimary))
                        .setXLabels(xLabels)
                        .setYTicks(new double[]{0, 1, 2, 3, 4, 5})
                        .build();
                GraphView graphView = findViewById(R.id.graph_view);
                graphView.setGraph(graph);
                TextView textView = findViewById(R.id.graph_view_label_symptom);
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setText(contentIntent);
            }

            else{
                Graph graph = new Graph.Builder()
                        .setWorldCoordinates(-0.4, 4, -1, 6)
                        .addLineGraph(scorePoints, getResources().getColor(R.color.colorPrimary))
                        .addLineGraph(activityPoints, getResources().getColor(R.color.colorActivites))
                        .setXLabels(xLabels)
                        .setYTicks(new double[]{0, 1, 2, 3, 4, 5})
                        .build();
                GraphView graphView = findViewById(R.id.graph_view);
                graphView.setGraph(graph);
                TextView textView = findViewById(R.id.graph_view_label_symptom);
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setText(contentIntent);
                TextView textViewActivity = findViewById(R.id.graph_view_label_activity);
                textViewActivity.setTextColor(getResources().getColor(R.color.colorActivites));
                textViewActivity.setVisibility(View.VISIBLE);
            }
        }
        else{
            Point[] pointString;
            ArrayList<String[]> currentMainSymptomData = dataSource.getMainSymptomScoresViaNameAndDate(contentIntent, pickedDate);
            if (currentMainSymptomData.size() != 0) {
                pointString  = getPoints(currentMainSymptomData);

                if (pointString.length > 0) {
                    Graph graph = new Graph.Builder()
                            .setWorldCoordinates(-0.4, 4, -1, 6)
                            .addLineGraph(pointString, getResources().getColor(R.color.colorPrimary))
                            .setXLabels(xLabels)
                            .setYTicks(new double[]{0, 1, 2, 3, 4, 5})
                            .build();
                    GraphView graphView = findViewById(R.id.graph_view);
                    graphView.setGraph(graph);
                    TextView textView = findViewById(R.id.graph_view_label_symptom);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    textView.setText(contentIntent);
                }
            }

            else{
                TextView textView = (TextView)findViewById(R.id.noDataTextView);
                textView.setVisibility(View.VISIBLE);
                Graph graph = new Graph.Builder()
                        .setWorldCoordinates(-0.4, 4, -1, 6)
                        .setXLabels(xLabels)
                        .setYTicks(new double[] {0, 1, 2, 3, 4, 5})
                        .build();
                GraphView graphView = findViewById(R.id.graph_view);
                graphView.setGraph(graph);

            }
        }
    }


    private Point[] getPoints(ArrayList<String[]> currentMainSymptomData){
        Point[] pointString;
        if(currentMainSymptomData.size()>0) {
            float counterMorning = 0, counterMidday = 0, counterEvening = 0, sumMorning = 0, sumMidday = 0, sumEvening = 0;
            for (int j = 0; j < currentMainSymptomData.size(); j++) {
                String currentDayTime = dateTimePicker.getDaytime(currentMainSymptomData.get(j)[2]);
                if (currentDayTime.equals(getResources().getString(R.string.morning))) {
                    counterMorning++;
                    sumMorning += Integer.parseInt(currentMainSymptomData.get(j)[1]);
                }
                if (currentDayTime.equals(getResources().getString(R.string.midday))) {
                    counterMidday++;
                    sumMidday += Integer.parseInt(currentMainSymptomData.get(j)[1]);
                }
                if (currentDayTime.equals(getResources().getString(R.string.evening))) {
                    counterEvening++;
                    sumEvening += Integer.parseInt(currentMainSymptomData.get(j)[1]);
                }
            }


            ArrayList<Point> temp = new ArrayList<>();
            if (counterMorning > 0) {
                temp.add(new Point(1, calculateAverage(sumMorning, counterMorning)));
            }
            if (counterMidday > 0) {
                temp.add(new Point(2, calculateAverage(sumMidday, counterMidday)));
            }
            if (counterEvening > 0) {
                temp.add(new Point(3, calculateAverage(sumEvening, counterEvening)));
            }
            pointString = new Point[temp.size()];
            for (int k = 0; k < temp.size(); k++) {
                pointString[k] = temp.get(k);
            }

        return pointString;
        }
        pointString = new Point[0];
        return pointString;
    }



    private int calculateAverage(float sum, float divisor){
        return Math.round(sum / divisor);
    }



    private void initializeClickListenerForDatePickerButton(){
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(GraphActivityDaily.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                        String newDate = dateTimePicker.setDateFormat(dayOfMonth, (monthOfYear + 1),  year);
                        Intent reloadIntent = new Intent(GraphActivityDaily.this, GraphActivityDaily.class);
                        reloadIntent.putExtra(getResources().getString(R.string.key_date), newDate);
                        GraphActivityDaily.this.finish();
                        startActivity(reloadIntent);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    private void initializeSpinner(){
        String[] allSpinnerEntries = new String[mainSymptoms.length+1];
        allSpinnerEntries[0] = getResources().getString(R.string.spinner_overview);
        for (int i = 0; i < mainSymptoms.length; i++) {
            allSpinnerEntries[i+1] = mainSymptoms[i];
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerList);
        spinnerView.setAdapter(spinnerAdapter);
        ArrayAdapter<String> spinnerGraphAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, allSpinnerEntries);
        spinnerGraph.setAdapter(spinnerGraphAdapter);
        spinnerGraph.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(++check > 1) {
            Intent i = new Intent(getApplicationContext(), GraphActivityDaily.class);
            i.putExtra(getResources().getString(R.string.key_spinner_graph), parent.getItemAtPosition(position).toString());
            this.finish();
            startActivity(i);
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
