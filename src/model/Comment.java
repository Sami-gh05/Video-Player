package model;

import java.util.Date;

public class Comment {
    private static int idCounter = 100;
    private final String commentId;
    private final User commenter;
    private String comment;
    private final Date date;
    private boolean isEdited;

    public Comment(User commenter, String comment) {
        this.commentId = String.valueOf(idCounter++);
        this.commenter = commenter;
        this.comment = comment;
        this.date = new Date();
        this.isEdited = false;
    }

    // Getters and setters
    public String getCommentId() { return commentId; }

    public User getCommenter() { return commenter; }

    public String getComment() { return comment; }
    public void setComment(String comment) { 
        this.comment = comment;
        this.isEdited = true;
    }

    public Date getDate() { return date; }

    public boolean getIsEdited() { return isEdited; }

    public void setIsEdited(boolean edited) {
        isEdited = edited;
    }
}