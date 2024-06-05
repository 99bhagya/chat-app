package uk.ac.wlv.afinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements MessageAdapter.MessageClickListener {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messageList;
    private MessageDatabaseHelper dbHelper;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView, adapter, and database helper
        recyclerView = findViewById(R.id.recycler_view_messages);
        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        dbHelper = new MessageDatabaseHelper(this);

        // Load messages from the database
        loadMessages();
    }
    Button buttonSend = findViewById(R.id.button_send);
    buttonSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendMessage(); // Call the sendMessage method when the button is clicked
        }
    });
    // Method to load messages from the database
    private void loadMessages() {
        messageList.clear();
        messageList.addAll(dbHelper.getAllMessages());
        adapter.notifyDataSetChanged();
    }

    // Method for composing and sending messages
    // Method to send a message
    // Method to send a message
    private void sendMessage() {
        String messageContent = editTextMessage.getText().toString().trim();
        if (!messageContent.isEmpty()) {
            // Create a new Message object
            long timestamp = System.currentTimeMillis(); // You can use your preferred timestamp
            Message message = new Message(0, messageContent, null, timestamp);

            // Add the message to the database
            long messageId = dbHelper.addMessage(message);

            if (messageId != -1) {
                // If the message was added successfully, update the UI
                message.setId(messageId);
                messageList.add(0, message); // Add the new message at the beginning of the list
                adapter.notifyItemInserted(0); // Notify the adapter about the new message
                recyclerView.smoothScrollToPosition(0); // Scroll to the top
                editTextMessage.setText(""); // Clear the EditText after sending the message

                // Optionally, scroll to the bottom of the RecyclerView
                 recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            } else {
                // Show a toast message if there was an error
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // Method for deleting a message
    private void deleteMessage(long messageId) {
        dbHelper.deleteMessage(messageId);
        // Update the UI after deleting the message
        loadMessages();
    }

    // Method for editing a message
    private void editMessage(long messageId, String newContent, String newImagePath) {
        Message editedMessage = new Message(messageId, newContent, newImagePath, System.currentTimeMillis());
        dbHelper.updateMessage(editedMessage);
        // Update the UI after editing the message
        loadMessages();
    }

    // Method to handle message item click events
    @Override
    public void onMessageClick(Message message) {
        // Handle click event for a message item
        Toast.makeText(this, "Clicked on message: " + message.getContent(), Toast.LENGTH_SHORT).show();
    }
}
