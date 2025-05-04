package controller;

import exceptions.InvalidCredentialsException;
import exceptions.ItemNotFoundException;
import exceptions.NotPremiumException;
import model.*;

import java.util.Calendar;
import java.util.Date;

//Singleton class
public class PremiumUserController extends UserController{
    private static PremiumUserController premiumUserController;
    private PremiumUserController(){};

    public static PremiumUserController getPremiumUserController() {
        if(premiumUserController == null)
            premiumUserController = new PremiumUserController();
        return premiumUserController;
    }
    public PremiumUser renewToPremium(PremiumSubPack pack) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        if(currentUser.getCredit() >= pack.getPrice()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(((PremiumUser)currentUser).getPremiumEndDate()); //downCasting to PremiumUser
            calendar.add(Calendar.DAY_OF_MONTH, pack.getDays());
            currentUser.setCredit(currentUser.getCredit() - pack.getPrice());
            return (PremiumUser) currentUser; //downCasting to PremiumUser
        } else throw new InvalidCredentialsException("Low credit");
    }

    @Override
    public Playlist createPlaylist(String name) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().createPlaylist(currentUser, name);
    }

    @Override
    public Playlist addToPlaylist(String playlistId, String contentId) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().addToPlaylist(currentUser, playlistId, contentId);
    }

    @Override
    public Playlist addToLiked(String contentId) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().addToPlaylist(currentUser, currentUser.getPlaylists().get(0).getId(), contentId);
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
        return PlaylistController.getPlaylistController().createPlaylist(currentUser.getChannel(), name);
    }
    @Override
    public Playlist addToChannelPlaylist(String playlistId, String contentId) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().addToPlaylist(currentUser.getChannel(), playlistId, contentId);
    }

    @Override
    public Playlist removeFromChannelPlaylist(String playlistId, String contentId) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        return PlaylistController.getPlaylistController().removeFromPlaylist(currentUser.getChannel(), playlistId, contentId);
    }
}
