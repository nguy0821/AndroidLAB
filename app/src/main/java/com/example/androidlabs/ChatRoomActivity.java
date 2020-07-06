package com.example.androidlabs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Button sendButton;
    Button receiveButton;
    MyOpener db;
    List<Message> messagesLists = new ArrayList<>();

    ChatAdapter chatAdapter;
    Cursor cursor;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_main_chatroom));

        listView = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.ChatEditText);
        sendButton = (Button) findViewById(R.id.SendBtn);
        receiveButton = (Button) findViewById(R.id.ReceiveBtn);
        chatAdapter = new ChatAdapter(messagesLists, getApplicationContext());
        db = new MyOpener(this);
        database = db.getWritableDatabase();


        String[] columns = {MyOpener.COLUMN_MESSAGE_ID, MyOpener.COLUMN_MESSAGE, MyOpener.COLUMN_IS_SEND};
        cursor = database.query(MyOpener.DB_TABLE, columns,
                null, null, null, null, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Message message = new Message(cursor.getString(1),
                        cursor.getInt(2) == 0 ? true : false,
                        cursor.getLong(0)
                );
                messagesLists.add(message);
                listView.setAdapter(chatAdapter);
            }
        }
        printCursor();

        sendButton.setOnClickListener(e -> {
                    String mess = editText.getText().toString();
                    if (!mess.equals("")) {
                        Message message = new Message(mess, true);
                        messagesLists.add(message);
                        db.insertData(mess, true);
                        editText.setText("");
                        listView.setAdapter(chatAdapter);
                    }
                }
        );

        receiveButton.setOnClickListener(e -> {
            String mess = editText.getText().toString();
            if (!mess.equals("")) {
                db.insertData(mess, false);
                Message message = new Message(mess, false);
                messagesLists.add(message);

                editText.setText("");
                listView.setAdapter(chatAdapter);
            }
        });


        //delete
        listView.setOnItemLongClickListener((parent, view, position, id) -> {

//            db.DeleteData(id);
            Message message = messagesLists.get(position);
//            messagesLists.remove(id);
//            Toast.makeText(ChatRoomActivity.this, "Message deleted", Toast.LENGTH_SHORT).show();
//            messagesLists.clear();
//            viewData();


            new AlertDialog.Builder(parent.getContext())
                    .setTitle("Do you want to delete this")
                    .setMessage("The selected row is: " + position +
                            "\nTHe database id is: " + message.getMessageID())
                    .setPositiveButton("Delete", (dialog, which) -> {
                        db.DeleteData(message.messageID);
                        messagesLists.remove(position);
                        chatAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
        Log.d("ChatRoomActivity", "onCreate");
    }

    public void printCursor() {

        Log.e("Count:", cursor.getCount() + "");
        Log.e("Database version:", database.getVersion() + "");
        Log.e("Number of columns:", cursor.getColumnCount() + "");
        Log.e("Name of the columns:", cursor.getColumnNames().toString());
        Log.e("Number of cursor", cursor.getCount() + "");
        Log.e("Row:", "");

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            while (!cursor.isAfterLast()) {

                long id = cursor.getLong(0);
                boolean isSent = cursor.getInt(2) == 0;
                String message = cursor.getString(1);

                Log.e("id", id + "");
                Log.e("isSent", isSent + "");
                Log.e("message", message + "");

                cursor.moveToNext();

            }
        }
    }


    private void viewData() {
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Message message = new Message(cursor.getString(1),
                        cursor.getInt(2) == 0 ? true : false,
                        cursor.getLong(0)
                );
                messagesLists.add(message);
                listView.setAdapter(chatAdapter);
            }
        }
    }
}
