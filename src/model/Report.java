package model;

public class Report {
    private static int idCounter = 100;
    private final String id;
    private User reporter;
    private String contentId;
    private String reportComment;
    private String userId;

    public Report(User reporter, String contentId, String reportComment) {
        this.id = String.valueOf(idCounter++);
        this.reporter = reporter;
        this.contentId = contentId;
        this.reportComment = reportComment;
    }

    // Getters and setters
    public String getId() { return id; }
    //public void setId(String id) { this.id = id; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public String getContentId() { return contentId; }
    public void setContentId(String contentId) { this.contentId = contentId; }

    public String getReportComment() { return reportComment; }
    public void setReportComment(String reportComment) { this.reportComment = reportComment; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
} 