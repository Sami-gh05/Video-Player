package controller;

import exceptions.ItemNotFoundException;
import model.*;

import java.util.ArrayList;

//Singleton class
public class AdminController {
    private static AdminController adminController;
    private AdminController() {
    }
    public static AdminController getAdminController() {
        if(adminController == null)
            adminController = new AdminController();
        return adminController;
    }

    public ArrayList<Channel> getTopSubChannels() {
        //this method returns 10 top subscribed channels
        ArrayList<Channel> allChannels = new ArrayList<>(showAllChannels());
        allChannels.sort((c1, c2) -> Integer.compare(c2.getSubscriberCount(), c1.getSubscriberCount()));

        return new ArrayList<>(allChannels.subList(0, Math.min(10, allChannels.size())));
    }

    public ArrayList<Content> showTopLikeContents() {
        return ContentController.getContentController().getTrendingContents();
    }

    public ArrayList<User> showAllUsers() {
        return Database.getDatabase().getUsers();
    }

    public User showUserDetails(String userId) throws ItemNotFoundException {
        for (User user: Database.getDatabase().getUsers()){
            if(user.getId().equals(userId)){
                return user;
            }
        }
        throw new ItemNotFoundException("User not found");
    }
    public ArrayList<Content> showAllContents() {
        return Database.getDatabase().getContents();
    }

    public Content showContentDetails(String contentId) throws ItemNotFoundException {
        return ContentController.getContentController().getContentByID(contentId);
    }

    public ArrayList<Channel> showAllChannels() {
        ArrayList<Channel> allChannels = new ArrayList<>();
        for(User user: Database.getDatabase().getUsers()){
            if(user.getChannel() != null)
                allChannels.add(user.getChannel());
        }
        return allChannels;
    }

    public ArrayList<Report> showAllReports() {
        return Database.getDatabase().getReports();
    }

    public void handleReport(String reportId, boolean isCorrect) throws ItemNotFoundException {
        Report report = ReportController.getReportController().getReportById(reportId);
        if(isCorrect){
            banUser(report.getUserId());
            deleteContent(report.getContentId());
        }
    }

    public User banUser(String userId) throws ItemNotFoundException {
        User user = showUserDetails(userId);
        user.setIsBanned(true);
        return user;
    }

    public User unbanUser(String userId) throws ItemNotFoundException {
        User user = showUserDetails(userId);
        user.setIsBanned(false);
        return user;
    }

    public void deleteContent(String contentId) throws ItemNotFoundException {
        Content content = ContentController.getContentController().getContentByID(contentId);
        Database.getDatabase().removeContent(content);
    }

    public void deleteComment(String contentId, String commentId) throws ItemNotFoundException {
        CommentController.getCommentController().deleteComment(contentId, commentId);
    }
} 