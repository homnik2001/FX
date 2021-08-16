package sample.view;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import sample.Main;
import sample.functions.Function;
import sample.functions.FunctionPoint;
import sample.functions.FunctionPointIndexOutOfBoundsException;
import sample.functions.InappropriateFunctionPointException;
import sample.model.utilityPoint;
import sample.util.DocUtil;
import sample.util.UtilityClassLoader;

import java.io.File;
import java.io.IOException;

public class PointsOverviewController {

    @FXML
    private TableView <utilityPoint> pointTable;
    @FXML
    private TableColumn<utilityPoint, String> xColumn;
    @FXML
    private TableColumn<utilityPoint, String> yColumn;
    @FXML
    private TextField xNew;
    @FXML
    private TextField yNew;
    @FXML
    private Label xLabel;
    @FXML
    private Label yLabel;
    @FXML
    private Button addPoint;
    @FXML
    private Button deletePoint;
    private UtilityClassLoader classLoader;
    private Main mainApp;
    private DocUtil doc;
    private File file = new File("temp");


    @FXML
    private void initialize() {
        // Инициализация таблицы точек с двумя столбцами.
        pointTable.setEditable(true);

        //set dynamically value from main link
        xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty());

        //set a format of column
        xColumn.setCellFactory(TextFieldTableCell.<utilityPoint> forTableColumn());
        yColumn.setCellFactory(TextFieldTableCell.<utilityPoint> forTableColumn());

    }
    public PointsOverviewController (){
        classLoader = new UtilityClassLoader(ClassLoader.getSystemClassLoader());
    }


    public void handleAdd ()throws InappropriateFunctionPointException{
        if (xNew.getText().isEmpty() || yNew.getText().isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.primaryStage);
            alert.setTitle("Wrong values");
            alert.setContentText("Coordinates should be numbers");
            alert.showAndWait();
        }
        else {
            try {

                doc.addPoint(new FunctionPoint(Double.parseDouble(xNew.getText()), Double.parseDouble(yNew.getText())));
                mainApp.setPointData(doc);
            } catch (InappropriateFunctionPointException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainApp.primaryStage);
                alert.setTitle("Wrong values");
                alert.setContentText("Coordinates should be numbers");
            }
        }
    }
    public void handleDelete (){
        if ( pointTable.getSelectionModel().getSelectedIndex() == -1 ){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.initOwner(mainApp.primaryStage);
            err.setTitle("Wrong");
            err.setContentText("Please, choose the point! ");
            err.showAndWait();
        }
        else {
            doc.deletePoint( pointTable.getSelectionModel().getSelectedIndex());
            mainApp.setPointData(doc);
        }

   
    }
    public void handleEdit (){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.primaryStage);
        alert.setTitle("Inappropriate");
        alert.setContentText( "Value could not exceed it is neighbour values");


        xColumn.setOnEditCommit((TableColumn.CellEditEvent<utilityPoint, String> event) -> {
            TablePosition<utilityPoint, String> pos = event.getTablePosition();

            String newXValue = event.getNewValue();

            int row = pos.getRow();
            utilityPoint point = event.getTableView().getItems().get(row);
            if (row == 0){
                utilityPoint pointnext = event.getTableView().getItems().get(row+1);
                if (Double.parseDouble( newXValue ) >= pointnext.getX() ){
                    alert.showAndWait();
                }
                else{
                    try {
                        System.out.println("hello world\n");
                        doc.setPointX(row ,Double.parseDouble(newXValue));
                        mainApp.setPointData(doc);
                    }
                    catch (InappropriateFunctionPointException e1){

                    }
                    catch(FunctionPointIndexOutOfBoundsException e2){

                    }


                }
            }
            else if ( mainApp.getPointData().size() == row + 1 ){
                utilityPoint pointprev = event.getTableView().getItems().get(row-1);
                 if (Double.parseDouble(newXValue) <= pointprev.getX()){
                     alert.showAndWait();
                 }
                 else{
                     try {
                         System.out.println("hello world\n");

                         doc.setPointX(row, Double.parseDouble(newXValue));
                         mainApp.setPointData(doc);
                     }
                     catch (InappropriateFunctionPointException e1){

                     }
                     catch (FunctionPointIndexOutOfBoundsException e2){

                     }

                     //point.setX(Double.parseDouble(newXValue));

                 }
            }
            else {
                utilityPoint pointprev = event.getTableView().getItems().get(row-1);
                utilityPoint pointnext = event.getTableView().getItems().get(row+1);
                double newXDValue = Double.parseDouble(newXValue);
                if (newXDValue >= pointnext.getX() || newXDValue <= pointprev.getX()){
                    alert.showAndWait();
                }
                else {
                    try {
                        System.out.println("hello world\n");
                        doc.setPointX(row, Double.parseDouble(newXValue));
                        mainApp.setPointData(doc);
                    }
                    catch (InappropriateFunctionPointException e1){

                    }
                    catch (FunctionPointIndexOutOfBoundsException e2){

                    }
                }

            }

        });
        yColumn.setOnEditCommit((TableColumn.CellEditEvent<utilityPoint, String> event) -> {
            TablePosition<utilityPoint, String> pos = event.getTablePosition();

            String newYValue = event.getNewValue();

            int row = pos.getRow();
            doc.setPointY(row , Double.parseDouble(newYValue));
            mainApp.setPointData(doc);
        });
    }
    public void handleNew (){
        mainApp.showPointsEditDialog();
    }
    public void menuItemSaveDocumentAsClicked() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
        file = fileChooser.showSaveDialog(mainApp.primaryStage);
       // System.out.println( file.getPath() );
        if (file != null)
        {
            doc.saveFunctionAs(file);
        }
    }
    public void handleTabulate () throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.primaryStage);
        alert.setTitle("Error");
        try {
            FileChooser fileChooser = new FileChooser();
            File fileT =fileChooser.showOpenDialog(mainApp.primaryStage);
            int pointIndex = fileT.getAbsolutePath().lastIndexOf(".");
            String absulutePath = fileT.getAbsolutePath().substring(0, pointIndex);

            Class clazz = classLoader.loadClass(absulutePath);
            Function function = (Function) clazz.newInstance();
            mainApp.showPointsEditDialog();
            doc.tabulateFunction(function, doc.getPointX(0), doc.getPointX(doc.getPointsCount() - 1), doc.getPointsCount());
            mainApp.setPointData(doc);
        }
        catch (ClassNotFoundException e){
            alert.setContentText( "ClassNotFoundException");
            alert.showAndWait();
        }
        catch (IllegalAccessException e1){
            alert.setContentText( "IlleagalAccesException");
            alert.showAndWait();
        }
        catch (InstantiationException e2){
            alert.setContentText( "InstantiationException");
            alert.showAndWait();
        }
    }
    public void menuItemOpenClicked (){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text", "*.txt"));
        file = fileChooser.showOpenDialog(mainApp.primaryStage);
        if(file != null){
            doc.loadFunction(file);
        }
        mainApp.clearPointData();
        mainApp.setPointData(doc);
    }
    public void menuItemSaveDocumentClicked() throws IOException
    {
        if (doc.getFileNameAssigned())
        {
            doc.saveFunction(file);
        }
        else
        {
            menuItemSaveDocumentAsClicked();
        }
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        pointTable.setItems(mainApp.getPointData());
    }
    public void setDoc ( DocUtil doc){
        this.doc = doc;
    }


}
