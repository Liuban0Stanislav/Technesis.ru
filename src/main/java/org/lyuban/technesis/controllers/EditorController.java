package org.lyuban.technesis.controllers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.lyuban.technesis.DataBaceConnection.DataStore;
import org.lyuban.technesis.models.Note;
import javafx.stage.Stage;
import org.lyuban.technesis.service.Service;

public class EditorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button canselButn;

    @FXML
    private Label dateTime;

    @FXML
    private Label warning;

    @FXML
    private TextField header;

    @FXML
    private TextArea noteText;

    @FXML
    private Button okButn;
    private MainController mainController;

    public void setOkButtonListener(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    void initialize() {
        ///получаем текущее время
        extractCurrentTime();

        //Работа кнопки "ок"
        okButn.setOnAction(event -> {
            newNoteCreate();
            System.out.println("Содержание хранилища:\n" + DataStore.getNoteStore());
        });

        //Работа кнопки "Отмена"
        canselButn.setOnAction(actionEvent -> closeWindow(canselButn));
    }

    /**
     * Метод создает новую заявку в хранилище.
     * <ul> В начале метод получает текст и время из полей:
     * <li>{@link EditorController#header}</li>
     * <li>{@link EditorController#dateTime}</li>
     * <li>{@link EditorController#noteText}</li>
     * </ul>
     * Далее, проводится проверка, не пусты ли эти поля, т.к. создавать пустую заявку нельзя.
     * В случае если и заголовок и текст заявки заполнены, то она добавится в хранилище.
     * Если нет, то пользователь увидит в тоже окне уведомление.
     * <br>В случае успешного создания завки окно создания будет закрыто.
     * Метод возвращает true если завяка создана, в противном случа - false</br>
     *
     * @return true или false
     */
    private boolean newNoteCreate() {
        //получаю текст из Заголовка
        String header = this.header.getText();

        //дата и время которые будут выводиться в форме создания заявки
        extractCurrentTime();

        //сохраняю текущее время в своем исходном формате в лист
        LocalDateTime dateTime = LocalDateTime.now();

        //получаю текст заявки
        StringBuilder noteText = new StringBuilder(this.noteText.getText());

        //проверка - заявку нельзя создать с пустым заголовком или текстом.
        Note newNote;
        if (!header.isEmpty() &&
                !header.isBlank() &&
                !noteText.isEmpty() &&
                header.length() >= 5 &&
                noteText.length() >= 5) {
            newNote = new Note(header, dateTime, noteText);
            closeWindow(okButn);
            DataStore.getNoteStore().add(newNote);

            return true;
        } else {
            Service.timedMessage(warning, 8, "ВНИМАНИЕ: Заявка не была создана!");
            return false;
        }
    }

    /**
     * Метод закрывает окно по нажатию кнопки.
     *
     * @param button кнопка на которую назначается закрытие окна.
     */
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * Метод добавляет текущее время и дату в поле {@link EditorController#dateTime},
     * которое отображается в форме создания заявки.
     */
    private void extractCurrentTime() {
        //получаю текующую дату и время, сохраняю как строку, задаю эту строку в конструктор Label
        //для отображения в коне интерфейса.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        dateTime.setText(currentTime);
    }

    public void setHeader(String text) {
        this.header.setText(text);
    }

    public void setNoteText(String noteText) {
        this.noteText.setText(noteText);
    }
}
