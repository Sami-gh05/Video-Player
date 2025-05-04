package controller;

import exceptions.InvalidCredentialsException;
import exceptions.ItemNotFoundException;
import exceptions.NotPremiumException;
import model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

//Singleton class
public class ContentController {
    private static ContentController contentController;
    private ContentController() {
    }

    public static ContentController getContentController() {
        if(contentController == null)
            contentController = new ContentController();
        return contentController;
    }

    public Content createShortVideo(User creator, String title, boolean isExclusive, String description, float duration,
                                    Category category, String link, String cover ,String subtitle, String referenceMusicName) throws InvalidCredentialsException {
        if(isValidShortVideo(duration)){
            ShortVideo shortVideo = new ShortVideo(title, isExclusive, description, duration, category, link, cover, subtitle, referenceMusicName);
            if(creator.getChannel() != null)
                creator.getChannel().getPlaylists().get(0).addContent(shortVideo); //add to all contents PL
            else throw new InvalidCredentialsException("User does not have a channel yet");
            Database.getDatabase().addContent(shortVideo);
            return shortVideo;
        }
        else throw new InvalidCredentialsException("The duration of Short videos should be less than 30 seconds");
    }
    public Content createNormalVideo(User creator, String title, boolean isExclusive, String description, float duration,
                                     Category category, String link, String cover, String subtitle, Quality quality, Format format) throws InvalidCredentialsException {
        NormalVideo normalVideo = new NormalVideo(title, isExclusive, description, duration, category, link, cover, subtitle, quality, format);
        if(creator.getChannel() != null)
            creator.getChannel().getPlaylists().get(0).addContent(normalVideo); //add to all contents PL
        else throw new InvalidCredentialsException("User does not have a channel yet");
        Database.getDatabase().addContent(normalVideo);
        return normalVideo;
    }
    public Content createLiveStream(User creator, String title, boolean isExclusive, String description, float duration,
                                    Category category, String link, String cover, String subtitle, Date scheduledTime) throws InvalidCredentialsException {
        LiveStream liveStream = new LiveStream(title, isExclusive, description, duration, category, link, cover, subtitle, scheduledTime);
        if(creator.getChannel() != null)
            creator.getChannel().getPlaylists().get(0).addContent(liveStream); //add to all contents PL
        else throw new InvalidCredentialsException("User does not have a channel yet");
        Database.getDatabase().addContent(liveStream);
        return liveStream;
    }
    public Content createPodcast(User creator, String title, boolean isExclusive, String description, float duration,
                                 Category category, String link, String cover, String owner) throws InvalidCredentialsException {
        Podcast podcast = new Podcast(title, isExclusive, description, duration, category, link, cover, owner);
        if(creator.getChannel() !=  null)
            creator.getChannel().getPlaylists().get(0).addContent(podcast); //add to all contents PL
        else throw new InvalidCredentialsException("User does not have a channel yet");
        Database.getDatabase().addContent(podcast);
        return podcast;
    }

    public Content getContentByID(String id) throws ItemNotFoundException {
        for(Content content: Database.getDatabase().getContents()){
            if(content.getId().equals(id))
                return content;
        }
        throw new ItemNotFoundException("Content not found");
    }

    public Channel getContentChannel(String contentId) throws ItemNotFoundException {
        for(User user: Database.getDatabase().getUsers()){
            if(user.getChannel() != null){
                ArrayList<Content> channelContents = ChannelController.getChannelController().getChannelContents(user.getChannel().getId());
                if(channelContents.contains(getContentByID(contentId)))
                    return user.getChannel();
            }
        }
        return null;
    }

    public ArrayList<Content> searchContent(String query) throws ItemNotFoundException {
        ArrayList<Content> searchResult = new ArrayList<>();
        String loweCaseQuery = query.toLowerCase(); //To be indifferent to uppercase and lowercase letters

        for(User user: Database.getDatabase().getUsers()){
            if(user.getChannel() != null && (user.getChannel().getName().contains(loweCaseQuery) || user.getChannel().getDescription().contains(loweCaseQuery)))
                searchResult.addAll(ChannelController.getChannelController().getChannelContents(user.getChannel().getId()));
        }

        for(Content content: Database.getDatabase().getContents()){
            if(!searchResult.contains(content) && (content.getTitle().contains(loweCaseQuery) || content.getDescription().contains(loweCaseQuery)))
                searchResult.add(content);
        }
        return searchResult;
    }

    public void likeContent(User user, String contentId) throws ItemNotFoundException {
        Content content = getContentByID(contentId);
        content.incrementLikes();
    }

    public Content viewContent(User user, String contentId) throws InvalidCredentialsException {
        Content content = getContentByID(contentId);
        if(content.getIsExclusive() && !user.hasPremiumAccess())
            throw new NotPremiumException("This content is for premium users only");
        else {
            content.incrementViews();
            if(content instanceof LiveStream)
                ((LiveStream) content).incrementOnlineViewers();
            return content;
        }
    }

    public ArrayList<Content> getRecommendedContent(User user) throws ItemNotFoundException {
        //The algorithm relates on scoring contents
        Map<Content, Integer> contentScores = new HashMap<>();

        ArrayList<Content> likedContents = user.getPlaylists().get(0).getContents();
        ArrayList<Channel> subscribedChannels = user.getSubscriptions();
        ArrayList<Category> favoriteCategories = user.getFavoriteCategories();

        for(Content content: Database.getDatabase().getContents()){
            int score = 0;

            if(likedContents.contains(content))
                score += 5;
            if(favoriteCategories.contains(content.getCategory()))
                score += 8;
            if(subscribedChannels.contains(getContentChannel(content.getId())))
                score += 10;

            if(score > 0)
                contentScores.put(content, score);
        }

        List<Content> sortedContents = contentScores.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
        return (ArrayList<Content>) sortedContents;
    }

    public ArrayList<Content> getTrendingContents() {
        return sortContent(Database.getDatabase().getContents(), SortOption.POPULAR);
    }

    public ArrayList<Content> sortContent(ArrayList<Content> contents, SortOption sortBy) {
        ArrayList<Content> tempContents = new ArrayList<>(contents);
        if(sortBy.getSortOption().equals("Popular"))
            tempContents.sort(Comparator.comparingInt(Content::getLikes).reversed());
        else if(sortBy.getSortOption().equals("Viewed"))
            tempContents.sort(Comparator.comparingInt(Content::getViews).reversed());
        else return contents;
        return tempContents;
    }
    public ArrayList<Content> filterContent(ArrayList<Content> contents, ContentType type, Category category){
        ArrayList<Content> filteredContents = new ArrayList<>();
        for(Content content: contents){
            boolean matchesType;
            if(type != null){
                if(type.equals(ContentType.VIDEO))
                    matchesType = content instanceof Video;
                else
                    matchesType = content instanceof Podcast;
            }
            else matchesType = false;

            boolean matchesCategory = (category == null || category.equals(content.getCategory()));

            if(matchesType && matchesCategory)
                filteredContents.add(content);
        }
        return filteredContents;
    }

    public boolean isValidShortVideo(float duration) {
        return duration < 30;
    }
} 