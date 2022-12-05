package examples;

import java.util.List;


public class App {

    public static void main(String[] args) throws Exception {

      SongSheetRepository repository = new SongSheetRepository();

      List<Song> songs = repository.readAll();
      for (Song song: songs) {
        System.out.println(song);
      } 

      // Song song = new Song("https://open.spotify.com/track/734dz1YaFITwawPpM25fSt",
      //     "Je veux",
      //     "Zaz",
      //     "andreainfufsm",
      //     "jazz, chanson, french");

      // //repository.create(song);
      // song.setTags("jazz, chanson");
      // repository.update("url","https://open.spotify.com/track/734dz1YaFITwawPpM25fSt", song);
      // repository.delete("url", "https://open.spotify.com/track/734dz1YaFITwawPpM25fSt");
     
    }
  
  

  
    
  }