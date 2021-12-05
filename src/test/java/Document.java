import java.util.List;

public class Document {
    String username, salt, verifier;

    public Document(String username, String salt, String verifier) {
        this.username = username;
        this.salt = salt;
        this.verifier = verifier;
    }

    public static Document find(List<Document> list, String username) {
        for (Document d : list) {
            if (d.username.equals(username))
                return d;
        }
        return null;
    }
}
