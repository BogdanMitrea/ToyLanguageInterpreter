package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui_Interpreter extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader((getClass().getResource("main-layout.fxml")));
        Scene scene = new Scene(mainLoader.load(), 800, 300);
        MainLayout ctrl= mainLoader.getController();
        ctrl.PopulateListView();
        stage.setTitle("A7");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
