package songs;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

// See: https://www.mongodb.com/developer/languages/java/java-mapping-pojos/

public class SongCrudRepository {

  private final static String DATABASE = "mynosqldb";
  private final static String COLLECTION = "songs";
  private final static String URI = "mongodb+srv://student:vc64R0Ozq4lMKu4J@cluster0.6ueci3j.mongodb.net/?retryWrites=true&w=majority";

  private MongoCollection<Song> collection;

  public SongCrudRepository() {
    MongoClient mongoClient = MongoClients.create(URI);
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    MongoDatabase database = mongoClient.getDatabase(DATABASE).withCodecRegistry(pojoCodecRegistry);
    collection = database.getCollection(COLLECTION, Song.class);
  }

  public void create(Song song) {
    collection.insertOne(song);
  }

  public List<Song> readAll() {
    List<Song> result = new ArrayList<Song>();
    collection.find().into(result);
    return result;
  }

  public List<Song> readByUrl(String url) {
    List<Song> result = new ArrayList<Song>();
    Document filterByUrl = new Document("url", url);
    collection.find(filterByUrl).into(result);
    return result;
  }

  public void updateTagsByUrl(String url, List<String> tags) {
    Document filterByUrl = new Document("url", url);
    collection.updateOne(filterByUrl,
        new Document("$set", new Document("tags", tags)));

  }

  public void deleteByUrl(String url) {
    Document filterByUrl = new Document("url", url);
    collection.deleteOne(filterByUrl);

  }

  public void deleteAll() {
    collection.drop();
  }
}
