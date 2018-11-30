class Video implements Comparable<Video> {

    private long date;
    private String path;

    public Video(long date, String path) {
        this.date = date;
        this.path = path;
    }

    public long getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }

    public int compareTo(Video v2) {
        return ((int) (this.date - v2.getDate()));
    }

}