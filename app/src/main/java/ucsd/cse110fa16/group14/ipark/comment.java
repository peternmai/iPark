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

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class comment extends AppCompatActivity {

    private Firebase root;

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


        root = new Firebase("https://ipark-e243b.firebaseio.com/Comments");


        final EditText userComment = (EditText) findViewById(R.id.userComment);
        final RatingBar rating = (RatingBar) findViewById(R.id.rating);

        Button submitButt = (Button) findViewById(R.id.submitButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        submitButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String comment = userComment.getText().toString();
                String rate = String.valueOf(rating.getRating());

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.format(date);

                Firebase hasChild = root.child(date + " ");

                Firebase commentChild = hasChild.child("Comment");
                Firebase dateChild = hasChild.child("Date");
                Firebase rateChild = hasChild.child("Rating");

                commentChild.setValue(comment);
                dateChild.setValue(sdf.format(date));
                rateChild.setValue(rate);


                Intent intent = new Intent(comment.this, UserHomepage.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
