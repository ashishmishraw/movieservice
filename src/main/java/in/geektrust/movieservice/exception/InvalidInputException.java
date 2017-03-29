package in.geektrust.movieservice.exception;

/**
 * Created by mishra.ashish@icloud.com
 */
public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = 4525821332583716666L;

    private final String parameter;

    public InvalidInputException(final String fieldName, final String message) {
        super(message);
        this.parameter = fieldName;
    }

    public String getFieldName() {
        return parameter;
    }

    @Override
    public String toString() {
        return "InvalidInputException [input=" + parameter + "]";
    }

}