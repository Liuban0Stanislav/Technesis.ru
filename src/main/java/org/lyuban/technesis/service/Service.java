package org.lyuban.technesis.service;

import javafx.animation.FadeTransition;
import javafx.scene.control.Labeled;
import javafx.util.Duration;

public class Service {
    /**
     * Метод заставляет сообщение появиться и потом плавно затухать в течение определенного времени.
     * @param millis время затухания
     * @param messageText текст сообщения
     */
    public static void timedMessage(Labeled label, int millis, String messageText){
        //устанавливаем прозрачность 100%
        label.setOpacity(1);
        //устанавливаем текст
        label.setText(messageText);
        //добавляем анимацию затухания
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), label);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }
}
