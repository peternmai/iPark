package ucsd.cse110fa16.group14.ipark;

// sources: https://www.youtube.com/watch?v=tOn5HsQPhUY&t=6s

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageUsersActivity extends AppCompatActivity {
    private Firebase root;
    Firebase hasChild;
    private static FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_users);

        final Bundle bundle = getIntent().getExtras();
        root = new Firebase("https://ipark-e243b.firebaseio.com/Messages");
        auth = FirebaseAuth.getInstance();
        final String userName = auth.getCurrentUser().getDisplayName();  // get current user
        Button msgCancelBtn = (Button) findViewById(R.id.paymentCancelButton);
        Button msgSendBtn = (Button) findViewById(R.id.send);
        final EditText editText = (EditText) findViewById(R.id.editText27);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        msgCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageUsersActivity.this, OwnerHomepage.class);
                startActivity(intent);
            }
        });

        msgSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editText.getText().toString(); // get the user's comment

                // get the current date
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.format(date);

                // create child fields for a message
                hasChild = root.child(date + " ");
                Firebase commentChild = hasChild.child("Comment");
                Firebase dateChild = hasChild.child("Date");
                Firebase keyChild = hasChild.child("Key");
                Firebase userChild = hasChild.child("User");

                // populate message in Firebase
                commentChild.setValue(comment);
                dateChild.setValue(sdf.format(date));
                keyChild.setValue(date + " ");
                userChild.setValue(userName);

                // Notify users of new messages
                iLink.alertUserNewMessages();

                // send user to the Owner Homepage
                Intent intent = new Intent(MessageUsersActivity.this, OwnerHomepage.class);
                startActivity(intent);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
