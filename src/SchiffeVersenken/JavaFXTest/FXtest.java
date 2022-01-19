package SchiffeVersenken.JavaFXTest;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXtest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);

        //Image icon = new Image("D:\\Eigene Dateien\\Dokumente\\GitHub\\LearnJavaFX\\src\\main\\MushroomBoi.png");
        //stage.getIcons().add(icon);
        stage.setTitle("Java FX test");
        stage.setWidth(420);
        stage.setHeight(420);
        stage.setResizable(false);
        //stage.setX(50);
        //stage.setY(50);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("You cant escape unless you press F11");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("F11"));

        stage.setScene(scene);

        stage.show();
    }
}
