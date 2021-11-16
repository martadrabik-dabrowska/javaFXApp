package frontend.view;

import frontend.business.CompanyBD;
import frontend.business.EmployeeBD;
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
import org.springframework.beans.factory.annotation.Autowired;


public class AppMainView extends Application {

    private TableView<CompanyVO> companyTableView;

    private TableView<EmployeeVO> workersTableView;
    @Autowired
    private CompanyBD companyBD;
    @Autowired
    private EmployeeBD employeeBD;

    public AppMainView(){
        this.companyBD = new CompanyBD();
        this.employeeBD = new EmployeeBD();
    }



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

    private HBox createRadioBtnHBox(){
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

    private Pane getAddEmployeePane(){
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

    private EmployeeVO createNewEmployee(TextField firstName, TextField lastName, TextField position, TextField email){
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setFirstName(firstName.getText());
        employeeVO.setLastName(lastName.getText());
        employeeVO.setPosition(position.getText());
        employeeVO.setEmail(email.getText());
        employeeVO.setCompany(getSelectedCompany().getId());
        return employeeVO;
    }

    private CompanyVO getSelectedCompany(){


    }

    private void clearTextField(TextField ...textFields){
        for (TextField textField: textFields) {
            textField.clear();
        }
    }

    private void refreshDataEmployeesTable(){

    }








    private Pane getAddCompanyPane(){
        HBox companyHBox = new HBox();
        TextField name = new TextField();
        name.setPromptText("Name");
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField nip = new TextField();
        nip.setPromptText("Nip");
        companyHBox.getChildren().addAll(name, address, nip);

        Button addBtn = new Button("Add");
        addBtn.setOnAction(p-> {
            CompanyVO companyVO = createNewCompany(name, address, nip);
            companyBD.saveCompany(companyVO);
            clearTextField(name, address, nip);
            refreshDataCompanyTable();
        });
        VBox companyVBox = new VBox();
        companyVBox.getChildren().addAll(companyHBox, addBtn);
        return companyVBox;
    }

    private CompanyVO createNewCompany(TextField name, TextField address, TextField nip){
        CompanyVO companyVO = new CompanyVO();
        companyVO.setName(name.getText());
        companyVO.setAddress(address.getText());
        companyVO.setNip(nip.getText());
        return companyVO;
    }

    private void refreshDataCompanyTable(){

    }


    private Pane addDeleteBtnPane() {
        HBox deleteBtnHbox = new HBox();
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(p->removeAction());
        deleteBtnHbox.getChildren().add(deleteBtn);
        return deleteBtnHbox;
    }


    private void removeAction(){
        EmployeeVO selectedEmployee = getSelectedEmployee();
        if (selectedEmployee != null){
            employeeBD.remove(selectedEmployee);
            refreshDataEmployeesTable();
        }else {
            CompanyVO selectedCompany = getSelectedCompany();
            if (selectedCompany != null){
                companyBD.remove(selectedCompany);
                refreshDataCompanyTable();
            }
        }
    }
    private EmployeeVO getSelectedEmployee(){
        

    }


    private static void configureBorder(final Region region){
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-padding: 6;");
    }
}
