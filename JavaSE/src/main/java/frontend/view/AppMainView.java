package frontend.view;

import frontend.model.CompanyVO;
import frontend.model.EmployeeVO;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class AppMainView extends Application {

    private TableView<CompanyVO> companyTableView;

    private TableView<EmployeeVO> workersTableView;



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
        //configureBorder(stackPane);
        return new Scene(stackPane, 800,800);
    }

    private StackPane createStackPane() {
        StackPane stackPane = new StackPane();
        VBox topVbox = addTopVBox();
        Pane deleteBtnPane = addDeleteBtnPane();

        TextField searchTextField = getSearchTextField();
        companyTableView = getCompanyTableView();
        workersTableView = getWorkersTableView();

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(topVbox,deleteBtnPane, searchTextField, companyTableView, workersTableView);
        stackPane.getChildren().add(vBox);
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

    public HBox createRadioBtnHBox(){
        VBox addingNewItemVBox = new VBox();

        HBox radioBtnHbox = new HBox();
        ToggleGroup radioBtnToggleGroup = new ToggleGroup();

        RadioButton employeeRadioBtn = new RadioButton("Employee");
        employeeRadioBtn.setToggleGroup(radioBtnToggleGroup);

        RadioButton companyRadioBtn = new RadioButton("Company");
        companyRadioBtn.setToggleGroup(radioBtnToggleGroup);
        companyRadioBtn.setSelected(true);

        radioBtnHbox.getChildren().addAll(employeeRadioBtn, companyRadioBtn);


        Pane addEmployeePane = getAddEmployeePane();
        Pane addCompanyPane = getAddCompanyPane();

        radioBtnToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton radioButton = (RadioButton) radioBtnToggleGroup.getSelectedToggle();
                if (radioButton != null){
                    String selectedRadioBtnName = radioButton.getText();
                    if (selectedRadioBtnName.equals("Employee")){
                        addEmployeePane.setVisible(true);
                        addEmployeePane.setManaged(true);
                        addCompanyPane.setVisible(false);
                        addCompanyPane.setManaged(false);
                    }else if (selectedRadioBtnName.equals("Company")){
                        addCompanyPane.setVisible(true);
                        addCompanyPane.setManaged(true);
                        addEmployeePane.setVisible(false);
                        addEmployeePane.setManaged(false);
                    }
                }
            }
        });

        radioBtnHbox.getChildren().addAll(employeeRadioBtn, companyRadioBtn);
        addingNewItemVBox.getChildren().addAll(radioBtnHbox, addEmployeePane, addCompanyPane);
        return radioBtnHbox;
    }

    public Pane getAddEmployeePane(){
        HBox employeeHBox = new HBox();
        TextField firstName = new TextField();
        firstName.setPromptText("First name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last name");
        TextField position = new TextField();
        position.setPromptText("Position");
        TextField email = new TextField();
        email.setPromptText("Email");
        employeeHBox.getChildren().addAll(firstName, lastName, position, email);

        Button addBtn = new Button("Add");
        addBtn.setOnAction(p->{
            EmployeeVO employeeVO = createNewEmployee(firstName, lastName, position, email);
            employeeBD.saveEmployee(employeeVO);
            clearTextField(firstName, lastName, position, email);
            refreshDataEmployeesTable();
        });
        VBox employeeVBox = new VBox();
        employeeVBox.getChildren().addAll(employeeHBox,addBtn);
        return employeeVBox;

    }




    public Pane getAddCompanyPane(){

    }


    public Pane addDeleteBtnPane() {
    }

    private static void configureBorder(final Region region){
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-padding: 6;");
    }
}
