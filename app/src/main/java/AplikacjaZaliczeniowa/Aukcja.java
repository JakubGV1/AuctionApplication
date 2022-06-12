package AplikacjaZaliczeniowa;

import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

public class Aukcja {

    private UUID AuctionID;
    private UUID CreatorID;
    private String CreatorName;
    private SimpleStringProperty Tytul;
    private SimpleStringProperty Koszt;
    private SimpleStringProperty Opis;

    public Aukcja(UUID ID, String CreatorName, String Tytul, String Koszt, String Opis){
        this.AuctionID = UUID.randomUUID();
        this.CreatorName = CreatorName;
        this.CreatorID = ID;
        this.Tytul = new SimpleStringProperty(Tytul);
        this.Koszt = new SimpleStringProperty(Koszt);
        this.Opis = new SimpleStringProperty(Opis);
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public UUID getCreatorID() {
        return CreatorID;
    }

    public UUID getAuctionID() {
        return AuctionID;
    }

    public void setTytul(String Tytul){
        this.Tytul = new SimpleStringProperty(Tytul);
    }
    public void setKoszt(String Koszt){
        this.Koszt = new SimpleStringProperty(Koszt);
    }
    public void setOpis(String Opis){
        this.Opis = new SimpleStringProperty(Opis);
    }
    public String getTytul(){
        return Tytul.get();
    }
    public String getKoszt(){
        return Koszt.get();
    }
    public String getOpis(){
        return Opis.get();
    }


}
