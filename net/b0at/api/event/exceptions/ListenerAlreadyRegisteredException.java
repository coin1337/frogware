package net.b0at.api.event.exceptions;

public class ListenerAlreadyRegisteredException extends RuntimeException {
   private static final String ERROR_MESSAGE = "Unable to register listener %s since it is already registered!";

   public ListenerAlreadyRegisteredException(Object listener) {
      super(String.format("Unable to register listener %s since it is already registered!", listener.getClass().getName()));
   }
}
