package model;

public class NormalVideo extends Video {
    private Quality quality;
    private Format format;

    public NormalVideo(String title, boolean isExclusive, String description, float duration, Category category, String link, String cover, String subtitle, Quality quality, Format format) {
        super(title, isExclusive, description, duration, category, link, cover, subtitle);
        this.quality = quality;
        this.format = format;
    }

    // Getters and setters
    public Quality getQuality() { return quality; }
    public void setQuality(Quality quality) { this.quality = quality; }

    public Format getFormat() { return format; }
    public void setFormat(Format format) { this.format = format; }
} 