package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.kfgodel.mathe.api.*;

import java.util.function.Supplier;

/**
 * Type used to define test dependend variables
 * Created by tenpines on 03/01/16.
 */
public interface MatheTestContext extends TestContext {
  Scalar scalar();
  void scalar(Supplier<Scalar> definition);

  BidiVector vector();
  void vector(Supplier<BidiVector> vector);

  void interval(Supplier<Interval> definition);
  Interval interval();

  void bidiInterval(Supplier<BidiInterval> definition);
  BidiInterval bidiInterval();

  void endpoint(Supplier<Endpoint> definition);
  Endpoint endpoint();

  Boolean inclusionResult();
  void inclusionResult(Supplier<Boolean> definition);
}
