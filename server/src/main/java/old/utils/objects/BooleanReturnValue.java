package old.utils.objects;

import java.util.function.Consumer;

/**
 * This is a class which will return a boolean or an exception if there occurs an error.
 */
public class BooleanReturnValue {
  public boolean exists;
  public Exception exception;

  public BooleanReturnValue(boolean exists, Exception ex){
    this.exists = exists;
    this.exception = ex;
  }

  public void run(Consumer<Boolean> onNoError, Consumer<Exception> error){
    if(exception != null) error.accept(exception);
    else onNoError.accept(exists);
  }

}