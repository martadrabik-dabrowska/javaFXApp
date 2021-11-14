package frontend.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AppMainView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("App");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setScene(createScene());
        primaryStage.show();


    }

    private Scene createScene() {
        StackPane stackPane = createStackPane();
        Scene scene = new Scene(stackPane, 800,800);
        //configureBorder(stackPane);
        return scene;
    }

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();
        VBox topVbox = addTopVBox();
        Pane deleteBtnPane = addDeleteBtnPane();





        return stackPane;
    }



    private VBox addTopVBox() {
        VBox topVbox = new VBox();
        HBox radioBtnHbox = createRadioBtnHBox();

        Pane addEmployeePane = getAddEmployeePane();
        Pane addCompanyPane = getAddCompanyPane();

        topVbox.getChildren().addAll(radioBtnHbox, addEmployeePane, addCompanyPane);
        return topVbox;
    }



    private Pane addDeleteBtnPane() {
    }

    private static void configureBorder(final Region region){
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-padding: 6;");
    }
}
