package me.juliasson.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etEmail;
    private Button bvSignUp;

    private final String KEY_FIRST_NAME = "firstName";
    private final String KEY_LAST_NAME = "lastName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        bvSignUp = findViewById(R.id.bvSignUp);

        bvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstName = etFirstName.getText().toString();
                final String lastName = etLastName.getText().toString();
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                final String email = etEmail.getText().toString();

                onSignUp(firstName, lastName, username, password, email);
            }
        });
    }

    private void onSignUp(String firstName, String lastName, String username, String password, String email) {
        ParseUser parseUser = new ParseUser();

        parseUser.setUsername(username);
        parseUser.setPassword(password);
        parseUser.setEmail(email);

        parseUser.put(KEY_FIRST_NAME, firstName);
        parseUser.put(KEY_LAST_NAME, lastName);

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("createaccount_activity", "Sign Up successful");
                    Toast.makeText(CreateAccountActivity.this, "Sign up successful", Toast.LENGTH_LONG).show();
                    final Intent i = new Intent(CreateAccountActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
