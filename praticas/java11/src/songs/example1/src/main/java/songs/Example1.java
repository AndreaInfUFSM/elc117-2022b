package songs;

import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Example1 {
  private final static String DATABASE = "mynosqldb";
  private final static String COLLECTION = "songs";

  public static void main(String[] args) {

    // Replace the uri string with your MongoDB deployment's connection string
    String uri = "mongodb+srv://student:vc64R0Ozq4lMKu4J@cluster0.6ueci3j.mongodb.net/?retryWrites=true&w=majority";

    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase(DATABASE);
    MongoCollection<Document> collection = database.getCollection(COLLECTION);

    // Document data = new Document();
    // data.append("artist", "O Rappa")
    //     .append("url", "https://open.spotify.com/track/19QGIdhhRCB303eGVUQyZM")
    //     .append("name", "Auto-reverse")
    //     .append("user", "andreainfufsm")
    //     .append("tags", Arrays.asList("rock", "fusion"));

    // collection.insertOne(data);

    FindIterable<Document> iter = collection.find();
    for (Document d : iter) {
      System.out.println(d);
    }
    // Same as:
    // collection.find().forEach(d -> System.out.println(d));

  }
}
