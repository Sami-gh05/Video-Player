package model;

public enum PremiumSubPack {
    BRONZE(30, 5),
    SILVER(60, 9),
    GOLD(180, 14);

    private final int days;
    private final float price;

    PremiumSubPack(int days, float price) {
        this.days = days;
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public float getPrice() {
        return price;
    }


} 