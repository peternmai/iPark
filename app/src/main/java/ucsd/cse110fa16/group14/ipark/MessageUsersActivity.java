package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        final String userName = auth.getCurrentUser().getDisplayName();

        Button msgCancelBtn = (Button) findViewById(R.id.send);
        Button msgSendBtn = (Button) findViewById(R.id.paymentCancelButton);
        final EditText editText = (EditText) findViewById(R.id.editText27);

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

                String comment = editText.getText().toString();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.format(date);

                hasChild = root.child(date + " ");

                Firebase commentChild = hasChild.child("Comment");
                Firebase dateChild = hasChild.child("Date");
                Firebase keyChild = hasChild.child("Key");
                Firebase userChild = hasChild.child("User");

                commentChild.setValue(comment);
                dateChild.setValue(sdf.format(date));
                keyChild.setValue(date + " ");
                userChild.setValue(userName);

                Intent intent = new Intent(MessageUsersActivity.this, OwnerHomepage.class);
                startActivity(intent);
            }
        });
    }
}
