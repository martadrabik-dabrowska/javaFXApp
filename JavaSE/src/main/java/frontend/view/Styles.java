package frontend.view;

import javafx.scene.layout.Region;
import org.springframework.stereotype.Service;

@Service
public class Styles {

    void setBorderStyle(Region region) {
        region.setStyle(
                "-fx-background-color: lightgrey;"
                        + "-fx-padding: 20;");
    }

    void setTopVBoxStyle(Region region) {
        region.setStyle(
                "-fx-background-color: lavender;"
                        + "-fx-border-color: black;"
                        + "-fx-border-width: 1;"
                        + "-fx-padding: 10;");
    }

    void setVBoxStyle(Region region){
        region.setStyle(
                "-fx-border-width: 6;"
                        + "-fx-padding: 20px;");
    }

    void setAddBtnStyle(Region region){
        region.setStyle(
                "-fx-background-color: white;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-color: black;"
                        +"-fx-font-color: black;"
                        +"-fx-font-size: 15;");
    }

    void setDeleteBtnStyle(Region region){
        region.setStyle(
                "-fx-background-color: lightgrey;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-color: black;"
                        +"-fx-font-color: black;"
                        +"-fx-font-size: 15;");
    }

   void setRadioButtonStyle(Region region){
        region.setStyle(
                "-fx-padding: 0px 20px 0px 5px;"
                        + "-fx-font-size: 15;");
    }

     void getTextFieldStyle(Region region){
        region.setStyle(" -fx-padding: 5px;"
                + "-fx-border-insets: 5px;"
                + "-fx-background-insets: 5px;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-color: lightgrey;");
    }

}
