package codecounter.counterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class testApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 加载FXML布局
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(testApp.class.getResource("test.fxml"));
            AnchorPane rootLayout = loader.load();

            // 设置Scene并显示
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("代码统计工具");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
