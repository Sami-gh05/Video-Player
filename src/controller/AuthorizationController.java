package controller;

import model.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

//Singleton class
public class AuthorizationController {
    //regex
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^(\\+98|0)?9\\d{9}$";
    private static final String WEAK_PASSWORD_REGEX = "^(?:[a-zA-Z]{6,}|\\d{6,})$";
    private static final String MEDIUM_PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
    private static final String STRONG_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    private static AuthorizationController authorizationController;
    private User loggedInUser; //current loggedIn user (Session)
    private Admin loggedInAdmin;
    private String message;
    private AuthorizationController() {
    }
    public static AuthorizationController getAuthorizationController() {
        if(authorizationController == null)
            authorizationController = new AuthorizationController();
        return authorizationController;
    }

    public void signUp(String username, String password, String fullName, String email, String phoneNumber, String profileCover, ArrayList<Category> favoriteCategories) {
        Database database = Database.getDatabase();
        if(loggedInUser != null || loggedInAdmin != null)
            logout();

        String usernameValidation = isValidUsername(username);
        String phoneNumberValidation = isValidPhoneNumber(phoneNumber);
        String emailValidation = isValidEmail(email);
        if(usernameValidation.equals("VALID")){
            if(emailValidation.equals("VALID")){
                if(phoneNumberValidation.equals("VALID")){
                    if(database.getUsers().isEmpty() && database.getAdmin() == null){
                        Admin.getAdmin().initialize(username, password, fullName, email, phoneNumber, profileCover);
                        database.setAdmin(Admin.getAdmin());
                        loggedInAdmin = database.getAdmin();
                        setMessage("You have signed up successfully as admin! | Password strength: " +  confirmPasswordStrength(password).getStrength());
                    } else{
                        //at first, all users are normal
                        loggedInUser = new NormalUser(username, password, fullName, email, phoneNumber, profileCover, 0.0f);
                        PlaylistController.getPlaylistController().ensureDefaultPlaylists(loggedInUser);
                        NormalUserController.getNormalUserController().setFavoriteCategory(favoriteCategories);
                        database.addUser(loggedInUser);
                        setMessage("You have signed up successfully as normal user! | Password strength: " +  confirmPasswordStrength(password).getStrength());
                    }
                } else
                    setMessage(phoneNumberValidation);
            } else
                setMessage(emailValidation);
        } else
            setMessage(usernameValidation);
    }
    public Account login(String username, String password) throws InvalidCredentialsException {
        Database database = Database.getDatabase();
        if(loggedInUser != null || loggedInAdmin != null)
            logout();

        if(database.getAdmin().getUsername().equals(username)){
            if(database.getAdmin().getPassword().equals(password)){
                loggedInAdmin = database.getAdmin();
                setMessage("You have logged in successfully as admin!");
                return loggedInAdmin;
            }
            else throw new InvalidCredentialsException("Wrong password");
        }
        else{
            for(User user: database.getUsers()) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)){
                        if(!user.getIsBanned()){
                            loggedInUser = user;
                            if(loggedInUser instanceof NormalUser){
                                setMessage("You have logged in successfully as normal user!");
                                return loggedInUser;
                            }
                            else{
                                //check whether the premium package is finished or not
                                setMessage("You have logged in successfully as premium user!");
                                PremiumUser tempUser = (PremiumUser) loggedInUser;
                                Date currentDate = new Date();
                                if(tempUser.getPremiumEndDate().before(currentDate)){ //it means the package is finished
                                    //converting premium user into normal
                                    NormalUser normalUser = new NormalUser(tempUser);
                                    database.removeUser(tempUser);
                                    database.addUser(normalUser);
                                    loggedInUser = normalUser;
                                    setMessage("You have logged in successfully as normal user!");
                                }
                                return loggedInUser;
                            }
                        } else throw new BannedException("This user is banned by admin");
                    } else throw new InvalidCredentialsException("Wrong password");
                }
            }
        }
        throw new ItemNotFoundException("Username not found");
    }

    public void logout(){
        loggedInUser = null;
        loggedInAdmin = null;
    }

    public String isValidUsername(String username) {
        for(User user: Database.getDatabase().getUsers()){
            if(user.getUsername().equals(username)){
                return  "This username already exists!";
            }
        }
        return "VALID";
    }
    public  String isValidEmail(String email) {
        boolean validation = Pattern.matches(EMAIL_REGEX, email);
        if(validation)
            return "VALID";
        else
            return "The email format is invalid. Try again.";
    }
    public  String isValidPhoneNumber(String phoneNumber){
        boolean validation = Pattern.matches(PHONE_REGEX, phoneNumber);
        if(validation)
            return "VALID";
        else
            return "The phone number format is invalid. Try again.";
    }

    public PasswordStrength confirmPasswordStrength(String password) {
        if(Pattern.matches(STRONG_PASSWORD_REGEX, password))
            return PasswordStrength.STRONG;
        else if(Pattern.matches(MEDIUM_PASSWORD_REGEX, password))
            return PasswordStrength.MEDIUM;
        else
            return PasswordStrength.WEAK;
    }

    public User getCurrentUser(){
        return loggedInUser;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}