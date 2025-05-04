package controller;

import exceptions.ItemNotFoundException;
import model.*;

import java.util.ArrayList;

//Singleton class
public class ReportController {
    private static ReportController reportController;
    private ReportController() {
    }
    public static ReportController getReportController() {
        if(reportController == null)
            reportController = new ReportController();
        return reportController;
    }

    public Report createReport(User reporter, String contentId, String reportComment) {
        Report report = new Report(reporter, contentId, reportComment);
        Database.getDatabase().addReport(report);
        return report;
    }

    public ArrayList<Report> getReports() {
        return Database.getDatabase().getReports();
    }
    public Report getReportById(String reportId) throws ItemNotFoundException {
        for(Report report: getReports()){
            if(report.getId().equals(reportId))
                return report;
        }
        throw new ItemNotFoundException("Report not found");
    }
} 