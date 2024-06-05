package uk.ac.wlv.afinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;
    private MessageClickListener clickListener;

    public interface MessageClickListener {
        void onMessageClick(Message message);
    }

    public MessageAdapter(List<Message> messageList, MessageClickListener clickListener) {
        this.messageList = messageList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView contentTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.text_view_content);
            itemView.setOnClickListener(this);
        }

        public void bind(Message message) {
            contentTextView.setText(message.getContent());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Message message = messageList.get(position);
                clickListener.onMessageClick(message);
            }
        }
    }
}
