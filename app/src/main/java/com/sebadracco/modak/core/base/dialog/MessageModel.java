package com.sebadracco.modak.core.base.dialog;

public class MessageModel {

    private String message;
    private String title;
    private String type;
    private String screen;
    private String buttonText;
    private String buttonDeeplink;

    public MessageModel() { }

    public MessageModel(String message, String title, String type, String screen, String buttonText, String buttonDeeplink) {
        this.setMessage(message);
        this.setTitle(title);
        this.setType(type);
        this.setScreen(screen);
        this.setButtonText(buttonText);
        this.setButtonDeeplink(buttonDeeplink);
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getScreen() {
        return screen;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getButtonDeeplink() {
        return buttonDeeplink;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public void setButtonDeeplink(String buttonDeeplink) {
        this.buttonDeeplink = buttonDeeplink;}
    }