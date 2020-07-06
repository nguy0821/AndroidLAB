package com.example.androidlabs;

import android.database.Cursor;
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

    @Override
    protected void onCreate ( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_main_chatroom));

        listView =  (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.ChatEditText);
        sendButton = (Button) findViewById(R.id.SendBtn);
        receiveButton = (Button) findViewById(R.id.ReceiveBtn);
        db = new MyOpener(this);
        chatAdapter = new ChatAdapter(messagesLists, getApplicationContext());

        viewData();

        sendButton.setOnClickListener(e -> {
                    String mess = editText.getText().toString();
                    if(!mess.equals("")){
                        db.insertData(mess, true);
                        editText.setText("");
                        messagesLists.clear();
                        viewData();
                    }
                }
        );

        receiveButton.setOnClickListener(e -> {
            String mess = editText.getText().toString();
            if (!mess.equals("")) {
                db.insertData(mess, false);
                editText.setText("");
                messagesLists.clear();
                viewData();
            }
        });


        viewData();
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
                            "\nTHe database id is: " +message.getMessageID())
                    .setPositiveButton("Delete", (dialog, which)->{
                        db.DeleteData(message.messageID);
                        messagesLists.remove(position);
                        chatAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel",null)
                    .show();

            return true;
//            try {
//                db.DeleteData(position);
//                messagesLists.remove(position);
//                Toast.makeText(ChatRoomActivity.this, "Message deleted", Toast.LENGTH_SHORT).show();
//
//
//
////                messagesLists.remove(position);
////                arrayAdapter.notifyDataSetChanged();
////                db.DeleteData(position);
////                messagesLists.remove(position);
////                arrayAdapter = new ArrayAdapter(ChatRoomActivity.this, android.R.layout.simple_list_item_1, messagesLists);
////                listView.setAdapter(arrayAdapter);
////                Toast.makeText(ChatRoomActivity.this, "Message deleted", Toast.LENGTH_SHORT).show();
////                messagesLists.clear();
////                viewData();
//
//
//            } catch (Exception e) {
//                StringWriter sw = new StringWriter();
//                PrintWriter pw = new PrintWriter(sw);
//                e.printStackTrace(pw);
//                Toast.makeText(ChatRoomActivity.this, sw.toString(), Toast.LENGTH_SHORT).show();
//            }
//            messagesLists.clear();
//            viewData();
//            return false;
        });



        Log.d("ChatRoomActivity","onCreate");
    }

    private void viewData(){
        Cursor cursor = db.viewData();
        if(cursor.getCount() !=0){
            while (cursor.moveToNext()){
                Message message = new Message(cursor.getString(1),
                        cursor.getInt(2)==0 ?true:false,
                        cursor.getLong(0)
                );
                messagesLists.add(message);
                listView.setAdapter( chatAdapter);
            }
        }
    }
}
