package rhynix.eliud.chatappsdemo;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ChatArrayAdapter apd;
    private ListView list;
    private EditText chatText;
    private Button send;
    Intent intent;
    private boolean side = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();

        send = (Button)findViewById(R.id.btn);
        list = (ListView)findViewById(R.id.listview);
        apd = new ChatArrayAdapter(getApplicationContext(),R.layout.chat);
        list.setAdapter(apd);
        chatText = (EditText)findViewById(R.id.chat_text);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()== KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    return sendChatMessage();
                }
                return false;
            }
        });
       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sendChatMessage();
           }
       });

        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(apd);
        apd.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(apd.getCount() -1);
            }
        });

    }
    private boolean sendChatMessage() {
        apd.add(new ChatMessage(side,chatText.getText().toString()));
        chatText.setText("");
        return true;
    }


}
