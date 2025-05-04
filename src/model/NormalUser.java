package model;

import controller.NormalUserController;

public class NormalUser extends User{
    private final int maxPlayList = 3;
    private final int maxContentsPerPL = 10;

    public NormalUser(String username, String password, String fullName, String email, String phoneNumber, String profileCover, float credit) {
        super(username, password, fullName, email, phoneNumber, profileCover, credit, false);
    }
    public NormalUser(User user){
        super(user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getProfileCover(), user.getCredit(), false);
        this.setId(user.getId());
        super.channel = user.channel;
        super.playlists = user.playlists;
        super.subscriptions = user.subscriptions;
        super.favoriteCategories = user.favoriteCategories;
    }

    public int getMaxContentsPerPL() {
        return maxContentsPerPL;
    }

    public int getMaxPlayList() {
        return maxPlayList;
    }
}