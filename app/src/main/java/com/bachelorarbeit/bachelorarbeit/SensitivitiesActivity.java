package com.bachelorarbeit.bachelorarbeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.CheckedTextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;

//TODO: ALLE STRINGS AUSLAGERN

public class SensitivitiesActivity extends AppCompatActivity {

    public ArrayList<String> allSelectedEntries = new ArrayList<String>();
    public ArrayList<String[]> arrayListStringArrays = new ArrayList<>();
    int counter = 0;
    public ListView listViewSensitivities;
    public TextView header;
    public String[] headerString = {"Allgemein", "Kopfbereich", "Hals- und Brustbereich", "Magen-Darm", "Blase und Sexualität", "Psychische Symptome", "Soziale Symptome", "Weitere Eingaben"};;
    public Button buttonSensitivitiesNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitivities);
        header = (TextView)findViewById(R.id.textView_sensitivities_header);
        listViewSensitivities = (ListView)findViewById(R.id.listViewCheckboxes);
        buttonSensitivitiesNext = (Button)findViewById(R.id.button_sensitivies_next);


        String[] general = {"Abgeschlagen sein", "Hitzewallungen", "Zittern", "Überempfindlichkeit", "Gefühl von innerer Leere", "Kraftlosigkeit", "Verspannungen im Nacken", "Gliederschmerzen", "Verminderter Antrieb", "Verminderte Aktivität", "Bewegungsdrang", "Unruhe", "Geminderte Leistungsfähigkeit", "Kraftlosigkeit", "Schlafstörungen", "Ständige Müdigkeit", "Morgentief", "Appetitsminderung", "Appetitsssteigerung"};
        String[] head = {"Kopf wie Blei", "Kopfschmerzen", "Sehstörungen", "Druck auf den Ohren", "Hörstörungen", "Zahnschmerzen", "Zungenbrennen", "Mundgeruch"};
        String[] chestNeck = {"Druckgefühl", "Beengung im Brustkorb", "Schmerzen in der Herzgegend", "Herzrasen", "Unregelmäßiges Atmen", "Kloßgefühl im Hals", "Würgegefühl"};
        String[] gastrointestinal = {"Appetitlosigkeit", "Unruhe im Bauchraum", "Völlegefühl/Blähungen", "Sodbrennen/Aufstoßen", "Übelkeit", "Erbrechen", "Durchfall", "Verstopfung", "Gewichtsverlust", "Heißhunger"};
        String[] bladderSexuality = {"Druck in der Blase", "Häufiger Harndrang", "Schmerzen beim Wasserlassen", "Libidoverlust", "Potenzstörungen", "Schmerzen beim Geschlechtsverkehr", "Störungen der Periode"};
        String[] mental = {"Leeregefühl im Kopf", "Konzentrationsstörungen", "Gedächtnisstörungen", "Gedankenblockade", "Gefühle der Werlosigkeit", "Beeinträchtigtes Selbstvertrauen", "Schuldgefühle", "Gedrückte Stimmung", "Versagensgefühle", "Selbstvorwürfe", "Pessimismus", "Unzufriedenheit", "Stress", "Verzweiflung", "Gefühle der Nutzlosigkeit", "Geminderte Begeisterungsfähigkeit", "Unentschlossenheit", "Weinen", "Interessenverlust"};
        String[] social ={"Sozialer Rückzug", "Verminderte Gesprächigkeit", "Reizbarkeit"};
        String[] ownEntries = {}; //TODO: aus Datenbank holen

        arrayListStringArrays.add(general);
        arrayListStringArrays.add(head);
        arrayListStringArrays.add(chestNeck);
        arrayListStringArrays.add(gastrointestinal);
        arrayListStringArrays.add(bladderSexuality);
        arrayListStringArrays.add(mental);
        arrayListStringArrays.add(social);
        arrayListStringArrays.add(ownEntries);

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
         if(counter == arrayListStringArrays.size()-1){
             buttonSensitivitiesNext.setText(R.string.next);
             final EditText editText = (EditText)findViewById(R.id.editText_sensitivities);
             Button saveButtonOwnEntries = (Button)findViewById(R.id.saveButton_sensitivities);
             editText.setVisibility(View.VISIBLE);
             saveButtonOwnEntries.setVisibility(View.VISIBLE);
             saveButtonOwnEntries.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     saveButtonOwnEntriesClicked(editText);
                 }
             });
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

    private void saveButtonOwnEntriesClicked(EditText editText){
        if(!(editText.getText().toString().equals(""))) {
            allSelectedEntries.add(editText.getText().toString());
            CharSequence text = "Gespeichert";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            editText.setText("");
        }
    }

    private void nextButtonClicked(){
       counter++;
       if(counter<arrayListStringArrays.size()) {
           buildActualPage();
       }
       else{
           String[] temp = new String[allSelectedEntries.size()];
           String sensitivitiesString = Arrays.toString(allSelectedEntries.toArray(temp));
           Intent i = new Intent (this, ActivitiesActivity.class);
           i.putExtra("sensitivitiesString", sensitivitiesString);
           startActivity(i);
       }
    }
}