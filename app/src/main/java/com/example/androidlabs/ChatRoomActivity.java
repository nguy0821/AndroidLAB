package com.example.androidlabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    int version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_main_chatroom));

        listView = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.ChatEditText);
        sendButton = (Button) findViewById(R.id.SendBtn);
        receiveButton = (Button) findViewById(R.id.ReceiveBtn);

        // If it returns null then you are on a phone, otherwise it’s on a tablet. Store this in result in a Boolean variable
        boolean onTablet = findViewById(R.id.fragmentLocation) != null;


        chatAdapter = new ChatAdapter(messagesLists, getApplicationContext());
        db = new MyOpener(this);
        database = db.getWritableDatabase();


//        String[] columns = {MyOpener.COLUMN_MESSAGE_ID, MyOpener.COLUMN_MESSAGE, MyOpener.COLUMN_IS_SEND};
//        cursor = database.query(MyOpener.DB_TABLE, columns,
//                null, null, null, null, null);
//
//        if (cursor.getCount() != 0) {
//            while (cursor.moveToNext()) {
//                Message message = new Message(cursor.getString(1),
//                        cursor.getInt(2) == 0 ? true : false,
//                        cursor.getLong(0)
//                );
//                messagesLists.add(message);
//                listView.setAdapter(chatAdapter);
//            }
//        }

        viewData();

        listView.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("item", messagesLists.get(position).message);
            dataToPass.putInt("id", position);
            dataToPass.putLong("db_id", messagesLists.get(position).messageID);


            if (onTablet){
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            }else {
                Intent emptyActivity = new Intent(this, EmptyActivity.class);
                emptyActivity.putExtras(dataToPass);
                startActivityForResult(emptyActivity, 345);
            }

        });

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
                messagesLists.clear();
                viewData();
            }
        });


        //delete
//        listView.setOnItemLongClickListener((parent, view, position, id) -> {
//
//            Message message = messagesLists.get(position);
//            messagesLists.remove(id);
//            Toast.makeText(ChatRoomActivity.this, "Message deleted", Toast.LENGTH_SHORT).show();
//            messagesLists.clear();
//            viewData();

//            new AlertDialog.Builder(parent.getContext())
//                    .setTitle("Do you want to delete this")
//                    .setMessage("The selected row is: " + position +
//                            "\nTHe database id is: " + message.getMessageID())
//                    .setPositiveButton("Delete", (dialog, which) -> {
//                        db.deleteEHIDE(message.messageID);
//                        messagesLists.remove(position);
//                        chatAdapter.notifyDataSetChanged();
//                    })
//                    .setNegativeButton("Cancel", null)
//                    .show();
//
//            return true;
//        });


        Log.d("ChatRoomActivity", "onCreate");
    }

//        @SuppressLint("LongLogTag")
//        public void printCursor(Cursor c, int version) {
//            Log.e("Database version number ", database.getVersion() + "");
//            Log.e("Number of columns ", c.getColumnCount() + "");
//            Log.e("Name of the columns: ", Arrays.toString(c.getColumnNames()));
//            Log.e("number of rows ", c.getCount() + "");
//            Log.e("Results in the cursor", DatabaseUtils.dumpCursorToString(c));
//            cursor.moveToFirst();
//
//        }

    private void viewData(){
        Cursor cursor = db.viewData();

        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                Message model = new Message(cursor.getString(1), cursor.getInt(2) == 0, cursor.getLong(0));
                messagesLists.add(model);
                ChatAdapter adt = new ChatAdapter(messagesLists, getApplicationContext());
                listView.setAdapter(adt);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 345) {
            if (resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra("db_id", 0);
                deleteMessageId((int) id);
            }
        }
    }

    public void deleteMessageId(int id)
    {
        db.deleteEHIDE(id);
        messagesLists.clear();
        viewData();
    }


}
