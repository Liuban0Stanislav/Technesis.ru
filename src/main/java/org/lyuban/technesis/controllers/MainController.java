package org.lyuban.technesis.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.lyuban.technesis.DataBaceConnection.DataStore;
import org.lyuban.technesis.HelloApplication;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ScrollPane scrollPane;

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

    @FXML
    private Label noteDelField;
    private int selectedNoteIndex = -1;



    @FXML
    void initialize() {
        //Работа кнопки создания новой заявки
        addButton.setOnAction(event -> {
            try {
                showNewNoteWindow("note_creation_form.fxml", "Technesis: \"Форма создания заявки.\"");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //Работа кнопки редактирования заявки
        updateButton.setOnAction(event -> {
                fillUpdateFields("note_creation_form.fxml", "Technesis: \"Форма создания заявки.\"");
        });

        deleteButton.setOnAction(event -> {
            deleteNote(selectedNoteIndex);
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
     * Метод выводит на экран новое окно из заданого fxlm файла.
     * В качетве параметров принимает имя FXML файла и его заголовок.
     * @param fxmlFileName имя файла находящегося в проекте
     * @param titleName заголовок окна
     * @return экземпляр fxmlLoader
     * @throws IOException
     */
    private FXMLLoader showNewNoteWindow(String fxmlFileName,
                                    String titleName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(), 450, 320);
        Stage stage = new Stage();
        stage.setTitle(titleName);
        stage.setScene(scene);
        stage.show();
        return fxmlLoader;
    }

    /**
     * Метод заполняет поля формы для редактирования заявки текстом выбранной заявки.
     * @param fxmlFileName имя файла находящегося в проекте
     * @param titleName заголовок окна
     */
    private void fillUpdateFields(String fxmlFileName,
                                  String titleName){
        EditorController controller;
        try {
            controller = showNewNoteWindow(fxmlFileName, titleName).getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String headerText = DataStore.getNoteStore().get(selectedNoteIndex).getHeader();
        String noteText = DataStore.getNoteStore().get(selectedNoteIndex).getNoteText().toString();
        controller.setHeader(headerText);
        controller.setNoteText(noteText);

    }

    /**
     * Метод выводит заголовки заявкок из хранилища в область {@link ScrollPane}
     */
    private void addNotesToVbox() {
        //очищаем все элементы VBox
        panelVBox.getChildren().clear();
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

    private void deleteNote(int selectedNoteIndex){
        if (selectedNoteIndex != -1) {
            DataStore.getNoteStore().remove(selectedNoteIndex);
            timedMessage(1000, "УДАЛЕНО");
            windowHeader.setText("");
            noteCreationTime.setText("");
            noteText.setText("");
            this.selectedNoteIndex = -1;
            System.out.println(DataStore.getNoteStore());
        }

    }


    private void timedMessage(int millis, String messageText){
        //устанавливаем прозрачность 100%
        noteDelField.setOpacity(1);
        //устанавливаем текст
        noteDelField.setText(messageText);

        //добавляем анимацию затухания
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), noteDelField);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    /**
     * Метод устанавливает заголовок заметки в соответствующее поле.
     *
     * @param index индекс заметки.
     */
    private void setWindowHeader(int index) {
        windowHeader.setText(getNoteHeader(index));
    }

    /**
     * Метод выводит текст выбранной завяки в соответсвтующее поле.
     * @param index индекс заявки в хранилище.
     */
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
}

