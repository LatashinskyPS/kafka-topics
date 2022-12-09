package by.intexsoft.consumer.exception;


public class TelegramUnavailable extends RuntimeException {
    public TelegramUnavailable() {
        super();
    }

    public TelegramUnavailable(String message) {
        super(message);
    }
}
