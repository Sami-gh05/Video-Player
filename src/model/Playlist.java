package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private static int idCounter = 100;
    private final String id;
    private String name;
    private ArrayList<Content> contents;

    public Playlist(String name) {
        this.id = String.valueOf(idCounter++);
        this.name = name;
        this.contents = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Content> getContents() { return contents; }
    public void addContent(Content content) { this.contents.add(content); }
    public void removeContent(Content content) { this.contents.remove(content); }

} 