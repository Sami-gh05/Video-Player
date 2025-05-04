package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Content {
    protected static int idCounter = 100;
    protected final String id;
    protected String title;
    protected boolean isExclusive;
    protected String description;
    protected float duration;
    protected int views;
    protected int likes;
    protected final Date releaseDate;
    protected Category category;
    protected final String link;
    protected String cover;
    protected ArrayList<Comment> comments;

    protected Content(String title, boolean isExclusive, String description, float duration, Category category, String link, String cover) {
        this.id = String.valueOf(idCounter++);
        this.title = title;
        this.description = description;
        this.category = category;
        this.isExclusive = isExclusive;
        this.views = 0;
        this.likes = 0;
        this.releaseDate = new Date();
        this.comments = new ArrayList<>();
        this.duration = duration;
        this.link = link;
        this.cover = cover;
    }

    // Getters and setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean getIsExclusive() { return isExclusive; }
    public void setIsExclusive(boolean exclusive) { isExclusive = exclusive; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getDuration() { return duration; }
    public void setDuration(float duration) { this.duration = duration; }

    public int getViews() { return views; }
    public void incrementViews() { this.views++; }

    public int getLikes() { return likes; }
    public void incrementLikes() { this.likes++; }

    public Date getReleaseDate() { return releaseDate; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getLink() { return link; }

    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }

    public ArrayList<Comment> getComments() { return comments; }
    public void addComment(Comment comment) { this.comments.add(comment); }
    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }
} 