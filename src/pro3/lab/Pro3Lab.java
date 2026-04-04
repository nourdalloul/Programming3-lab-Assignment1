
package pro3.lab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author ALAmiah
 */
public class Pro3Lab extends Application {
     
    BorderPane borderPane = new BorderPane ();
    List <Button> list = new ArrayList();
    Label lablel3 = new Label ();
    File file = new File("C:\\nour\\history.txt");
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //First Number
        Label lablel1 = new Label ();
        lablel1.setText("Number 1 :");
        TextField textField1 = new TextField();
        textField1.setFocusTraversable(false);//ما يعمل فوكس عال فيلد الا بس انا اضغط اما لو كان ترو بيعمل فوكس على طول
        textField1.setPromptText("Enter First Number");
        HBox hBox1 = new HBox (10,lablel1,textField1);
        hBox1.setAlignment(Pos.CENTER);
        
        //Second Number
        Label lablel2 = new Label ();
        lablel2.setText("Number 2 :");
        TextField textField2 = new TextField();
        textField2.setFocusTraversable(false);//ما يعمل فوكس عال فيلد الا بس انا اضغط اما لو كان ترو بيعمل فوكس على طول
        textField2.setPromptText("Enter Second Number");
        HBox hBox2 = new HBox (10,lablel2,textField2);
        hBox2.setAlignment(Pos.CENTER);
        
        //Operator
        Button add = new Button ("+");
        Button sub = new Button ("-");
        Button div = new Button ("/");
        Button mul = new Button ("*");
        HBox hBox3 = new HBox (10,add,sub,div,mul);
        hBox3.setAlignment(Pos.CENTER);
        
        list.add(sub); 
        list.add(add); 
        list.add(div); 
        list.add(mul);
        
        for (Button btn : list){
            btn.setPrefSize(70, 5);
        }
        
        //result
       
        lablel3.setText("Result :");
        
        Button clear = new Button ("Clear");
        clear.setPrefSize(90, 5);
        Button history = new Button ("History");
        history.setPrefSize(90, 5);
        HBox hBox4 = new HBox (10,clear,history);
        hBox4.setAlignment(Pos.CENTER);
        
        //history
        Label lablel4 = new Label ();
        lablel4.setText("History :");
        
        add.setOnAction(e->{
            float num1 = Float.parseFloat(textField1.getText());
            float num2 = Float.parseFloat(textField2.getText());
            float result =num1+num2;
            verefication(result);
            //write on file
            try (PrintWriter pr = new PrintWriter(new FileWriter(file, true))) {
                pr.println(num1 + " + " + num2 + " = " + result);
                pr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        sub.setOnAction(e->{
            float num1 = Float.parseFloat(textField1.getText());
            float num2 = Float.parseFloat(textField2.getText());
            float result =num1-num2;
            verefication(result);
            //write on file
            try (PrintWriter pr = new PrintWriter(new FileWriter(file, true))) {
                pr.println(num1 + " - " + num2 + " = " + result);
                pr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        mul.setOnAction(e->{
            float num1 = Float.parseFloat(textField1.getText());
            float num2 = Float.parseFloat(textField2.getText());
            float result =num1*num2;
            verefication(result);
            //write on file
            try (PrintWriter pr = new PrintWriter(new FileWriter(file, true))) {
                pr.println(num1 + " *" + num2 + " = " + result);
                pr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        div.setOnAction(e->{
            float num1 = Float.parseFloat(textField1.getText());
            float num2 = Float.parseFloat(textField2.getText());
            if (num2==0 ){
               lablel3.setText("Error , Can not devide By zero ");
               textField1.setText(" ");
               textField2.setText(" ");
               return;
            }
            float result =num1/num2;
            verefication(result);
            //write on file
            try (PrintWriter pr = new PrintWriter(new FileWriter(file, true))) {
                pr.println(num1 + " / " + num2 + " = " + result);
                pr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        clear.setOnAction(e->{
          textField1.setText(" ");
          textField2.setText(" ");
          lablel3.setText("Result : ");
          lablel4.setText("History : ");
          // مسح محتوى الملف
         try (PrintWriter pr = new PrintWriter(file)) {
             // مجرد فتح الملف بهذه الطريقة يمسح كل المحتوى
         }catch (IOException ex) {
             ex.printStackTrace();
         }
        });
        
        history.setOnAction(e -> {
            new Thread(() -> {
               StringBuilder historyText = new StringBuilder();
               try (Scanner sc = new Scanner(file)) {
                   while (sc.hasNextLine()) {
                       historyText.append(sc.nextLine()).append("\n");
                   }
               } catch (IOException ex) {
                  ex.printStackTrace();
               }

               String finalText = historyText.toString();
               javafx.application.Platform.runLater(() -> {
                    lablel4.setText("History :\n" + finalText);
               });
           }).start();
       });
        
        
        VBox vBox = new VBox (15,hBox1,hBox2,hBox3,lablel3,hBox4,lablel4);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);
        
        
        borderPane.setCenter(vBox);
        Scene scene = new Scene (borderPane,450,430);
        stage.setScene(scene);
        stage.setTitle("Java FX Calculater with Hisrory");
        stage.show();
        stage.setAlwaysOnTop(true);
    }
    
    private void verefication(float result){
        if(result == (int) result){
             lablel3.setText("Result : " +String.valueOf((int) result));
        }else{
            lablel3.setText("Result : " +String.valueOf(result));
        }
    }
}
