package cj1098.animeshare.exceptions;

public class ViewNotAttachedException extends RuntimeException {

    public ViewNotAttachedException() {
        super("No view is attached to this presenter. Attach one first.");
    }
}
