package net.b0at.api.event.exceptions;

public class ListenerNotAlreadyRegisteredException extends RuntimeException {
   private static final String ERROR_MESSAGE = "Unable to deregister listener %s since it is not already registered!";

   public ListenerNotAlreadyRegisteredException(Object listener) {
      super(String.format("Unable to deregister listener %s since it is not already registered!", listener.getClass().getName()));
   }
}
