package model;

public class ShortVideo extends Video {
    private String referenceMusicName;

    public ShortVideo(String title, boolean isExclusive, String description, float duration, Category category, String link, String cover, String subtitle, String referenceMusicName) {
        super(title, isExclusive, description, duration, category, link, cover, subtitle);
        this.referenceMusicName = referenceMusicName;
    }

    // Getters and setters
    public String getReferenceMusicName() { return referenceMusicName; }
    public void setReferenceMusicName(String referenceMusicName) { this.referenceMusicName = referenceMusicName; }
} 