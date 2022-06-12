package AplikacjaZaliczeniowa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class User {
    private UUID UserID;
    private String UserName;
    public ObservableList<Aukcja> UserAuctions = FXCollections.observableArrayList();

    public ObservableList<Aukcja> getUserAuctions() {
        return UserAuctions;
    }
    public User(String UserName) {
        this.UserName = UserName;
        UserID = UUID.randomUUID();
    }

    public String getUserName() {
        return UserName;
    }

    public UUID getUserID() {
        return UserID;
    }
}
