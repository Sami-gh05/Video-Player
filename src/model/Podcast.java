package model;

public class Podcast extends Content {
    protected String owner;

    public Podcast(String title, boolean isExclusive, String description, float duration, Category category, String link, String cover, String owner) {
        super(title, isExclusive, description, duration, category, link, cover);
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
