package uk.ac.wlv.afinal;

public class Message {
    private long id;
    private String content;
    private String imagePath;
    private long timestamp;

    public Message() {
        // Default constructor required for Firebase
    }

    public Message(long id, String content, String imagePath, long timestamp) {
        this.id = id;
        this.content = content;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
