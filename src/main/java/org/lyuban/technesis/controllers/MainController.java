package org.lyuban.technesis.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.lyuban.technesis.DataBaceConnection.DataStore;
import org.lyuban.technesis.HelloApplication;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label noteCreationTime;

    @FXML
    private TextArea noteText;

    @FXML
    private VBox panelVBox;
    @FXML
    private Button noteButn;

    @FXML
    private Label windowHeader;
    private int selectedNoteIndex;
    private EditorController editorController = new EditorController();


    @FXML
    void initialize() {
        //Работа кнопки создания новой заявки
        addButton.setOnAction(event -> {
            createNewNote(event);
        });

        updateButton.setOnAction(event -> {
            createNewNote(event);
            fillFieldsInCreationForm(selectedNoteIndex);
        });

        //заполняю хранилище примерами заявок
        DataStore.addSomeNotes();
        DataStore.addSomeNotes();

        //добавляем данные из хранилища на правую панель в виде списка
        addNotesToVbox();

        //выводим список заявок в консоль
        System.out.println("selectedNoteIndex = " + selectedNoteIndex);
    }

    /**
     * Метод открывает окно с формой для создания новой заметки.
     *
     * @param event
     */
    private void createNewNote(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("note_creation_form.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 320);
            Stage stage = new Stage();
            stage.setTitle("Technesis: \"Форма создания заявки.\"");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод выводит заголовки заявкок из хранилища в область {@link ScrollPane}
     */
    private void addNotesToVbox() {
        //получаю размер хранилища (количество заявок)
        int size = DataStore.getNoteStore().size();
        int counter = 0;

        while (counter < size) {
            //создаю кнопку
            Button button = new Button(getNoteHeader(counter));
            //устанавливаю обработчик событий на кнопку
            int finalCounter = counter;
            button.setOnAction(el -> {
                selectedNoteIndex = Math.toIntExact(DataStore.getNoteStore().get(finalCounter).getId());
                System.out.println("selectedNoteIndex = " + selectedNoteIndex);
                setWindowHeader(finalCounter);
                setNoteTime(finalCounter);
                setNoteText(finalCounter);
            });
            //устанавливаю класс со стилями
            button.getStyleClass().add("noteButton");
            //создаю HBox
            HBox hBox = new HBox();
            //задаю центрирование дочерних элементов сверху и по центру
            hBox.setAlignment(Pos.TOP_CENTER);
            //задаю расширение кнопки в размер ширины VBox
            HBox.setHgrow(button, Priority.ALWAYS);
            //добавляю кнопку в HBox
            hBox.getChildren().add(button);
            //растягиваю кнопку на всю ширину HBox
            button.setMaxWidth(Double.MAX_VALUE);
            //задаю положение кнопки
            hBox.setAlignment(Pos.TOP_CENTER);
            //добавляю HBox на VBox
            panelVBox.getChildren().add(hBox);
            //задаю отступы между VBox
            panelVBox.setSpacing(5);

            counter++;

        }
    }

    /**
     * Метод устанавливает заголовок заметки в соответствующее поле.
     *
     * @param index индекс заметки.
     */
    private void setWindowHeader(int index) {
        windowHeader.setText(getNoteHeader(index));
    }

    private void setNoteText(int index) {
        noteText.setText(getNoteText(index).toString());
    }

    /**
     * Метод устанавливает время создания заявки в соответствующее поле.
     *
     * @param index индекс заметки.
     */
    private void setNoteTime(int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime time = getNoteTime(index);
        String noteTime = time.format(formatter);
        noteCreationTime.setText(noteTime);
    }

    /**
     * Метод получает заголовок заявки из хранилища по индексу.
     *
     * @param index индекс заявки в хранилище
     * @return объект {@link String}
     */
    private String getNoteHeader(int index) {
        return DataStore.getNoteStore().get(index).getHeader();
    }

    /**
     * Метод получает текст заявки из хранилища по индексу.
     *
     * @param index индекс заявки в хранилище
     * @return объект {@link StringBuilder}
     */
    private StringBuilder getNoteText(int index) {
        return DataStore.getNoteStore().get(index).getNoteText();
    }

    /**
     * Метод получает время создания завки из хранилища по индексу.
     *
     * @param index индекс заявки в хранилище
     * @return объект {@link LocalDateTime}
     */
    private LocalDateTime getNoteTime(int index) {
        return DataStore.getNoteStore().get(index).getDateTime();
    }

    private void fillFieldsInCreationForm(int index){
        String noteText = DataStore.getNoteStore().get(selectedNoteIndex).getNoteText().toString();
        TextField textField = editorController.getHeader();
        textField.setText(noteText);
        editorController.setHeader(textField);
    }
}

