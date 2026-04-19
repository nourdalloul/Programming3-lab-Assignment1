
package Assignment3;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.util.Random;

public class projectController {

    // الربط مع عناصر FXML
    @FXML private BorderPane rootPane;
    @FXML private TextField nameField, addressField, phoneField;
    @FXML private RadioButton singleRadio, marriedRadio, divorcedRadio;
    @FXML private DatePicker birthDatePicker;
    @FXML private ImageView imageView;
    @FXML private ListView<String> availableSkillsList, selectedSkillsList;
    @FXML private TextArea coverLetterArea;
    @FXML private Slider fontSizeSlider;
    @FXML private ColorPicker backgroundColorPicker;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        // 1. إضافة مهارات افتراضية
        availableSkillsList.getItems().addAll("Java", "JavaFX", "Python", "SQL", "Communication", "Problem Solving");

        // 2. شرط تاريخ الميلاد: تعطيل أي تاريخ بعد 1 يناير 2010
        birthDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate maxDate = LocalDate.of(2010, 1, 1);
                if (date.isAfter(maxDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // تلوين التواريخ المعطلة
                }
            }
        });

        // 3. السلايدر لتغيير حجم خط الـ TextArea في الوقت الفعلي
        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            coverLetterArea.setStyle("-fx-font-size: " + newValue.doubleValue() + "px;");
        });
    }

    // رفع الصورة الشخصية
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    // نقل المهارات لليمين
    @FXML
    private void moveToSelected() {
        String selected = availableSkillsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedSkillsList.getItems().add(selected);
            availableSkillsList.getItems().remove(selected);
        }
    }

    // نقل المهارات لليسار
    @FXML
    private void moveToAvailable() {
        String selected = selectedSkillsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableSkillsList.getItems().add(selected);
            selectedSkillsList.getItems().remove(selected);
        }
    }

    // رفع الرسالة التعريفية من ملف نصي
    @FXML
    private void handleUploadCoverLetter() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) content.append(line).append("\n");
                coverLetterArea.setText(content.toString());
            } catch (IOException e) {
                showAlert("Error", "Could not read file", Alert.AlertType.ERROR);
            }
        }
    }

    // تغيير لون الخلفية ً
    @FXML
    private void handleColorChange() {
        String color = Integer.toHexString(backgroundColorPicker.getValue().hashCode()).substring(0, 6);
        rootPane.setStyle("-fx-background-color: #" + color + ";");
    }

    // زر الحفظ مع التحقق من البيانات
    @FXML
    private void handleSave() {
        if (validateInput()) {
            String fileName = nameField.getText() + new Random().nextInt(1000) + ".txt";
            try (PrintWriter writer = new PrintWriter(new File(fileName))) {
                writer.println("Full Name: " + nameField.getText());
                writer.println("Address: " + addressField.getText());
                writer.println("Phone: " + phoneField.getText());
                writer.println("Birthdate: " + birthDatePicker.getValue());
                writer.println("Skills: " + selectedSkillsList.getItems());
                writer.println("Cover Letter Content:\n" + coverLetterArea.getText());
                
                showAlert("Success", "Application saved to: " + fileName, Alert.AlertType.INFORMATION);
                statusLabel.setText("Status: Saved successfully.");
            } catch (FileNotFoundException e) {
                showAlert("Error", "Could not save file", Alert.AlertType.ERROR);
            }
        }
    }

    //  التحقق من البيانات
    private boolean validateInput() {
        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            showAlert("Validation Error", "All personal information fields must be filled!", Alert.AlertType.WARNING);
            return false;
        }
        if (!singleRadio.isSelected() && !marriedRadio.isSelected() && !divorcedRadio.isSelected()) {
            showAlert("Validation Error", "Please select your marital status.", Alert.AlertType.WARNING);
            return false;
        }
        if (birthDatePicker.getValue() == null) {
            showAlert("Validation Error", "Please select your birthdate.", Alert.AlertType.WARNING);
            return false;
        }
        if (selectedSkillsList.getItems().isEmpty()) {
            showAlert("Validation Error", "Please select at least one skill.", Alert.AlertType.WARNING);
            return false;
        }
        if (coverLetterArea.getText().isEmpty()) {
            showAlert("Validation Error", "Cover letter cannot be empty.", Alert.AlertType.WARNING);
            return false;
        }
        if (imageView.getImage() == null) {
            showAlert("Validation Error", "Please upload a personal image.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @FXML private void handleExit() { Platform.exit(); }
    
    // تغيير نوع الخط لكل الواجهة
    @FXML
    private void changeFontFamily(javafx.event.ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        String family = item.getText();
        rootPane.setStyle(rootPane.getStyle() + "-fx-font-family: '" + family + "';");
    }

    // تغيير حجم الخط لكل الواجهة
    @FXML
    private void changeFontSize(javafx.event.ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        String size = item.getText().replace("px", "");
        rootPane.setStyle(rootPane.getStyle() + "-fx-font-size: " + size + "px;");
    }

    // تغيير ستايل الخط (Normal / Italic)
    @FXML
    private void changeFontStyle(javafx.event.ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        String style = item.getText().equalsIgnoreCase("Italic") ? "italic" : "normal";
        rootPane.setStyle(rootPane.getStyle() + "-fx-font-style: " + style + ";");
    }
    
    @FXML
    private void handleAbout() {
        showAlert("About System", "Job Application System\nPurpose: Applying for jobs\nDeveloper: Nour N Dalloul", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
