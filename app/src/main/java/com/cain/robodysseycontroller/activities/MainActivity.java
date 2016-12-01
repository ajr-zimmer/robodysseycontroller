package com.cain.robodysseycontroller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cain.robodysseycontroller.R;
import com.cain.robodysseycontroller.utils.Utils;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String mapSelected;
    EditText inputIP;

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    public static final String LAPTOP_IP = "laptop_ip";
    boolean isUserFirstTime;

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

        inputIP = (EditText) findViewById(R.id.inputIP);

    }

    // Implementing methods from OnItemSelectedListener for the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        mapSelected = parent.getItemAtPosition(pos).toString();

    }

    public void onNothingSelected(AdapterView<?> parent){
        // Another interface callback
    }

    public void launchTutorial(View view){
        Utils.saveSharedSetting(MainActivity.this, LAPTOP_IP, inputIP.getText().toString());

        Toast.makeText(this, "Oooo you chose the " + mapSelected + " map", Toast.LENGTH_LONG).show();

        Intent tutIntent = new Intent(this, TutorialActivity.class);
        Intent controlIntent = new Intent(this, ControlActivity.class);
        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));
        // This skips the tutorial if the user has already done it, this feature can be taken out.
        //startActivity(tutIntent); // debug purposes
        if(isUserFirstTime){
            startActivity(tutIntent);
        } else {
            startActivity(controlIntent);
        }
    }

}
