package ucsd.cse110fa16.group14.ipark;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mag on 10/10/2016.=
 */
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class LoginPage extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    private Button registerButton;
    private Button loginButton;
    private Button help;
    private TextView forgotPass;
    private ProgressDialog progress;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private CheckBox rememberMe;
    private SharedPreferences loginPref;
    private SharedPreferences.Editor loginPrefEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        DriverRegistration data = new DriverRegistration();
        data.getData();


        // the buttons
        //registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        help = (Button) findViewById(R.id.help);
        registerButton = (Button) findViewById(R.id.registerButton);
        forgotPass = (TextView) findViewById(R.id.forget);
        usernameField = (EditText) findViewById(R.id.userName);
        passwordField = (EditText) findViewById(R.id.password);
        rememberMe = (CheckBox) findViewById(R.id.checkBox2);


        loginPref = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();

        saveLogin = loginPref.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            usernameField.setText(loginPref.getString("username", ""));
            rememberMe.setChecked(true);
        }

        auth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);

        //Source: https://firebase.google.com/docs/auth/android/password-auth
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //User is singed in
                if (firebaseAuth.getCurrentUser() != null) {

                    String username = firebaseAuth.getCurrentUser().getEmail();
                    String admin = "admin@ipark.com";

                    Intent intent;
                    if (username.equals(admin))
                        intent = new Intent(LoginPage.this, OwnerHomepage.class);
                    else intent = new Intent(LoginPage.this, UserHomepage.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, DriverRegistration.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        //Sign in when log in button is pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rememberMe.isChecked()) {
                    loginPrefEditor.putBoolean("saveLogin", true);
                    loginPrefEditor.putString("username", usernameField.getText().toString());
                    loginPrefEditor.commit();
                } else {
                    loginPrefEditor.clear();
                    loginPrefEditor.commit();
                }
                signIn();
            }
        });

        /* information page */
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(LoginPage.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("\t\t\t\tIn case you forgot your password, please " +
                        "click \"Forgot password\" to reset it.\n"
                        + "\n Please create a new iPark account if you are a new user."
                );
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });

        passwordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        /* forget password */
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
        auth.signOut();
    }


    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Takes the user name from the text field as an input
     * and looks for the corresponding email in the database.
     * Then it logs in using the email found and the password
     * entered in the text field.
     *
     * Source: https://firebase.google.com/docs/auth/android/password-auth
     */
    private void signIn() {
        final String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginPage.this, "Please enter your username and password",
                    Toast.LENGTH_LONG).show();
        } else {
            if (!(DriverRegistration.uMapEmail.containsKey(username))) {
                Toast.makeText(LoginPage.this, "Invalid Username", Toast.LENGTH_LONG).show();
                usernameField.setText("");
                passwordField.setText("");
            } else {
                final String email = DriverRegistration.uMapEmail.get(username);
                progress.show();
                progress.setMessage("Signing in....");
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progress.dismiss();
                            Toast.makeText(LoginPage.this, "Invalid password.\nPlease try again.",
                                    Toast.LENGTH_LONG).show();
                            passwordField.setText("");
                        } else {
                            Toast.makeText(LoginPage.this, "Sign in successful",
                                    Toast.LENGTH_LONG).show();
                            Intent intent;
                            if (email.equals("admin@ipark.com")) {
                                intent = new Intent(LoginPage.this, OwnerHomepage.class);
                            } else {
                                intent = new Intent(LoginPage.this, UserHomepage.class);
                            }
                            progress.dismiss();
                            intent.putExtra("Username", username);
                            startActivity(intent);
                        }
                    }
                });
            }
        }
    }

}


