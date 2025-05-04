package controller;

import exceptions.ItemNotFoundException;
import model.*;

import java.util.ArrayList;

//Singleton class
public class PlaylistController {
    private static PlaylistController playlistController;
    private PlaylistController() {
    }
    public static PlaylistController getPlaylistController() {
        if(playlistController == null)
            playlistController = new PlaylistController();
        return playlistController;
    }

    public Playlist createPlaylist(User user, String name) {
        Playlist playlist = new Playlist(name);
        user.addPlaylist(playlist);
        return playlist;
    }

    public Playlist addToPlaylist(User user, String playlistId, String contentId) throws ItemNotFoundException {
        //find content
        Content tempContent;
        for(Content content: getPlaylistContents(user, playlistId)){
            if(content.getId().equals(contentId)){
                tempContent = content;
                Playlist playlist = getPlaylistById(getUserPlaylists(user), playlistId);
                if(playlist != null){
                    playlist.addContent(tempContent);
                    return playlist;
                    //check
                }
            }
        }
        throw new ItemNotFoundException("Content not found");
    }

    public Playlist removeFromPlaylist(User user, String playlistId, String contentId) throws ItemNotFoundException {
        //find content
        Content tempContent;
        for(Content content: getPlaylistContents(user, playlistId)){
            if(content.getId().equals(contentId)){
                tempContent = content;
                Playlist playlist = getPlaylistById(getUserPlaylists(user), playlistId);
                if(playlist != null){
                    playlist.removeContent(tempContent);
                    return playlist;
                    //check
                }
            }
        }
        throw new ItemNotFoundException("Content not found");
    }

    public Playlist createPlaylist(Channel channel, String name) {
        Playlist playlist = new Playlist(name);
        ChannelController.getChannelController().publishPlaylist(channel, playlist);
        return playlist;
    }

    public Playlist addToPlaylist(Channel channel, String playlistId, String contentId) throws ItemNotFoundException {
        //find content
        Content tempContent;
        for(Content content: getPlaylistContents(channel, playlistId)){
            if(content.getId().equals(contentId)){
                tempContent = content;
                Playlist playlist = getPlaylistById(getChannelPlaylists(channel), playlistId);
                if(playlist != null){
                    playlist.addContent(tempContent);
                    return playlist;
                    //check
                }
            }
        }
        throw new ItemNotFoundException("Content not found");
    }

    public Playlist removeFromPlaylist(Channel channel, String playlistId, String contentId) throws ItemNotFoundException {
        //find content
        Content tempContent;
        for(Content content: getPlaylistContents(channel, playlistId)){
            if(content.getId().equals(contentId)){
                tempContent = content;
                Playlist playlist = getPlaylistById(getChannelPlaylists(channel), playlistId);
                if(playlist != null){
                    playlist.removeContent(tempContent);
                    return playlist;
                    //check
                }
            }
        }
        throw new ItemNotFoundException("Content not found");
    }

    public ArrayList<Playlist> getUserPlaylists(User user) {
        return user.getPlaylists();
    }

    public ArrayList<Playlist> getChannelPlaylists(Channel channel) {
        return channel.getPlaylists();
    }

    public Playlist getPlaylistById(ArrayList<Playlist> playlists, String playlistId) throws ItemNotFoundException {
        for(Playlist playlist: playlists){
            if(playlist.getId().equals(playlistId))
                return playlist;
        }
        throw new ItemNotFoundException("Playlist not found");
    }

    public ArrayList<Content> getPlaylistContents(Channel channel, String playlistId) throws ItemNotFoundException {
        for(Playlist playlist: channel.getPlaylists()){
            if(playlist.getId().equals(playlistId))
                return playlist.getContents();
        }
        throw new ItemNotFoundException("Playlist not found");
    }

    public ArrayList<Content> getPlaylistContents(User user, String playlistId) throws ItemNotFoundException {
        for(Playlist playlist: user.getPlaylists()){
            if(playlist.getId().equals(playlistId))
                return playlist.getContents();
        }
        throw new ItemNotFoundException("Playlist not found");
    }

    public void ensureDefaultPlaylists(User user) {
        createPlaylist(user, "Liked"); //first playlist for each user
        createPlaylist(user, "Watch Later"); //second playlist for each user
    }
} 