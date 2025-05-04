package model;

public enum PasswordStrength {
    WEAK("weak"),
    MEDIUM("medium"),
    STRONG("Strong");

    private final String strength;

    PasswordStrength(String strength){
        this.strength = strength;
    }

    public String getStrength() {
        return strength;
    }
}
