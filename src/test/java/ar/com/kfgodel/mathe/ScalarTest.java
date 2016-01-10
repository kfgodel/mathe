package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.kfgodel.mathe.api.Mathe;
import ar.com.kfgodel.mathe.api.Scalar;
import ar.com.kfgodel.mathe.api.ScalarMutabilityType;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.scalar;
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
        context().scalar(()-> scalar(2.0));

        it("has a double value equal to the original", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(2.0);
        });
        
        it("has a float accessor to the value",()->{
          assertThat(context().scalar().asFloat()).isEqualTo(2.0f);
        });

        it("has an int accessor to the value",()->{
          assertThat(context().scalar().asInt()).isEqualTo(2);
        });

        it("its value doesn't change over time", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(2.0);
          assertThat(context().scalar().asDouble()).isEqualTo(2.0);
        });

        it("is immutable", ()->{
          assertThat(context().scalar().mutability()).isEqualTo(ScalarMutabilityType.IMMUTABLE);
        });
      });

      describe("when created from a generator function", ()->{
        Variable<Double> modifiableValue = Variable.of(1.0);
        context().scalar(()-> scalar(modifiableValue::get));

        it("has a double value equal to the function result", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(1.0);
        });

        it("its value can change over time", ()->{
          modifiableValue.set(5.0);
          assertThat(context().scalar().asDouble()).isEqualTo(5.0);

          modifiableValue.set(6.0);
          assertThat(context().scalar().asDouble()).isEqualTo(6.0);
        });

        it("is mutable", ()->{
          assertThat(context().scalar().mutability()).isEqualTo(ScalarMutabilityType.MUTABLE);
        });
      });

      describe("when created from a lazy function", ()->{
        Variable<Double> modifiableValue = Variable.of(1.0);
        context().scalar(()-> Mathe.lazyScalar(modifiableValue::get));

        it("has a double value equal to the function result", ()->{
          assertThat(context().scalar().asDouble()).isEqualTo(1.0);
        });

        it("its value it's cached the first time", ()->{
          modifiableValue.set(8.0);
          assertThat(context().scalar().asDouble()).isEqualTo(8.0);

          modifiableValue.set(6.0);
          assertThat(context().scalar().asDouble()).isEqualTo(8.0);
        });

        it("is immutable", ()->{
          assertThat(context().scalar().mutability()).isEqualTo(ScalarMutabilityType.IMMUTABLE);
        });
      });

      describe("knwon objects", () -> {
        it("is zero",()->{
            assertThat(Mathe.ZERO_SCALAR).isEqualTo(scalar(0.0));
        });   
        it("is unity",()->{
          assertThat(Mathe.ONE_SCALAR).isEqualTo(scalar(1.0));
        });   
      });


      describe("as a functional supplier", ()->{
        it("returns its double value when used as a DoubleSupplier", ()->{
          assertThat(scalar(1.0).getAsDouble()).isEqualTo(1.0);
        });
        it("returns itself when used as a Supplier", ()->{
          Scalar scalar = scalar(1.0);
          assertThat(scalar.get()).isSameAs(scalar);
        });
      });

      describe("equality", ()->{
        it("is defined by it's current value", ()->{
          assertThat(scalar(3.0)).isNotEqualTo(scalar(1.0));
          assertThat(scalar(3.0)).isEqualTo(scalar(3.0));
        });
        describe("compared to a number", () -> {
          it("is true if the double value of the number is equal to the scalar value",()->{
            assertThat(scalar(3.0)).isEqualTo(3.0);
            assertThat(scalar(3.0)).isEqualTo(3.0f);
            assertThat(scalar(3.0)).isEqualTo(3);
          });
          
          it("is false if the values differ",()->{
            assertThat(scalar(3.5)).isNotEqualTo(3.6);
            assertThat(scalar(3.5)).isNotEqualTo(3.500001f);
            assertThat(scalar(3.5)).isNotEqualTo(3);
          });   
        });

      });

      describe("toString", ()->{
        it("is its current value as string", ()->{
          assertThat(scalar(18.0).toString()).isEqualTo("18.0");
        });
      });

      describe("addition", ()->{
        it("is the sum of both scalars", ()->{
          assertThat(scalar(1.0).plus(scalar(9.0))).isEqualTo(scalar(10.0));
        });
        it("can receive a primitive value",()->{
          assertThat(scalar(1.0).plus(9.0)).isEqualTo(scalar(10.0));
        });
        it("is immutable if both scalars are immutable", ()->{
          assertThat(scalar(1.0).plus(scalar(9.0)).mutability()).isEqualTo(ScalarMutabilityType.IMMUTABLE);
        });
        it("is mutable if any scalar is mutable", ()->{
          assertThat(scalar(1.0).plus(()-> 9.0).mutability()).isEqualTo(ScalarMutabilityType.MUTABLE);
        });

      });

      describe("multiplication", ()->{
        it("is the scalar with the multiplication of both values", ()->{
          assertThat(scalar(2.0).multiply(scalar(5.0))).isEqualTo(scalar(10.0));
        });
        it("can receive a primitive value",()->{
          assertThat(scalar(2.0).multiply(5.0)).isEqualTo(scalar(10.0));
        });
        it("is the multiplication by 2 when doubled() called",()->{
          assertThat(scalar(9.0).doubled()).isEqualTo(scalar(18.0));
        });   
      });

      describe("division", () -> {
        it("is the division by other scalar",()->{
          assertThat(scalar(5.0).divide(scalar(2.0))).isEqualTo(scalar(2.5));
        });
        it("can receive a primitive value",()->{
          assertThat(scalar(1.0).divide(2.0)).isEqualTo(scalar(0.5));
        });
        it("is the division by 2 when called to halved()",()->{
          assertThat(scalar(5.0).halved()).isEqualTo(scalar(2.5));
        });   
      });

      describe("invertion", () -> {
        it("changes the scalar sign",()->{
          assertThat(scalar(2.0).invert()).isEqualTo(scalar(-2.0));
        });   
      });

      describe("integerization", () -> {
        it("converts the value to an integer",()->{
          assertThat(scalar(1.99).integered()).isEqualTo(scalar(1.0));
        });   
      });

      describe("comparators", () -> {
        describe("isLessThan", () -> {
          it("is true if value is less than other scalar",()->{
            assertThat(scalar(1.0).isLessThan(scalar(1.1))).isTrue();
          }); 
          it("is false if value is equal or greater than other value",()->{
            assertThat(scalar(1.0).isLessThan(scalar(1.0))).isFalse();
            assertThat(scalar(1.0).isLessThan(scalar(0.9))).isFalse();
          });
          it("can receive a primitive value",()->{
            assertThat(scalar(1.0).isLessThan(1.1)).isTrue();
          });
        });
        describe("isLessOrEqualTo", () -> {
          it("is true if value is less or equal to other scalar",()->{
            assertThat(scalar(1.0).isLessOrEqualTo(scalar(1.1))).isTrue();
            assertThat(scalar(1.0).isLessOrEqualTo(scalar(1.0))).isTrue();
          });
          it("is false if value is greater than other value",()->{
            assertThat(scalar(1.0).isLessOrEqualTo(scalar(0.9))).isFalse();
          });
          it("can receive a primitive value",()->{
            assertThat(scalar(1.0).isLessOrEqualTo(1.1)).isTrue();
          });
        });
        describe("isEqualTo", () -> {
          it("is true if value is equal to other scalar",()->{
            assertThat(scalar(1.0).isEqualTo(scalar(1.0))).isTrue();
          });
          it("is false if value is less or greater than other value",()->{
            assertThat(scalar(1.0).isEqualTo(scalar(1.1))).isFalse();
            assertThat(scalar(1.0).isEqualTo(scalar(0.9))).isFalse();
          });
          it("can receive a primitive value",()->{
            assertThat(scalar(1.0).isEqualTo(1.0)).isTrue();
          });
        });
        describe("isGreaterOrEqualTo", () -> {
          it("is true if value is greater or equal to other scalar",()->{
            assertThat(scalar(1.0).isGreaterOrEqualTo(scalar(0.9))).isTrue();
            assertThat(scalar(1.0).isGreaterOrEqualTo(scalar(1.0))).isTrue();
          });
          it("is false if value is less than other value",()->{
            assertThat(scalar(1.0).isGreaterOrEqualTo(scalar(1.1))).isFalse();
          });
          it("can receive a primitive value",()->{
            assertThat(scalar(1.0).isGreaterOrEqualTo(0.9)).isTrue();
          });
        });
        describe("isGreaterThan", () -> {
          it("is true if value is greater than other scalar",()->{
            assertThat(scalar(1.0).isGreaterThan(scalar(0.9))).isTrue();
          });
          it("is false if value is equal or less than other value",()->{
            assertThat(scalar(1.0).isGreaterThan(scalar(1.0))).isFalse();
            assertThat(scalar(1.0).isGreaterThan(scalar(1.1))).isFalse();
          });
          it("can receive a primitive value",()->{
            assertThat(scalar(1.0).isGreaterThan(0.9)).isTrue();
          });
        });
      });


    });
  }
}
