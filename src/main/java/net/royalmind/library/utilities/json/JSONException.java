package net.royalmind.library.utilities.json;

public class JSONException extends Exception
{
    private static final long serialVersionUID = 0L;
    private Throwable cause;

    public JSONException(final String s) {
        super(s);
    }

    public JSONException(final Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}
