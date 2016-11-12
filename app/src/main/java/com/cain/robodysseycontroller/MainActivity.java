package com.cain.robodysseycontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Populate dropdown
        Spinner spinner = (Spinner) findViewById(R.id.maps_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.maps_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // specify interface implementation
        spinner.setOnItemSelectedListener(this);
    }

    // Implementing methods from OnItemSelectedListener for the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        Toast.makeText(this, "Oooo you chose the " + parent.getItemAtPosition(pos).toString() + " map", Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> parent){
        // Another interface callback
    }

}
