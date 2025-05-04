package model;

import java.util.ArrayList;

//Singleton
public class Database {
    private static Database database;
    private  ArrayList<Content> contents;
    private  ArrayList<Report> reports;
    private  ArrayList<User> users;
    private Admin admin;

    private Database(){
        this.contents = new ArrayList<Content>();
        users = new ArrayList<>();
        reports = new ArrayList<>();
    };
    private Database(Admin admin){
        this.contents = new ArrayList<Content>();
        users = new ArrayList<>();
        reports = new ArrayList<>();
        this.admin = admin;
    };

    public static Database getDatabase() {
        if(database == null)
            database = new Database();
        return database;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

        public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void addContent(Content content){
        this.contents.add(content);
    }
    public void removeContent(Content content){
        this.contents.remove(content);
    }
    public void addReport(Report report){
        this.reports.add(report);
    }
    public void removeReport(Report report){
        this.reports.remove(report);
    }
}
