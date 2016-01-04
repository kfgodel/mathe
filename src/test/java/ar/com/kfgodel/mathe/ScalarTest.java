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
public class ScalarTest extends JavaSpec<MatheTestContext> {
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

      describe("as a functional supplier", ()->{
        it("returns its double value when used as a DoubleSupplier", ()->{
          assertThat(Mathe.scalar(1.0).getAsDouble()).isEqualTo(1.0);
        });
        it("returns itself when used as a Supplier", ()->{
          Scalar scalar = Mathe.scalar(1.0);
          assertThat(scalar.get()).isSameAs(scalar);
        });
      });

      describe("equality", ()->{
        it("is defined by it's current value", ()->{
          assertThat(Mathe.scalar(3.0)).isNotEqualTo(Mathe.scalar(1.0));
          assertThat(Mathe.scalar(3.0)).isEqualTo(Mathe.scalar(3.0));
        });
      });

      describe("toString", ()->{
        it("is its current value as string", ()->{
          assertThat(Mathe.scalar(18.0).toString()).isEqualTo("18.0");
        });
      });
    });
  }
}
