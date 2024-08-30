


public class Movie {

    private String title;
    private String genre;
    private String movieId;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public Movie(String title, String genre, String movieId) {
		super();
		this.title = title;
		this.genre = genre;
		this.movieId = movieId;
	}


}