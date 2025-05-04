package controller;

import exceptions.InvalidCredentialsException;
import exceptions.ItemNotFoundException;
import exceptions.NotPremiumException;
import model.*;

import java.util.Calendar;
import java.util.Date;

//Singleton class
public class NormalUserController extends UserController{
    private static NormalUserController normalUserController;
    private NormalUserController(){
    };

    public static NormalUserController getNormalUserController() {
        if(normalUserController == null)
            normalUserController = new NormalUserController();
        return normalUserController;
    }

    public PremiumUser upgradeToPremium(PremiumSubPack pack) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
            if(currentUser.getCredit() >= pack.getPrice()){
                currentUser.setCredit(currentUser.getCredit() - pack.getPrice());
                calendar.add(Calendar.DAY_OF_MONTH, pack.getDays());
                Date premiumEndDate = calendar.getTime();
                String packType = switch (pack.getDays()) {
                    case 60 -> "SILVER";
                    case 180 -> "GOLD";
                    default -> "BRONZE";
                };
                //converting normal user into premium
                PremiumUser premiumUser = new PremiumUser((NormalUser) currentUser, premiumEndDate, packType);
                Database.getDatabase().removeUser(currentUser);
                Database.getDatabase().addUser(premiumUser);
                currentUser = premiumUser;
                return premiumUser;
            }
            else
                throw new InvalidCredentialsException("Low Credit");
    }

    @Override
    public Playlist createPlaylist(String name) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
          if(currentUser.getPlaylists().size() < 5) //normal user also has two default playlists
              return PlaylistController.getPlaylistController().createPlaylist(currentUser, name);
          else throw new NotPremiumException("Upgrade to premium for more playlists");
    }

    @Override
    public Playlist addToPlaylist(String playlistId, String contentId) throws InvalidCredentialsException {
        PlaylistController playlistController = PlaylistController.getPlaylistController();
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        if(playlistController.getPlaylistContents(currentUser, playlistId).size() < 10)
            return playlistController.addToPlaylist(currentUser, playlistId, contentId);
        else throw new NotPremiumException("Upgrade to premium in order to have more contents in each playlist");
    }
    @Override
    public Playlist addToLiked(String contentId) throws ItemNotFoundException, NotPremiumException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        if(currentUser.getPlaylists().get(0).getContents().size() < 10)
            return PlaylistController.getPlaylistController().addToPlaylist(currentUser, currentUser.getPlaylists().get(0).getId(), contentId);
        else throw new NotPremiumException("Upgrade to premium in order to have more contents in each playlist");
    }

    @Override
    public Playlist removeFromPlaylist(String playlistId, String contentId) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().removeFromPlaylist(currentUser, playlistId, contentId);
    }

    @Override
    public Playlist createChannelPlaylist(String name) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        if(currentUser.getChannel().getPlaylists().size() < 3) //normal users can only have 3 PLs in their channel
            return PlaylistController.getPlaylistController().createPlaylist(currentUser.getChannel(), name);
        else throw new NotPremiumException("Upgrade to premium for more playlists");
    }

    @Override
    public Playlist addToChannelPlaylist(String playlistId, String contentId) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        Playlist desiredPL = PlaylistController.getPlaylistController().getPlaylistById(
                PlaylistController.getPlaylistController().getChannelPlaylists(currentUser.getChannel()), playlistId);
        if(desiredPL.getContents().size() < 10)
            return PlaylistController.getPlaylistController().addToPlaylist(currentUser.getChannel(), playlistId, contentId);
        else throw new NotPremiumException("Upgrade to premium in order to have more contents in each playlist");
    }

    @Override
    public Playlist removeFromChannelPlaylist(String playlistId, String contentId) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().removeFromPlaylist(currentUser.getChannel(), playlistId, contentId);
    }

}
