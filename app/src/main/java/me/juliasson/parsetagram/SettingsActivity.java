package me.juliasson.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    private Button bvLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bvLogOut = findViewById(R.id.bvLogOut);
        bvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Log.d("settings_activity", "Logged out successfully");
                Toast.makeText(SettingsActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
