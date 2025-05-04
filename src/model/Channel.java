package model;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private static int idCounter = 100;
    private final String id;
    private String name;
    private String description;
    private String cover;
    private String ownerName;
    private ArrayList<Playlist> playlists;
    private ArrayList<User> subscribers;

    public Channel(String name, String description, String cover, String ownerName) {
        this.id = String.valueOf(idCounter++);
        this.name = name;
        this.description = description;
        this.cover = cover;
        this.playlists = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.ownerName = ownerName;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public ArrayList<Playlist> getPlaylists() { return playlists; }
    public void addPlaylist(Playlist playlist) { this.playlists.add(playlist); }

    public ArrayList<User> getSubscribers() { return subscribers; }
    public void addSubscriber(User subscriber) { this.subscribers.add(subscriber); }
    public void removeSubscriber(User subscriber) { this.subscribers.remove(subscriber); }
    public int getSubscriberCount(){
        return this.getSubscribers().size();
    }
} 