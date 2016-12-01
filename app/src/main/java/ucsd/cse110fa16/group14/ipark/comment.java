package ucsd.cse110fa16.group14.ipark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
  https://www.youtube.com/watch?v=tOn5HsQPhUY&t=6s
 */
public class comment extends AppCompatActivity {
    private Firebase root;
    Firebase hasChild;
    private static FirebaseAuth auth;

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final Bundle bundle = getIntent().getExtras();

        root = new Firebase("https://ipark-e243b.firebaseio.com/Comments");
        auth = FirebaseAuth.getInstance();

        // get the current user's username, default: maggie
        final String userName = auth.getCurrentUser() != null ? auth.getCurrentUser().getDisplayName():"maggie";

        final EditText userComment = (EditText) findViewById(R.id.userComment);
        final RatingBar rating = (RatingBar) findViewById(R.id.rating);

        Button submitButt = (Button) findViewById(R.id.submitButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        submitButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get the User's comment and rating
                String comment = userComment.getText().toString();
                String rate = String.valueOf(rating.getRating());

                // get the current date
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.format(date);

                hasChild = root.child(date + " ");

                // create necessary children in Firebase for current comment
                Firebase commentChild = hasChild.child("Comment");
                Firebase dateChild = hasChild.child("Date");
                Firebase rateChild = hasChild.child("Rating");
                Firebase keyChild = hasChild.child("Key");
                Firebase userChild = hasChild.child("User");

                // if the comment box left empty, set to message
                if (comment.isEmpty()) {
                    commentChild.setValue("No comment left by the user.");
                } else {
                    commentChild.setValue(comment);
                }

                // setting the values in Firebase for the comment
                dateChild.setValue(sdf.format(date));
                rateChild.setValue(rate);
                keyChild.setValue(date + " ");
                userChild.setValue(userName);

                Toast.makeText(comment.this, "Thank you for your input!",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(comment.this, UserHomepage.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if cancelButton is pressed, send user to User Homepage
                Intent intent = new Intent(comment.this, UserHomepage.class);
                startActivity(intent);
            }
        });

        userComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
