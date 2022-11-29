package songs;

import java.util.List;

public class Song {
  private String name;
  private String artist;
  private String user;
  private String url;
  private List<String> tags;

  public Song() {
    
  }
  public Song(String name, String artist, String user, String url, List<String> tags) {
    this.name = name;
    this.artist = artist;
    this.user = user;
    this.url = url;
    this.tags = tags;
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

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<String> getTags() {
    return this.tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", artist='" + getArtist() + "'" +
      ", user='" + getUser() + "'" +
      ", url='" + getUrl() + "'" +
      ", tags='" + getTags() + "'" +
      "}";
  }


}
