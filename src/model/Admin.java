package model;

//Singleton class
public class Admin extends Account{
    private static Admin admin;
    private Admin(){
        super();
    };

    public static Admin getAdmin() {
        if(admin == null)
            admin = new Admin();
        return admin;
    }
    public void initialize(String username, String password, String fullName, String email, String phoneNumber, String profileCover){
        super.username = username;
        super.password = password;
        super.fullName= fullName;
        super.email = email;
        super.phoneNumber = phoneNumber;
        super.profileCover = profileCover;
        super.isUser = false;
    }
}
