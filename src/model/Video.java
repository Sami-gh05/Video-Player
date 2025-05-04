package model;

import javax.swing.text.StringContent;

public abstract class Video extends Content {
    protected String subtitle;

    public Video(String title, boolean isExclusive, String description, float duration, Category category, String link, String cover, String subtitle) {
        super(title, isExclusive, description, duration, category, link, cover);
        this.subtitle = subtitle;
    }

    // Getters and setters
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
} 