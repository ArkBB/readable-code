package cleancode.studycafe.tobe.io;

public class StudyCafeIOHandler {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;


    public StudyCafeIOHandler(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void showWelcomeMessage() {
        outputHandler.showWelcomeMessage();
    }

    public void showAnnouncement() {
        outputHandler.showAnnouncement();
    }
}
