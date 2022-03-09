package it.castelli.graphics;

public enum Scenes {
    LOGIN("login"),
    MENU("menu"),
    REGISTER("register"),
    SIGNATURE("signature"),
    CERTIFICATE("certificate");

    private final String fxmlName;
    private final String FXML_PATH = "./res/fxmls/";
    private final String FXML_EXTENSION = ".fxml";

    private Scenes(String fxmlName) {
        this.fxmlName = fxmlName;
    }

    public String getPath() {
        return FXML_PATH + fxmlName + FXML_EXTENSION;
    }
}
