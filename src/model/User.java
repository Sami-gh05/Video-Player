package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class User extends Account{
    protected float credit;
    protected ArrayList<Playlist> playlists;
    protected Channel channel;
    protected ArrayList<Channel> subscriptions;
    protected ArrayList<Category> favoriteCategories;
    protected boolean isBanned;
    protected boolean isPremium;

    public User(String username, String password, String fullName, String email, String phoneNumber, String profileCover, float credit, boolean isPremium) {
        super(username, password, fullName, email, phoneNumber, profileCover, true);
        this.credit = credit;
        this.playlists = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.favoriteCategories = new ArrayList<>();
        this.isBanned = false;
        this.isPremium = isPremium;
    }

    // Getters and setters
    public float getCredit() { return credit; }
    public void setCredit(float credit) { this.credit = credit; }

    public ArrayList<Playlist> getPlaylists() { return playlists; }
    public void addPlaylist(Playlist playlist) { this.playlists.add(playlist); }

    public Channel getChannel() { return channel; }
    public void setChannel(Channel channel) { this.channel = channel; }

    public ArrayList<Channel> getSubscriptions() { return subscriptions; }
    public void addSubscription(Channel channel) { this.subscriptions.add(channel); }
    public void removeSubscription(Channel channel) { this.subscriptions.remove(channel); }

    public ArrayList<Category> getFavoriteCategories() { return favoriteCategories; }
    public void resetCategories(ArrayList<Category> categories){
        this.favoriteCategories = categories;
    }
    public boolean hasPremiumAccess(){
        return this instanceof PremiumUser;
    }
    public boolean getIsBanned(){
        return isBanned;
    };
    public void setIsBanned(boolean isBanned){this.isBanned = isBanned;}

    public boolean isPremium() {
        return isPremium;
    }

}