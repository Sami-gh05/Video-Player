package model;

public enum SortOption {
    POPULAR("Popular"),
    VIEWED("Viewed");

    private final String sortOption;

    SortOption(String sortOption){
        this.sortOption = sortOption;
    }

    public String getSortOption() {
        return sortOption;
    }
}
