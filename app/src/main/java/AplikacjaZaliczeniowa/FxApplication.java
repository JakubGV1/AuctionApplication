package AplikacjaZaliczeniowa;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FxApplication extends Application {

    Stage window;
    Scene MenuGlowne, Aukcje, DAukcje, EAukcja, UserDetails;

    private final List<User> Lista = new ArrayList<>();

    User uzytkownik = new User("Uzytkownik");
    User test1 = new User("Test1");
    User test2 = new User("Test2");

    private final ObservableList<Aukcja> ObList = FXCollections.observableArrayList();
    private final TableView<Aukcja> table = new TableView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Lista.add(test1);
        Lista.add(test2);
        Lista.add(uzytkownik);

        //prep

        Aukcja n1 = new Aukcja(test2.getUserID(), test2.getUserName(), "Test aukcja 1", "20", "Opis ... . .. . .");
        Aukcja n2 = new Aukcja(test1.getUserID(), test1.getUserName(), "Test aukcja 2", "25", "Opis . .. . . ");
        Aukcja n3 = new Aukcja(test1.getUserID(), test1.getUserName(),"Test aukcja 3", "350", "Opis . .. . . ");

        ObList.add( n1);
        ObList.add( n2);
        ObList.add( n3);

        test1.UserAuctions.add(n2);
        test2.UserAuctions.add(n3);
        test1.UserAuctions.add(n1);


        window = primaryStage;
        /// Start
        Label labelstart = new Label("Wybierz opcje:");
        Button BAukcje = new Button("Przegladaj aukcje");
        Button BDOaukcje = new Button("Dodaj aukcje");

        BAukcje.setOnAction(e-> window.setScene(Aukcje));
        BDOaukcje.setOnAction(e->window.setScene(DAukcje));

        BAukcje.setMaxSize(400,200);
        BAukcje.setMinHeight(100);
        BDOaukcje.setMaxSize(400,200);
        BDOaukcje.setMinHeight(100);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(labelstart, BAukcje, BDOaukcje);
        MenuGlowne = new Scene(layout, 400, 400);

        // Przeglad Aukcji
        Button ButtonPowDoMenu = new Button("Powrot do Menu");
        ButtonPowDoMenu.setOnAction(e->window.setScene(MenuGlowne));
        Label LAukcje = new Label("Aukcje!");
        LAukcje.setStyle("-fx-font-weight: bold;");

        VBox layout2 = new VBox();

        TableColumn<Aukcja, String> tyt = new TableColumn<>("Tytul Aukcji");
        tyt.setCellValueFactory(new PropertyValueFactory<>("Tytul"));
        tyt.setMinWidth(300);

        TableColumn<Aukcja, String> Kosz = new TableColumn<>("Cena");
        Kosz.setCellValueFactory(new PropertyValueFactory<>("Koszt"));
        Kosz.setMinWidth(80);


        table.setItems(ObList);
        table.getColumns().add(tyt);
        table.getColumns().add(Kosz);


        layout2.setSpacing(5);
        layout2.setPadding(new Insets(10, 8, 0, 10));
        layout2.getChildren().addAll(LAukcje, table , ButtonPowDoMenu);
        Aukcje = new Scene(layout2, 400, 400);

        table.setOnMouseClicked(e-> {
         if(e.getClickCount() == 2) {
             Button Powrot = new Button("Powrot do listy aukcji");
             Powrot.setOnAction(f -> window.setScene(Aukcje));

             VBox layout4 = new VBox();

             TextArea OpisAuk = new TextArea(table.getSelectionModel().getSelectedItem().getOpis());
             OpisAuk.setEditable(false);
             OpisAuk.setMinHeight(100);
             HBox l1 = new HBox();
             HBox l2 = new HBox();
             TextField NazwaAuk = new TextField(table.getSelectionModel().getSelectedItem().getTytul());
             l1.getChildren().addAll(new Label("Tytul aukcji: "), NazwaAuk);
             NazwaAuk.setEditable(false);
             TextField KosztAuk = new TextField(table.getSelectionModel().getSelectedItem().getKoszt());
             l2.getChildren().addAll(new Label("Cena: "), KosztAuk);
             KosztAuk.setEditable(false);
             Label l3 = new Label(table.getSelectionModel().getSelectedItem().getCreatorName());
             l3.setStyle("-fx-background-color:rgba(85, 255, 68,0.7)");
             l3.setOnMouseClicked(g->{
                 VBox layoutDetails = new VBox();
                 Label LNazwaUzytk = new Label(table.getSelectionModel().getSelectedItem().getCreatorName());
                 Label In = new Label("Inne aukcje");

                 User thisUser = new User(null);
                 for(User i : Lista){
                     if(i.getUserID().toString().equals(table.getSelectionModel().getSelectedItem().getCreatorID().toString())){
                         thisUser = i;
                         break;
                     }
                 }

                 TableView<Aukcja> tableUser = new TableView<>();


                 TableColumn<Aukcja, String> tytdet = new TableColumn<>("Tytul Aukcji");
                 tytdet.setCellValueFactory(new PropertyValueFactory<>("Tytul"));
                 tytdet.setMinWidth(300);

                 TableColumn<Aukcja, String> koszdet = new TableColumn<>("Cena");
                 koszdet.setCellValueFactory(new PropertyValueFactory<>("Koszt"));
                 koszdet.setMinWidth(80);


                 tableUser.getColumns().add(tytdet);
                 tableUser.getColumns().add(koszdet);

                 tableUser.setItems(thisUser.getUserAuctions());
                 layoutDetails.getChildren().addAll(LNazwaUzytk, In, tableUser , Powrot);
                 UserDetails = new Scene(layoutDetails, 400, 400);
                 window.setScene(UserDetails);
             });

             if(table.getSelectionModel().getSelectedItem().getCreatorID() == uzytkownik.getUserID()){
                 Button Usun = new Button("Usun aukcje");
                 Usun.addEventHandler(ActionEvent.ACTION, (d)->{
                     ObList.removeIf(x->x.getCreatorID()==uzytkownik.getUserID() && x.getAuctionID() == table.getSelectionModel().getSelectedItem().getAuctionID());
                     uzytkownik.UserAuctions.removeIf(x->x.getCreatorID()==uzytkownik.getUserID() && x.getAuctionID() == table.getSelectionModel().getSelectedItem().getAuctionID());
                     window.setScene(Aukcje);
                 });
                 layout4.getChildren().addAll(l1, l2,l3, OpisAuk, Powrot, Usun);

             } else {
                 layout4.getChildren().addAll(l1, l2,l3, OpisAuk, Powrot);
             }
             EAukcja = new Scene(layout4, 400, 400);
             window.setScene(EAukcja);
         }
        });
        // DodajAukcje

        VBox layout3 = new VBox();
        Label LDodajAukcje = new Label("Panel dodawania aukcji");
        LDodajAukcje.setStyle("-fx-font-weight: bold;");
        Button ButtonPowDoMenu1 = new Button("Powrot do Menu");
        ButtonPowDoMenu1.setOnAction(e->window.setScene(MenuGlowne));
        Button DodajOferte = new Button("Dodaj");

        TextField TytulAukcji = new TextField();
        TextField Koszt = new TextField();
        TextField Opis = new TextField();
        VBox Tytul = new VBox(TytulAukcji);
        VBox VKoszt = new VBox(Koszt);
        VBox VOpis = new VBox(Opis);

        Label label1 = new Label("Tytul: ");
        Tytul.getChildren().add(new Label("Cena:"));
        VKoszt.getChildren().add(new Label("Opis:"));

        Opis.setPrefHeight(100);
        Opis.setAlignment(Pos.TOP_LEFT);

        DodajOferte.addEventHandler(ActionEvent.ACTION, (e)->{
            if(!TytulAukcji.getText().isEmpty() && !Koszt.getText().isEmpty() && Koszt.getText().matches("[0-9]*")){
                Aukcja NowaAukcja = new Aukcja(uzytkownik.getUserID(),uzytkownik.getUserName(), TytulAukcji.getText(), Koszt.getText(),Opis.getText());
                ObList.add(NowaAukcja);
                uzytkownik.UserAuctions.add(NowaAukcja);
                TytulAukcji.setText(null);
                Koszt.setText(null);
                Opis.setText(null);
                window.setScene(Aukcje);
            }
        });

        layout3.getChildren().addAll(LDodajAukcje, label1, Tytul, VKoszt, VOpis, DodajOferte, ButtonPowDoMenu1);
        DAukcje = new Scene(layout3, 400, 400);

        window.setScene(MenuGlowne);
        window.setTitle("Aplikacja aukcyjna");
        window.show();


    }


}
