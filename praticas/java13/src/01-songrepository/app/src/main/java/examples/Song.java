package examples;

public class Song {
  private String url;
  private String name;
  private String artist;
  private String user;
  private String tags;

  public Song() {
    
  }

  public Song(String url, String name, String artist, String user, String tags) {
    this.url = url;
    this.name = name;
    this.artist = artist;
    this.user = user;
    this.tags = tags;
  }
  
  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getArtist() {
    return this.artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getTags() {
    return this.tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "{" +
      " url='" + getUrl() + "'" +
      ", name='" + getName() + "'" +
      ", artist='" + getArtist() + "'" +
      ", user='" + getUser() + "'" +
      ", tags='" + getTags() + "'" +
      "}";
  }


}