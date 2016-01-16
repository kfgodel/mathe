package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.mathe.api.BidiVector;
import ar.com.kfgodel.mathe.api.Scalar;
import ar.com.kfgodel.mathe.api.ScalarMutabilityType;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import org.assertj.core.data.Offset;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the behavior of a bidi vector
 * Created by tenpines on 03/01/16.
 */
@RunWith(JavaSpecRunner.class)
public class BidiVectorTest extends JavaSpec<MatheTestContext> {
  @Override
  public void define() {
    describe("a bi dimensional vector", ()->{
      context().vector(()-> vector(scalar(1.0), scalar(7.0)));

      it("can also be created from primitives", ()->{
        assertThat(vector(2.0, 3.0)).isNotNull();
        assertThat(vector(scalar(2.0), 3.0)).isNotNull();
        assertThat(vector(2.0, scalar(3.0))).isNotNull();
      });

      describe("created from a nary", ()->{
        it("accepts a nary of scalars",()->{
          Nary<Scalar> nary = NaryFromNative.of(scalar(1.0), scalar(2.0));
          assertThat(vector(nary)).isEqualTo(vector(1.0, 2.0));
        });
        it("throws an error if the stream contains less than 2 scalars",()->{
          Nary<Scalar> nary = NaryFromNative.of(scalar(1));
          try{
            vector(nary);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
          }catch (IllegalArgumentException e){
            assertThat(e).hasMessage("Insufficient scalars in the nary to create a vector. Expected 2, got: 1");
          }
        });
        it("ignores the additional scalars if more than 2 passed",()->{
          Nary<Scalar> nary = NaryFromNative.of(scalar(1.0), scalar(2.0), scalar(3.0));
          assertThat(vector(nary)).isEqualTo(vector(1.0, 2.0));
        });   
        it("also accepts a nary of numbers",()->{
          Nary<Integer> nary = NaryFromNative.of(1, 2);
          assertThat(vectorFrom(nary)).isEqualTo(vector(1.0, 2.0));
        });
      });

      describe("first component", ()->{
        it("is the first given scalar", ()->{
          assertThat(context().vector().firstComponent()).isEqualTo(scalar(1.0));
        });
        it("is also known as x", ()->{
          assertThat(context().vector().x()).isEqualTo(scalar(1.0));
        });
        it("is also known as width", ()->{
          assertThat(context().vector().width()).isEqualTo(scalar(1.0));
        });
      });

      describe("second component", ()->{
        it("is the second given scalar", ()->{
          assertThat(context().vector().secondComponent()).isEqualTo(scalar(7.0));
        });
        it("is also known as y", ()->{
          assertThat(context().vector().y()).isEqualTo(scalar(7.0));
        });
        it("is also known as height", ()->{
          assertThat(context().vector().height()).isEqualTo(scalar(7.0));
        });
      });

      describe("known objects", ()->{
        it("is zero",()->{
            assertThat(BidiVector.ZERO).isEqualTo(vector(0.0, 0.0));
        });
        
        it("is x",()->{
          assertThat(BidiVector.X).isEqualTo(vector(1.0, 0.0));
        });

        it("is y",()->{
          assertThat(BidiVector.Y).isEqualTo(vector(0.0, 1.0));
        });

      });

      describe("as a supplier", ()->{
        it("returns itself", ()->{
          assertThat(context().vector().get()).isSameAs(context().vector());
        });
      });

      describe("toString", ()->{
        it("is the string representation of an array of two doubles", ()->{
          assertThat(context().vector().toString()).isEqualTo("[1.0, 7.0]");
        });
      });

      describe("equality", ()->{
        it("is based on its two components", ()->{
          assertThat(context().vector()).isEqualTo(vector(1.0, 7.0));
          assertThat(context().vector()).isNotEqualTo(vector(2.0, 7.0));
          assertThat(context().vector()).isNotEqualTo(vector(1.0, 8.0));
        });
      });

      describe("mutability", ()->{
        it("is mutable if any of the components is mutable", ()->{
          assertThat(vector(()-> 1.0, ()-> 2.0).mutability())
            .isEqualTo(ScalarMutabilityType.MUTABLE);
          assertThat(vector(()-> 1.0, scalar(2.0)).mutability())
            .isEqualTo(ScalarMutabilityType.MUTABLE);
          assertThat(vector(scalar(()-> 1.0), ()-> 2.0).mutability())
            .isEqualTo(ScalarMutabilityType.MUTABLE);
        });
        it("is immutable if both components are immutable", ()->{
          assertThat(vector(scalar(1.0), scalar(2.0)).mutability()).isEqualTo(ScalarMutabilityType.IMMUTABLE);
          assertThat(vector(lazyScalar(()-> 1.0), lazyScalar(()-> 2.0)).mutability())
            .isEqualTo(ScalarMutabilityType.IMMUTABLE);
        });
      });

      describe("addition", ()->{
        it("is the vector addition of components", ()->{
          assertThat(context().vector().plus(vector(2.0, 4.0))).isEqualTo(vector(3.0, 11.0));
        });
      });

      describe("subtraction", ()->{
        it("is the vector resulting from subtracting each pair of components", ()->{
          assertThat(context().vector().minus(vector(2.0, 4.0))).isEqualTo(vector(-1.0, 3.0));
        });
      });

      describe("component product", ()->{
        it("is the vector formed by multiplying components of each vector", ()->{
          assertThat(context().vector().componentProduct(vector(-2.0, -1.0))).isEqualTo(vector(-2.0, -7.0));
        });
      });

      describe("scalar product", () -> {
        it("multiplies each component by a scalar",()->{
          assertThat(context().vector().scalarProduct(scalar(3.0))).isEqualTo(vector(3.0, 21.0));
        });
        it("does scalar product 2 when called to doubled()",()->{
          assertThat(context().vector().doubled()).isEqualTo(vector(2.0, 14.0));
        });   
      });

      describe("division", () -> {
        it("is the scalar division of each component",()->{
          assertThat(context().vector().divide(scalar(2.0))).isEqualTo(vector(0.5, 3.5));
        });
        it("divides by 2 when called to halved()",()->{
          assertThat(context().vector().halved()).isEqualTo(vector(0.5, 3.5));
        });
        it("is same as halved() when called to center()",()->{
          assertThat(context().vector().center()).isEqualTo(context().vector().halved());
        });
      });


      describe("invertion", () -> {
        it("changes the x sign when called to invertX()",()->{
            assertThat(context().vector().invertX()).isEqualTo(vector(-1.0, 7.0));
        });
        it("changes the y sign when called to invertY()",()->{
          assertThat(context().vector().invertY()).isEqualTo(vector(1.0, -7.0));
        });   
        it("changes both components when invert() called",()->{
          assertThat(context().vector().invert()).isEqualTo(vector(-1.0, -7.0));
        });   
      });

      describe("integerization", () -> {
        it("converts each component to an integer value",()->{
          assertThat(vector(1.2, -3.5).integered()).isEqualTo(vector(1.0, -3.0));
        });   
      });

      describe("rotation", () -> {
        it("rotates the vector components on the 2d space",()->{
          BidiVector rotated = vector(1.0, 0.0).rotate(90);
          assertThat(rotated.x().asDouble()).isEqualTo(0.0, Offset.offset(0.00000001));
          assertThat(rotated.y().asDouble()).isEqualTo(1.0);
        });
      });

      describe("components", () -> {
        it("has 2 elements",()->{
            assertThat(context().vector().components().count()).isEqualTo(2);
        }); 
        
        it("has the first component as first element",()->{
          assertThat(context().vector().components().findFirst().get()).isSameAs(context().vector().firstComponent());
        }); 
        
        it("has the second component as last",()->{
          assertThat(context().vector().components().findLast().get()).isSameAs(context().vector().secondComponent());
        });
      });


    });
  }


}
