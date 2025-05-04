package model;

public enum Quality {
    Q1080(1080),
    Q720(720),
    Q480(480),
    Q360(360);

    private final int resolution;

    Quality() {
        this.resolution = 360;
    }

    Quality(int resolution) {
        this.resolution = resolution;
    }

    public int getResolution() {
        return resolution;
    }
} 