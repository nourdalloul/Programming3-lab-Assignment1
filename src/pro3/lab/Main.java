
package Assignment3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // تحميل ملف الواجهة - تأكدي من كتابة الاسم الصحيح للملف مع الامتداد
        Parent root = FXMLLoader.load(getClass().getResource("sceneBuilderFile.fxml"));
        
        // ضبط عنوان النافذة
        primaryStage.setTitle("Job Application System");
        
        // إنشاء المشهد (Scene) مع تحديد الحجم الابتدائي
        Scene scene = new Scene(root, 950, 750);
        
        // ربط المشهد بالنافذة وإظهارها
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}