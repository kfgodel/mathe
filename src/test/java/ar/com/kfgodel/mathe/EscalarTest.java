package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.mathe.api.Mathe;
import ar.com.kfgodel.mathe.api.Scalar;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior an scalar value
 * Created by tenpines on 03/01/16.
 */
@RunWith(JavaSpecRunner.class)
public class EscalarTest extends JavaSpec<MatheTestContext> {
  @Override
  public void define() {
    describe("a scalar value", ()->{
      describe("when created from a double", ()->{
        context().scalar(()-> Mathe.scalar(2.0));

        it("has a double value equal to the original", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(2.0);
        });

        it("its value doesn't change over time", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(2.0);
          assertThat(context().scalar().asDouble()).isEqualTo(2.0);
        });
      });
      describe("when cached from a function", ()->{
        Variable<Double> modifiableValue = Variable.of(1.0);
        context().scalar(()-> Mathe.cachedScalar(modifiableValue::get));

        it("has a double value equal to the first function result", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(1.0);
        });

        it("its value it's cached for the subsequent calls", ()->{
          modifiableValue.set(5.0);
          assertThat(context().scalar().asDouble()).isEqualTo(5.0);

          modifiableValue.set(6.0);
          assertThat(context().scalar().asDouble()).isEqualTo(5.0);
        });
      });

      describe("when created from a generator function", ()->{
        Variable<Double> modifiableValue = Variable.of(1.0);
        context().scalar(()-> Mathe.scalar(modifiableValue::get));

        it("has a double function equal to the function result", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(1.0);
        });

        it("its value can change over time", ()->{
          modifiableValue.set(5.0);
          assertThat(context().scalar().asDouble()).isEqualTo(5.0);

          modifiableValue.set(6.0);
          assertThat(context().scalar().asDouble()).isEqualTo(6.0);
        });
      });
    });
  }
}
