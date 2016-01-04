package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.scalar;
import static ar.com.kfgodel.mathe.api.Mathe.vector;
import static org.assertj.core.api.Assertions.assertThat;

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

    });
  }

}
