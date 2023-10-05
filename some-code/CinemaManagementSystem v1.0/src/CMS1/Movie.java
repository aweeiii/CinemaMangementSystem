package CMS1;

public class Movie {
    private String movieName;
    private String director;
    private String leadingRole;
    private String synopsis;
    private String duration;

    public Movie() {
    }

    public Movie(String movieName, String director, String leadingRole, String synopsis, String duration) {
        this.movieName = movieName;
        this.director = director;
        this.leadingRole = leadingRole;
        this.synopsis = synopsis;
        this.duration = duration;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLeadingRole() {
        return leadingRole;
    }

    public void setLeadingRole(String leadingRole) {
        this.leadingRole = leadingRole;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
