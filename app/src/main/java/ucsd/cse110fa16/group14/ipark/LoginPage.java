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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mag on 10/10/2016.
 */

public class LoginPage extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    private Button registerButton;
    private Button loginButton;
    private Button help;
    private TextView forgotPass;
    //private CheckBox rememberMe;
    private ProgressDialog progress;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;


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

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //User is singed in
                if (firebaseAuth.getCurrentUser() != null) {
                    String username = firebaseAuth.getCurrentUser().getEmail();
                    String admin = "www123@gmail.com";

                    /*Intent intent = username.equals("admin") ?
                            new Intent(LoginPage.this, OwnerHomepage.class):
                            new Intent(LoginPage.this, UserHomepage.class) ;*/

                    Intent intent;
                    if (username.equals(admin))
                        intent = new Intent(LoginPage.this, OwnerHomepage.class);
                    else intent = new Intent(LoginPage.this, UserHomepage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        DriverRegistration data = new DriverRegistration();
        data.getData();


        auth = FirebaseAuth.getInstance();

        // the buttons
        //registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        help = (Button) findViewById(R.id.help);
        registerButton = (Button) findViewById(R.id.registerButton);
        forgotPass = (TextView) findViewById(R.id.forget);
        usernameField = (EditText) findViewById(R.id.userName);
        passwordField = (EditText) findViewById(R.id.password);

        //forgotPassword = (CheckBox) findViewById(R.id.checkBox);

        progress = new ProgressDialog(this);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //User is singed in
                if (firebaseAuth.getCurrentUser() != null) {

                    String username = firebaseAuth.getCurrentUser().getEmail();
                    String admin = "www123@gmail.com";
                    /*Intent intent = username.equals("admin") ?
                            new Intent(LoginPage.this, OwnerHomepage.class):
                            new Intent(LoginPage.this, UserHomepage.class) ;*/

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

                signIn();

                /* Will take care of this later
                if (forgotPassword.isChecked()) {
                    Intent intent = new Intent(LoginPage.this, ForgotPassword_1.class);
                    startActivity(intent);
                }
                */

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
                        //   "\t\t\t\tClick 'CHECKOUT' to sign out and end your reservation.\n"+
                        // "\t\t\t\tClick 'REPORT' if there is a car in your spot, " +
                        //"and you will receive a new parking space.\n"
                        //+ "\t\t\t\tClick 'MAP' to view the map of parking lot.\n" +
                        //  "\t\t\t\tClick 'EMERGENCY' in case of any emergency."
                );
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                //hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });


        /*usernameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });*/


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
                Intent intent = new Intent(LoginPage.this, ForgotPassword_1.class);
                startActivity(intent);
            }
        });

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
     */
    private void signIn() {
        String username = usernameField.getText().toString();
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
                            progress.dismiss();
                            Toast.makeText(LoginPage.this, "Sign in successful",
                                    Toast.LENGTH_LONG).show();
                            Intent intent;
                            if (email.equals("www123@gmail.com")) {
                                intent = new Intent(LoginPage.this, OwnerHomepage.class);
                            } else {
                                intent = new Intent(LoginPage.this, UserHomepage.class);
                            }
                            startActivity(intent);
                        }
                    }
                });
            }
        }
    }

}

