public class OutOfPlaceException extends Exception {
    public OutOfPlaceException(){
        super();
    }
    public OutOfPlaceException(String message) {
        super(message);
    }
    public OutOfPlaceException(String message, Throwable cause) {
        super(message, cause);
    }
    public OutOfPlaceException(Throwable cause) {
        super(cause);
    }
}
