package model;

import java.util.Date;

public class LiveStream extends Video {
    private int onlineViewers;
    private Date scheduledTime;

    public LiveStream(String title, boolean isExclusive, String description, float duration, Category category, String link, String cover, String subtitle, Date scheduleTime) {
        super(title, isExclusive, description, duration, category, link, cover, subtitle);
        this.onlineViewers = 0;
        this.scheduledTime = scheduleTime;
    }

    // Getters and setters
    public int getOnlineViewers() { return onlineViewers; }
    public void incrementOnlineViewers() { this.onlineViewers++; }

    public Date getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(Date scheduledTime) { this.scheduledTime = scheduledTime; }
}