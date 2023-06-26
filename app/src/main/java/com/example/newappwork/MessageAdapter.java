package com.example.newappwork;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    private List<Message> mmsg;
    private static final int userviewtype=0, botviewtype=1;
    public MessageAdapter(List<Message> mmsg) {
        this.mmsg = mmsg;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view;
        if(viewType==userviewtype)
            view=inflater.inflate(R.layout.item_message_user,parent,false);
        else
            view=inflater.inflate(R.layout.item_message_bot,parent,false);
        return  new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message= mmsg.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mmsg.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        Message msg=mmsg.get(position);
        return msg.isSentbyUser()?userviewtype:botviewtype;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView mtext;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mtext=itemView.findViewById(R.id.text_message_user);
        }
        public void bind(Message message)
        {
            if(message.isSentbyUser())
            {
                mtext=itemView.findViewById(R.id.text_message_user);
                mtext.setText(message.getmText());
            }
            else
            {
                mtext=itemView.findViewById(R.id.text_message_bot);
                mtext.setText(message.getmText());
            }
        }
    }
}

