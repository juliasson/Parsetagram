package me.juliasson.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private Button bvLogIn;
    private Button bvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            Log.d("login_activity", "Login successful");
            final Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        bvLogIn = findViewById(R.id.bvLogin);
        bvSignUp = findViewById(R.id.bvSignUp);

        bvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                logIn(username, password);
            }
        });

        bvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });
    }

    private void logIn(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("login_activity", "Login successful");
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    final Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Log.e("login_activity","Login failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
