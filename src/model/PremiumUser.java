package model;

import java.util.Date;

public class PremiumUser extends User{
    private Date PremiumEndDate;
    private String packageType;
    public PremiumUser(String username, String password, String fullName, String email, String phoneNumber, String profileCover, float credit, Date premiumEndDate, String packType) {
        super(username, password, fullName, email, phoneNumber, profileCover, credit, true);
        this.PremiumEndDate = premiumEndDate;
        this.packageType = packType;
    }
    public PremiumUser(NormalUser normalUser, Date premiumEndDate, String packType){
        super(normalUser.username, normalUser.password, normalUser.fullName, normalUser.email, normalUser.phoneNumber,
                normalUser.profileCover, normalUser.credit, true);
        this.PremiumEndDate = premiumEndDate;
        this.packageType = packType;
        this.setId(normalUser.getId());
        super.channel = normalUser.channel;
        super.playlists = normalUser.playlists;
        super.subscriptions = normalUser.subscriptions;
        super.favoriteCategories = normalUser.favoriteCategories;
    }


    public Date getPremiumEndDate() {
        return PremiumEndDate;
    }

    public void setPremiumEndDate(Date premiumEndDate) {
        PremiumEndDate = premiumEndDate;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}
