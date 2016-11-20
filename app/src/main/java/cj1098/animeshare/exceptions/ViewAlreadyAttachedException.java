package cj1098.animeshare.exceptions;

public class ViewAlreadyAttachedException extends RuntimeException {

    public ViewAlreadyAttachedException() {
        super("View is already attached to this presenter. Detach your view first.");
    }
}