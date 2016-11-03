package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MessageUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_users);

        Button msgCancelBtn = (Button) findViewById(R.id.msgCancel);
        Button msgSendBtn = (Button) findViewById(R.id.msgSend);

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
                Intent intent = new Intent(MessageUsersActivity.this, NavigationMenu.class);
                startActivity(intent);
            }
        });
    }
}
