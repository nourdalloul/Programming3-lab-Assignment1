
package pro3.lab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class assignment2 extends Application implements EventHandler<ActionEvent>{

    ComboBox<String> id;
    TextField name;
    RadioButton male;
    RadioButton female;
    ToggleGroup tg,tg2;
    ListView <String> preferdPL ;
    ListView <String> selectedPL;
    String [] pl = {"Java","Paython","JS","HTML","CSS","PHP","Scala"};
    Button save;
    Button arrow;
    CheckBox c1,c2,c3;
    RadioButton r1,r2,r3;
    ComboBox <Integer> cb;
    ColorPicker cp;
    ListView <String> studentList;
    BorderPane root;
    Label titel;
    Label message;
    ArrayList<Student> students = new ArrayList<>();
     // مصفوفة لتخزين العناصر اللي المستخدم ضغط عليها
    private List<String> clickedItems = new ArrayList<>();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        id = new ComboBox();
        id.setPromptText("Select Id");
        id.setPrefWidth(200);
        
        name = new TextField();
        name.setPromptText("Enter Student Name");
        name.setPrefWidth(200);
        
        male = new RadioButton("Male");
        female = new RadioButton("female");
        tg= new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);
        
        ObservableList <String> ol = FXCollections.observableArrayList(pl);
        preferdPL = new ListView(ol);
        selectedPL = new ListView();
        preferdPL.setPrefSize(150,120 );
        selectedPL.setPrefSize(150,120 );
        preferdPL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        arrow = new Button("..");
        save = new Button("save");
        save.setPrefWidth(100 );
        message = new Label(" ");
        message.setStyle("-fx-text-fill :red ;");
        
        
        //Design Controls
        c1= new CheckBox("Normal");
        c2= new CheckBox("Bold");
        c3= new CheckBox("Italic");
        
        r1= new RadioButton("Red");
        r2= new RadioButton("Green");
        r3= new RadioButton("Blue");
        
        tg2= new ToggleGroup();
        r1.setToggleGroup(tg2);
        r2.setToggleGroup(tg2);
        r3.setToggleGroup(tg2);
        
        cb = new ComboBox();
        cb.getItems().addAll(5,10,15,20);
        cb.setPrefWidth(100 );
        cb.setValue(10);
        
        cp = new ColorPicker();
        cp.setValue(Color.WHITE);
       
        
        //right side list
        studentList = new ListView();
        studentList.setPrefSize(250,350 );
        
        //left side panel
        Label designTitle = new Label("Design Tools");
        
        VBox leftBox = new VBox(12);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPadding(new Insets(20));
        leftBox.setPrefWidth(180);
        
        leftBox.getChildren().addAll(
            designTitle,
            new Label("Style"),
              c1,c2,c3,
            new Label("Color"),
              r1,r2,r3,
            new Label("Font Size"),
              cb,
            new Label("Custom Color"),
              cp
       );
        
        
        //Center Panel
        titel = new Label("Student Regestration System");
        
        HBox genderBox = new HBox(15,male,female);
        genderBox.setAlignment(Pos.CENTER_LEFT);
        
        HBox plBox = new HBox(15,preferdPL,arrow,selectedPL);
        genderBox.setAlignment(Pos.CENTER_LEFT);
        
        Button delete = new Button("Delete");
        
        HBox btnBox = new HBox(15,save,delete);
        btnBox.setAlignment(Pos.CENTER_LEFT);
        
        GridPane formGridPane = new GridPane();
        formGridPane.setHgap(15);
        formGridPane.setVgap(18);
        formGridPane.setAlignment(Pos.TOP_LEFT);
        formGridPane.setPadding(new Insets(20));
        
        formGridPane.add(titel, 0, 0,2,1);//2  تعني خدلي العمودين وال 1 تعني خدلي صف واحد
        formGridPane.add(new Label("ID"), 0, 1);
        formGridPane.add(id, 1, 1);
        formGridPane.add(new Label("Name"), 0, 2);
        formGridPane.add(name, 1, 2);
        formGridPane.add(new Label("Gender"), 0, 3);
        formGridPane.add(genderBox, 1, 3);
        formGridPane.add(new Label("PL"), 0, 4);
        formGridPane.add(plBox, 1, 4);
        formGridPane.add(btnBox, 1, 5);
         //formGridPane.add(delete, 1, 5);
        formGridPane.add(message, 1, 6);
       
        
        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.getChildren().add(formGridPane);
        
        
        //right panel
        Label savedTitle = new Label("Saved Student");
        VBox rightBox = new VBox(15);
        rightBox.setPadding(new Insets(20));
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.getChildren().addAll(savedTitle,studentList);
        rightBox.setPrefWidth(280);
        
        //Main layout 
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setLeft(leftBox);
        root.setCenter(centerBox);
        root.setRight(rightBox);
        
        //IDs
        leftBox.setId("left-panel");
        rightBox.setId("right-panel");
        centerBox.setId("center-panel");
        save.setId("save-btn");
        formGridPane.setId("form-grid");
        designTitle.setId("section-titel");
        titel.setId("main-titel");
        savedTitle.setId("section-titel");
        delete.setId("delete-btn");
        
        //Registering an Events
        
        c2.setOnAction(this);
        c3.setOnAction(this);
        r1.setOnAction(this);
        r2.setOnAction(this);
        r3.setOnAction(this);
        cb.setOnAction(this);
        cp.setOnAction(this);
        arrow.setOnAction(this);
        save.setOnAction(this);
        id.setOnAction(this);
        
        
         // كل مرة يضغط المستخدم على عنصر، نخزن العنصر في clickedItems
        preferdPL.setOnMouseClicked(event -> {
            String selected = preferdPL.getSelectionModel().getSelectedItem();
            if (selected != null && !clickedItems.contains(selected)) {
                clickedItems.add(selected);
            }
        });

        // عند الضغط على الزر، ننسخ كل العناصر المخزنة في clickedItems
        arrow.setOnAction(e -> {
              selectedPL.getItems().addAll(clickedItems);
              preferdPL.getItems().removeAll(clickedItems);
              clickedItems.clear(); // بعد النسخ نفضي المصفوفة
        });
        
        // Read From File
        readFromFile();
        
        delete.setOnAction(e -> {
            String selectedId = id.getValue();
            if(selectedId != null){
               students.removeIf(s -> s.getId().equals(selectedId));
               studentList.getItems().removeIf(item -> item.contains(selectedId));
               id.getItems().remove(selectedId);
               clear();
           }
       });
        
        
        //Scene
        Scene scene = new Scene(root,1000,500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Student Regestration App");
        stage.setScene(scene);
        //stage.setAlwaysOnTop(true);
        stage.show();
        stage.setOnCloseRequest(e->{
            saveToFile();
        });
    }   
        
    public boolean validate(){
        if(name.getText().isBlank() ||tg.getSelectedToggle() == null){
            return false;
        }
        return true;
    }
    
    public void clear(){
        name.setText("");
        male.setSelected(false);
        female.setSelected(false);
        selectedPL.getItems().clear();
        preferdPL.getItems().setAll(pl);
        message.setText("");
    }
    
    public void saveToFile(){
        try(ObjectOutputStream oos =new ObjectOutputStream(
                new FileOutputStream("C:\\nour\\students.dat"))){
            oos.writeObject(students);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void readFromFile() throws ClassNotFoundException{
        File f = new File("C:\\nour\\students.dat");
        if(!f.exists()){
            System.out.println("No File yet");
            return;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            students = (ArrayList<Student>)ois.readObject();
            for(Student s:students){
                studentList.getItems().add(s.toString());
                id.getItems().add(s.getId());
            }
            int stdId = Integer.parseInt(students.getLast().getId().substring(5));
            Student.counter = stdId+1;
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    @Override
      public void handle(ActionEvent t) {
        String backgroundColor = "white";
        String gender ="";
        if(r1.isSelected()){
            backgroundColor="red";
        }
        if(r2.isSelected()){
            backgroundColor="green";
        }
        if(r3.isSelected()){
            backgroundColor="blue";
        }
        if(t.getSource() == cp){
            String color = cp.getValue().toString().substring(2,8);
            save.setStyle("-fx-background-color:#"+color);
        }
        if(t.getSource() == arrow){
            ObservableList<String> ol = FXCollections.observableArrayList(preferdPL.getSelectionModel().getSelectedItems());
            preferdPL.getItems().removeAll(ol);
            selectedPL.getItems().addAll(ol);
        }
        if(male.isSelected()){
            gender="male";
        }
        if(female.isSelected()){
            gender = "female";
        }
        if(t.getSource() == save){
            if(validate()){
                Student s =new Student(name.getText(), gender);
                s.setPl(new ArrayList<>(selectedPL.getItems()));
                students.add(s);
                studentList.getItems().add(s.toString());
                id.getItems().add(s.getId());
                clear();
            }else{
                message.setText("Name and gender are required!");
            }
       }
        root.setStyle("-fx-font-weight:"+(c2.isSelected()?"bold":"normal") +";"+
                        "-fx-font-style:"+(c3.isSelected()?"italic":"normal")+";"+
                        "-fx-background-color:"+backgroundColor+";"+
                        "-fx-font-size:"+cb.getValue()+"px;");
        
        if(t.getSource()== id){
            String stdId = id.getValue();
            for(Student s: students){
               if(stdId.equals( s.getId())){
                   name.setText(s.getName());
                   if( s.getGender().equals("male")){
                       male.setSelected(true);
                   }else{
                       female.setSelected(true);
                   }
                   selectedPL.getItems().clear();
                   selectedPL.getItems().addAll(s.getPl());
                   preferdPL.getItems().removeAll(s.getPl());
               }
            }
        }
      
    }
    
  }
    

