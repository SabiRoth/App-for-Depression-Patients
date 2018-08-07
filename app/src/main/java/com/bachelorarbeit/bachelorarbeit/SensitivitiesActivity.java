package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckedTextView;


import java.util.ArrayList;

//TODO: ALLE STRINGS AUSLAGERN

public class SensitivitiesActivity extends AppCompatActivity {

    public ArrayList<String> allSelectedEntries = new ArrayList<String>();
    public ArrayList<String[]> arrayListStringArrays = new ArrayList<>();
    int counter = 0;
    public ListView listViewSensitivities;
    public TextView header;
    public String[] headerString = {"Allgemein", "Kopfbereich", "Hals- und Brustbereich", "Magen-Darm", "Blase und Sexualität", "Geistige Symptome"};;
    public Button buttonSensitivitiesNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitivities);
        header = (TextView)findViewById(R.id.textView_sensitivities_header);
        listViewSensitivities = (ListView)findViewById(R.id.listViewCheckboxes);
        buttonSensitivitiesNext = (Button)findViewById(R.id.button_sensitivies_next);


        String[] general = {"Abgeschlagen sein", "Hitzewallungen", "Zittern", "Überempfindlichkeit", "Gefühl von innerer Leere", "Kraftlosigkeit", "Verspannungen im Nacken", "Gliederschmerzen"};
        String[] head = {"Kopf wie Blei", "Kopfschmerzen", "Sehstörungen", "Druck auf den Ohren", "Hörstörungen", "Zahnschmerzen", "Zungenbrennen", "Mundgeruch"};
        String[] chestNeck = {"Druckgefühl", "Beengung im Brustkorb", "Schmerzen in der Herzgegend", "Herzrasen", "unregelmäßiges Atmen", "Kloßgefühl im Hals", "Würgegefühl"};
        String[] gastrointestinal = {"Appetitlosigkeit", "Unruhe im Bauchraum", "Völlegefühl/Blähungen", "Sodbrennen/Aufstoßen", "Übelkeit/Erbrechen", "Durchfall/Verstopfung", "Gewichtsverlust", "Heißhunger"};
        String[] bladderSexuality = {"Druck in der Blase", "Häufiger Harndrang", "Schmerzen beim Wasserlassen", "kein sexuelles Verlangen", "Potenzstörungen", "Schmerzen beim Geschlechtsverkehr", "Störungen der Periode"};
        String[] mental = {"Leeregefühl im Kopf", "ständige Müdigkeit", "Konzentrationsstörungen", "Gedächtnisstörungen", "Gedankenblockade", "Schlafstörungen"};

        arrayListStringArrays.add(general);
        arrayListStringArrays.add(head);
        arrayListStringArrays.add(chestNeck);
        arrayListStringArrays.add(gastrointestinal);
        arrayListStringArrays.add(bladderSexuality);
        arrayListStringArrays.add(mental);

        buildActualPage();

        buttonSensitivitiesNext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 nextButtonClicked();
              }
        });
    }


    private void buildActualPage(){
     header.setText(headerString[counter]);
     final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
             R.layout.listentry_sensitivities, arrayListStringArrays.get(counter));

     listViewSensitivities.setAdapter(adapter);
     listViewSensitivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             entryClicked(adapter.getItem(i), view);
         }
     });
     if(counter == 5){
         buttonSensitivitiesNext.setText("Abschließen");
     }
  }

    private void entryClicked(String clickedEntry, View viewListEntry){
        if (allSelectedEntries.contains(clickedEntry)) {
            allSelectedEntries.remove(clickedEntry);
        }
        else{
            allSelectedEntries.add(clickedEntry);
        }
        CheckedTextView checkedTextView = (CheckedTextView) viewListEntry;
        checkedTextView.toggle();
        checkedTextView.refreshDrawableState();
    }

    private void nextButtonClicked(){
       counter++;
       if(counter<6) {
           buildActualPage();
       }
       else{
           allSelectedEntries.size();
           Intent i = new Intent (this, HomeActivity.class);
           startActivity(i);
       }
    }
}