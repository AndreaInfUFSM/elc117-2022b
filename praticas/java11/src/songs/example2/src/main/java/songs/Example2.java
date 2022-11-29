package songs;

import java.util.Arrays;

public class Example2 {

  public static void main(String[] args) {

    SongCrudRepository repo = new SongCrudRepository();

    Song s = new Song("J't'emmène au vent",
                      "Louise Attaque",
                      "andreainfufsm",
                      "https://open.spotify.com/track/0Wr98MVkENZXddiLB3bPb0",
                      Arrays.asList("rock", "french", "folk"));

    
    // Insert the song into the database
    // repo.create(s);

    // Read all songs from the database into local memory and print their tags
    for (Song item : repo.readAll()) {
      System.out.println(item.getTags());
    }

    // Short code to read all songs and print their attributes
    // repo.readAll().stream().forEach(song -> System.out.println(song));

    // Update tags of a single song
    repo.updateTagsByUrl("https://open.spotify.com/track/0Wr98MVkENZXddiLB3bPb0", Arrays.asList("notmpb"));
    repo.readAll().stream().forEach(song -> System.out.println(song));

    // repo.deleteAll();
    // repo.deleteByUrl("https://open.spotify.com/track/0Wr98MVkENZXddiLB3bPb0?si=0918743e3dd14380");
    
  }
}
