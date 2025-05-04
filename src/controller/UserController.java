package controller;

import exceptions.InvalidCredentialsException;
import exceptions.ItemNotFoundException;
import exceptions.NotPremiumException;
import model.*;

import java.util.ArrayList;

public abstract class UserController {
    protected User currentUser;
    UserController(){
    }
    public void setFavoriteCategory(ArrayList<Category> categories){
        currentUser.resetCategories(categories);
    }
    public String showProfile() throws ItemNotFoundException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new ItemNotFoundException("No user is logged in");
        if(currentUser instanceof NormalUser normalUser){
            return "Normal user" + ",\nId:\t" + normalUser.getId() + ",\nUsername:\t" + normalUser.getUsername()
                    + ",\nPassword:\t" + normalUser.getPassword() + ",\nFull Name:\t" + normalUser.getFullName()
                    + ",\nEmail:\t" + normalUser.getEmail() + ",\nPhone Number:\t" + normalUser.getPhoneNumber()
                    + ",\nProfile Cover:\t" + normalUser.getProfileCover() + ",\nMax Playlist Number:\t"
                    + normalUser.getMaxPlayList() + ",\nMax Contents Per Playlist:\t" + normalUser.getMaxContentsPerPL();
        }
        else if(currentUser instanceof PremiumUser premiumUser){
            return "Premium user" + ",\nId:\t" + premiumUser.getId() + ",\nUsername:\t" + premiumUser.getUsername()
                    + ",\nPassword:\t" + premiumUser.getPassword() + ",\nFull Name:\t" + premiumUser.getFullName()
                    + ",\nEmail:\t" + premiumUser.getEmail() + ",\nPhone Number:\t" + premiumUser.getPhoneNumber()
                    + ",\nProfile Cover:\t" + premiumUser.getProfileCover() + ",\nPremium Subscription Package Type:\t"
                    + premiumUser.getPackageType() + ",\nPremium End Date:\t" + premiumUser.getPremiumEndDate();
        }
        else return null;
    }
    public User updateUsername(String updatedUsername) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        currentUser.setUsername(updatedUsername);
        return currentUser;
    }
    public User updatePassword(String updatedPassword) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        currentUser.setPassword(updatedPassword);
        return currentUser;
    }
    public User updateFullName(String updatedFullName) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        currentUser.setFullName(updatedFullName);
        return currentUser;
    }
    public User updatePhoneNumber(String updatedPhoneNumber) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        if(AuthorizationController.getAuthorizationController().isValidPhoneNumber(updatedPhoneNumber).equals("VALID")){
            currentUser.setPhoneNumber(updatedPhoneNumber);
            return currentUser;
        }
        else
            throw new Exception(AuthorizationController.getAuthorizationController().isValidPhoneNumber(updatedPhoneNumber));
    }
    public User updateEmail(String updatedEmail) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        if(AuthorizationController.getAuthorizationController().isValidEmail(updatedEmail).equals("VALID")){
            currentUser.setEmail(updatedEmail);
            return currentUser;
        }
        else throw new Exception(AuthorizationController.getAuthorizationController().isValidEmail(updatedEmail));
    }
    public User updateProfileCover(String updatedProfileCover) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        currentUser.setProfileCover(updatedProfileCover);
        return currentUser;
    }
    public User addCredit(float amount) throws Exception {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new Exception("No user is logged in");
        currentUser.setCredit(currentUser.getCredit() + amount);
        return currentUser;
    }
    public abstract Playlist createPlaylist(String name) throws InvalidCredentialsException;
    public abstract Playlist addToPlaylist(String playlistId, String contentId) throws InvalidCredentialsException;
    public abstract Playlist removeFromPlaylist(String playlistId, String contentId) throws InvalidCredentialsException;
    public abstract Playlist createChannelPlaylist(String name) throws InvalidCredentialsException;
    public abstract Playlist addToChannelPlaylist(String playlistId, String contentId) throws InvalidCredentialsException;
    public abstract Playlist removeFromChannelPlaylist(String playlistId, String contentId) throws InvalidCredentialsException;
    public abstract Playlist addToLiked(String contentId) throws InvalidCredentialsException;
    public ArrayList<Playlist> showUserPlaylists(){
        return PlaylistController.getPlaylistController().getUserPlaylists(currentUser);
    }
    public ArrayList<Content> showUserPlaylistContents(String playlistId) throws ItemNotFoundException {
        for(Playlist playlist: currentUser.getPlaylists()){
            if(playlist.getId().equals(playlistId))
                return playlist.getContents();
        }
        throw new ItemNotFoundException("Playlist not found");
    }

    public ArrayList<Content> showChannelPlaylistContents(String playlistId) throws ItemNotFoundException {
        for(Playlist playlist: currentUser.getChannel().getPlaylists()){
            if(playlist.getId().equals(playlistId))
                return playlist.getContents();
        }
        throw new ItemNotFoundException("Playlist not found");
    }
    public String play(String contentId) throws InvalidCredentialsException {
        Content content = ContentController.getContentController().viewContent(currentUser, contentId);
        return content.getLink();
    }
    public void like(String contentId) throws InvalidCredentialsException {
        ContentController.getContentController().likeContent(currentUser, contentId);
        if(currentUser.isPremium())
            PremiumUserController.getPremiumUserController().addToLiked(contentId);
        else
            NormalUserController.getNormalUserController().addToLiked(contentId);
    }
    public ArrayList<Content> search(String query) throws ItemNotFoundException{
        return ContentController.getContentController().searchContent(query);
    }
    public Report report(String contentId, String reportComment){
        return ReportController.getReportController().createReport(currentUser, contentId, reportComment);
    }
    public void subscribe(String channelId) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        ChannelController.getChannelController().subChannel(currentUser, channelId);
    }

    public void unSubscribe(String channelId) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        ChannelController.getChannelController().unSubChannel(currentUser, channelId);
    }

    public ArrayList<Content> showRecommendedContents() throws ItemNotFoundException {
        return ContentController.getContentController().getRecommendedContent(currentUser);
    }
    public ArrayList<Channel> showSubscription(){
        return currentUser.getSubscriptions();
    }
    public ArrayList<Channel> showChannels(){
        ArrayList<Channel> allChannels = new ArrayList<>();
        for(User user: Database.getDatabase().getUsers()){
            if(user.getChannel() != null)
                allChannels.add(user.getChannel());
        }
        return allChannels;
    }
    public Channel getUserChannel(String channelId) throws ItemNotFoundException {
        return ChannelController.getChannelController().getChannelById(channelId);
    }
    public Comment comment(String contentId, String comment) throws ItemNotFoundException {
        return CommentController.getCommentController().addComment(currentUser, contentId, comment);
    }
    public ArrayList<Comment> showComments(String contentId) throws ItemNotFoundException {
        return CommentController.getCommentController().getComments(contentId);
    }
    public void deleteComment(String contentId, String commentId) throws ItemNotFoundException {
        CommentController.getCommentController().deleteComment(contentId, commentId);
    }
    public Comment editComment(String contentId, String commentId, String newComment) throws ItemNotFoundException {
        return CommentController.getCommentController().editComment(contentId, commentId, newComment);
    }
    public Channel createChannel(String name, String description, String cover) throws InvalidCredentialsException{
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        return ChannelController.getChannelController().createChannel(currentUser, name, description, cover);
    }
    public Channel updateChannelName(String updatedName) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        return ChannelController.getChannelController().updateChannelName(currentUser.getChannel(), updatedName);
    }
    public Channel updateChannelDescription(String updatedDescription) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        return ChannelController.getChannelController().updateChannelDescription(currentUser.getChannel(), updatedDescription);
    }
    public Channel updateChannelCover(String updatedCover) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        return ChannelController.getChannelController().updateChannelCover(currentUser.getChannel(), updatedCover);
    }
    public String showUserChannel() throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        return "Details of channel with Id:\t" + currentUser.getChannel().getId() + ",\nName:\t" + currentUser.getChannel().getName()
                + ",\nDescription:\t" + currentUser.getChannel().getDescription() + ",\nCover:\t" +
                currentUser.getChannel().getCover() + ",\nOwnerName:\t" + currentUser.getChannel().getOwnerName();
    }
    public Channel publishContent(Content content) throws InvalidCredentialsException {
        currentUser = AuthorizationController.getAuthorizationController().getCurrentUser();
        if(currentUser == null)
            throw new InvalidCredentialsException("No user is logged in");
        return ChannelController.getChannelController().publishContent(currentUser.getChannel(), content);
    }
    public ArrayList<User> showChannelSubs(String channelId) throws InvalidCredentialsException {
        return ChannelController.getChannelController().getChannelSubscribers(channelId);
    }
    public ArrayList<Playlist> showChannelPlaylists(String channelId) throws InvalidCredentialsException {
        return ChannelController.getChannelController().getChannelPlaylists(channelId);
    }
    public ArrayList<Content> showChannelContents(String channelId) throws InvalidCredentialsException {
        return ChannelController.getChannelController().getChannelContents(channelId);
    }




}
