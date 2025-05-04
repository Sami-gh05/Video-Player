package controller;

import exceptions.ItemNotFoundException;
import model.*;

import java.util.ArrayList;

//Singleton class
public class CommentController {
    private static CommentController commentController;
    private CommentController(){
    };

    public static CommentController getCommentController() {
        if(commentController == null)
            commentController = new CommentController();
        return commentController;
    }
    public Comment addComment(User commenter, String contentId, String commentText) throws ItemNotFoundException {
        Content content = ContentController.getContentController().getContentByID(contentId);
        Comment comment = new Comment(commenter, commentText);
        content.addComment(comment);
        return comment;
    }
    public ArrayList<Comment> getComments(String contentId) throws ItemNotFoundException {
        Content content = ContentController.getContentController().getContentByID(contentId);
        return content.getComments();
    }
    public Comment getCommentById(String contentId, String commentId) throws ItemNotFoundException {
        Content content = ContentController.getContentController().getContentByID(contentId);
        for (Comment comment : content.getComments()) {
            if (comment.getCommentId().equals(commentId))
                return comment;
        }
        throw new ItemNotFoundException("Comment not found");
    }
    public void deleteComment(String contentId, String commentId) throws ItemNotFoundException {
        Content content = ContentController.getContentController().getContentByID(contentId);
        Comment comment = getCommentById(contentId, commentId);
        content.removeComment(comment);
    }
    public Comment editComment(String contentId, String commentId, String newComment) throws ItemNotFoundException {
        Comment comment = getCommentById(contentId, commentId);
        comment.setComment(newComment);
        comment.setIsEdited(true);
        return comment;
    }

}
