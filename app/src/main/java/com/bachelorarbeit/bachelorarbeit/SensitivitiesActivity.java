package com.bachelorarbeit.bachelorarbeit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SensitivitiesActivity extends AppCompatActivity {

    public String[] allSelectedEntries;

    //TODO: Auslagern?
    public String[] general = {"Abgeschlagen sein", "Hitzewallungen", "Zittern", "Überempfindlichkeit", "Gefühl von innerer Leere", "Kraftlosigkeit", "Verspannungen im Nacken", "Gliederschmerzen"};
    public String[] head = {"Kopf wie Blei", "Kopfschmerzen", "Sehstörungen", "Druck auf den Ohren", "Hörstörungen", "Zahnschmerzen", "Zungenbrennen", "Mundgeruch"};
    public String[] chestNeck = {"Druckgefühl", "Beengung im Brustkorb", "Schmerzen in der Herzgegend", "Herzrasen", "unregelmäßiges Atmen", "Kloßgefühl im Hals", "Würgegefühl"};
    public String[] gastrointestinal = {"Appetitlosigkeit", "Unruhe im Bauchraum", "Völlegefühl", "Blähungen", "Sodbrennen", "Aufstoßen", "Übelkeit", "Erbrechen", "Durchfall/Verstopfung", "Gewichtsverlust", "Heißhunger"};
    public String[] bladderSexuality = {"Druch in der Blase", "Häufiger Harndrang", "Schmerzen beim Wasserlassen", "kein sexuelles Verlangen", "Potenzstörungen", "Schmerzen beim Geschlechtsverkehr", "Störungen der Periode"};
    public String[] mental = {"Leeregefühl im Kopf", "ständige Müdigkeit", "Konzentrationsstörungen", "Gedächtnisstörungen", "Gedankenblockade", "Schlafstörungen"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitivities);
        TextView header = (TextView)findViewById(R.id.textView_sensitivities_header);
        header.setText("Allgemein");
        ListView listViewGeneral = (ListView)findViewById(R.id.listViewCheckboxesGeneral);
        ListView listViewHead = (ListView)findViewById(R.id.listViewCheckboxesHead);
        ListView listViewChestNeck = (ListView)findViewById(R.id.listViewCheckboxesChestNeck);
        //usw
        Button buttonSensitivitiesNext = (Button)findViewById(R.id.button_sensitivies_next);

        listViewHead.setVisibility(View.GONE);
        listViewChestNeck.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               R.layout.simple_list_item, general);

        listViewGeneral.setAdapter(adapter);
        //clicklistener
    }


    private void entryClicked(){

        //setChecked(boolean checked)
        //add to allSelectedEntries
    }

}