package com.bachelorarbeit.bachelorarbeit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;


public class GraphActivityDaily extends AppCompatActivity {

    Spinner spinnerView, spinnerGraph;
    Button datePickerButton;
    DateTimePicker dateTimePicker;
    String pickedDate;
    GraphView graph;
    String[] spinnerList = getResources().getStringArray(R.array.spinner_view);
    String[] spinnerListGraph = getResources().getStringArray(R.array.spinner_graph);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = (GraphView) findViewById(R.id.graph);
        datePickerButton = (Button) findViewById(R.id.button_date_picker_calendar);
        spinnerView = (Spinner) findViewById(R.id.spinner);
        spinnerGraph = (Spinner) findViewById(R.id.spinner_graph);
        dateTimePicker = DateTimePicker.getInstance();
        pickedDate = getIntent().getStringExtra("date");
        if(pickedDate == null){
            pickedDate = dateTimePicker.getCurrentDate();
        }
        datePickerButton.setText(pickedDate);
        initializeSpinner();
        initializeClickListenerForDatePickerButton();
        initializeGraphView();
    }


    //TODO: Uhrzeit in creteMovementEntry mit aufnehmen für Tagesansicht ?? -> DB VERSION ÄNDERN

    private void initializeGraphView(){
        dataSource dataSource = new dataSource(this);
        dataSource.open();
        ArrayList<Entry> entries  = dataSource.getAllEntries(pickedDate);
        //ArrayList<String[]> movementEntries = dataSource.getAllMovementEntriesViaDate(pickedDate);
        ArrayList<String> activitiesEntriesMorning = new ArrayList<>(), activitiesEntriesMidday = new ArrayList<>(), activitiesEntriesEvening = new ArrayList<>();

        Integer sensivitiesCounterMorning = 0, sensivitiesCounterMidday = 0, sensivitiesCounterEvening = 0;
        dataSource.close();

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{getResources().getString(R.string.morning), getResources().getString(R.string.midday), getResources().getString(R.string.evening)});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

        if(entries.size()!=0){
           for(int i = 0; i<entries.size(); i++){
               if(entries.get(i).getSensitivies()!=null){
                   String[] allSensitivities = entries.get(i).getSensitivies().split(",");
                   if(entries.get(i).getDaytime().equals(getResources().getString(R.string.morning))){
                       sensivitiesCounterMorning += allSensitivities.length;
                   }
                   if(entries.get(i).getDaytime().equals(getResources().getString(R.string.midday))){
                       sensivitiesCounterMidday += allSensitivities.length;
                   }
                   if(entries.get(i).getDaytime().equals(getResources().getString(R.string.evening))){
                       sensivitiesCounterEvening += allSensitivities.length;
                   }
              }
               if(entries.get(i).getActivities()!=null){
                   if(entries.get(i).getDaytime().equals(getResources().getString(R.string.morning))){
                       activitiesEntriesMorning.add(entries.get(i).getActivities());
                   }
                   if(entries.get(i).getDaytime().equals(getResources().getString(R.string.midday))){
                       activitiesEntriesMidday.add(entries.get(i).getActivities());
                   }
                   if(entries.get(i).getDaytime().equals(getResources().getString(R.string.evening))){
                       activitiesEntriesEvening.add(entries.get(i).getActivities());
                   }
               }
           }
        }

        else{
            TextView noDataTextView = (TextView)findViewById(R.id.noDataTextView);
            noDataTextView.setVisibility(View.VISIBLE);
            return;
        }


      //  DataPoint[] dataPoints = new DataPoint[3];



        LineGraphSeries<DataPoint> seriesSensivities = new LineGraphSeries<>(new DataPoint[]{
               new DataPoint(0, sensivitiesCounterMorning),
               new DataPoint(1, sensivitiesCounterMidday),
               new DataPoint(2, sensivitiesCounterEvening)
        });
        seriesSensivities.setTitle(getResources().getString(R.string.title_sensitivity));
        seriesSensivities.setColor(getResources().getColor(R.color.colorText));
        graph.addSeries(seriesSensivities);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graph.getLegendRenderer().setBackgroundColor(getResources().getColor(R.color.backgroundColorTheme));
        graph.getLegendRenderer().setTextColor(getResources().getColor(R.color.colorText));


        LineGraphSeries<DataPoint> seriesActivities = new LineGraphSeries<>(new DataPoint[] {
               new DataPoint(0, activitiesEntriesMorning.size()),
               new DataPoint(1, activitiesEntriesMidday.size()),
               new DataPoint(2, activitiesEntriesEvening.size())
        });
        seriesActivities.setTitle(getResources().getString(R.string.title_movement));
        seriesActivities.setColor(getResources().getColor(R.color.colorPrimary));
        graph.addSeries(seriesActivities);
    }




    //TODO: AUSLAGERN?
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
                        String newDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        Intent reloadIntent = new Intent(GraphActivityDaily.this, GraphActivityDaily.class);
                        reloadIntent.putExtra("date", newDate);
                        GraphActivityDaily.this.finish();
                        startActivity(reloadIntent);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    private void initializeSpinner(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerList);
        //spinnerAdapter.setDropDownViewResource(R.layout.listentry_simple_listview);
        spinnerView.setAdapter(spinnerAdapter);

        ArrayAdapter<String> spinnerGraphAdapter = new ArrayAdapter<String>(this, R.layout.listentry_spinner, spinnerListGraph);
        spinnerGraph.setAdapter(spinnerGraphAdapter);
    }

}