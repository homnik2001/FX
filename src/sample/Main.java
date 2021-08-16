package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.functions.ArrayTabulatedFunction;
import sample.functions.basic.Cos;
import sample.model.utilityPoint;
import sample.util.DocUtil;
import sample.view.PointsEditDialogController;
import sample.view.PointsOverviewController;
import java.io.IOException;
import  java.util.Comparator;

public class Main extends Application {
    public Stage primaryStage;
    public DocUtil mainDoc = new DocUtil() ;
    public ObservableList<utilityPoint> pointData = FXCollections.observableArrayList();
    public Main (){
        pointData.add(new utilityPoint(0 , 0));
        pointData.add(new utilityPoint(1 , 0));
        pointData.add(new utilityPoint(2 , 0));
        pointData.add(new utilityPoint(3 , 0));
        pointData.add(new utilityPoint(4 , 0));
        pointData.add(new utilityPoint(5 , 0));
        pointData.add(new utilityPoint(6 , 0));
        pointData.add(new utilityPoint(7 , 0));
        pointData.add(new utilityPoint(8 , 0));
        pointData.add(new utilityPoint(9 , 0));
        pointData.add(new utilityPoint(10, 0));

    }



    public ObservableList<utilityPoint> getPointData (){
        return pointData;
    }
    public void clearPointData(){
        pointData.clear();
    }
    public void setPointData(DocUtil fnc){
        clearPointData();
        for(int i = 0; i < fnc.getPointsCount();i++)
        {
            pointData.add(new utilityPoint(fnc.getPoint(i)));
        }
        fnc.printTabulated();
        System.out.println('\n');
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tabulated Functions");
        showPointOverview();
    }

    public void showPointOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/sample/view/PointsOverview.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            PointsOverviewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDoc(mainDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPointsEditDialog () {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/PointsEditDialog.fxml"));

            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Function parameters");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            Platform.setImplicitExit(false);

            /////////
            PointsEditDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDoc(mainDoc);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {

        launch(args);

    }
}
