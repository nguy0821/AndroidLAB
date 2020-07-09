package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ChatAdapter extends BaseAdapter {
    private List<Message> messageList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    ListView listView;
    public ChatAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ChatAdapter() {

    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        if (newView  == null){
            if (messageList.get(position).isSent()){
                newView  = layoutInflater.inflate(R.layout.activity_main_send, null);

            }else {
                newView = layoutInflater.inflate(R.layout.activity_main_receive, null);
            }
            TextView messageText = (TextView)newView.findViewById(R.id.textViewMessage);
            messageText.setText(messageList.get(position).message);
        }
        return newView;    }



}
