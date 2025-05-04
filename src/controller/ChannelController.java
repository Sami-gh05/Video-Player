package controller;

import exceptions.ItemNotFoundException;
import model.*;

import java.util.ArrayList;

//Singleton class
public class ChannelController {
    private static ChannelController channelController;
    private ChannelController(){
    }

    public static ChannelController getChannelController() {
        if(channelController == null)
            channelController = new ChannelController();
        return channelController;
    }

    public Channel createChannel(User user, String name, String description, String cover){
        Channel channel = new Channel(name, description, cover, user.getFullName());
        PlaylistController.getPlaylistController().createPlaylist(channel, "allContents");
        user.setChannel(channel);
        return channel;
    }
    public Channel updateChannelName(Channel channel, String updatedName){
        channel.setName(updatedName);
        return channel;
    }
    public Channel updateChannelDescription(Channel channel, String updatedDescription){
        channel.setDescription(updatedDescription);
        return channel;
    }
    public Channel updateChannelCover(Channel channel, String updatedCover){
        channel.setCover(updatedCover);
        return channel;
    }
    public void subChannel(User user, String channelId) throws ItemNotFoundException {
        Channel channel = getChannelById(channelId);
        user.addSubscription(channel);
        channel.addSubscriber(user);
    }
    public void unSubChannel(User user, String channelId) throws ItemNotFoundException {
        Channel channel = getChannelById(channelId);
        user.removeSubscription(channel);
        channel.removeSubscriber(user);
    }
    //Thrown exception should be caught whenever this method is called (like in View part of MVC)
    public Channel getChannelById(String id) throws ItemNotFoundException {
        for(User user: Database.getDatabase().getUsers()){
            if(user.getChannel() != null){
                if(user.getChannel().getId().equals(id))
                    return user.getChannel();
            }
        }
        throw new ItemNotFoundException("Channel not found");
    }

    public ArrayList<Content> getChannelContents(String channelId) throws ItemNotFoundException {
        Channel channel = getChannelById(channelId);
        ArrayList<Content> channelContents = new ArrayList<>();
        ArrayList<Playlist> channelPlaylists = channel.getPlaylists();
        for(Playlist playlist : channelPlaylists){
            channelContents.addAll(playlist.getContents());
        }
        return channelContents;
    }
    public ArrayList<Playlist> getChannelPlaylists(String channelId) throws ItemNotFoundException {
        Channel channel = getChannelById(channelId);
        return channel.getPlaylists();
    }
    public ArrayList<User> getChannelSubscribers(String channelId) throws ItemNotFoundException {
        Channel channel = getChannelById(channelId);
        return channel.getSubscribers();
    }
    //check it
    public Channel publishContent(Channel channel, Content content) throws ItemNotFoundException {
        channel.getPlaylists().get(0).addContent(content);
        return channel;
    }
    public void publishPlaylist(Channel channel, Playlist playlist){
        channel.addPlaylist(playlist);
    }

}
