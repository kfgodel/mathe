package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.interval;
import static ar.com.kfgodel.mathe.api.Mathe.scalar;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ikari on 11/01/2016.
 */
@RunWith(JavaSpecRunner.class)
public class IntervalTest extends JavaSpec<MatheTestContext> {
  @Override
  public void define() {
    describe("an interval", () -> {

      context().interval(()-> interval(scalar(1), scalar(2)));

      describe("creation", () -> {
        it("is defined by a pair of scalars",()->{
            assertThat(interval(scalar(1), scalar(2))).isNotNull();
        });   
        it("or a mix of scalars and numbers",()->{
          assertThat(interval(1, scalar(2))).isEqualTo(interval(scalar(1), scalar(2)));
          assertThat(interval(scalar(1), 2)).isEqualTo(interval(scalar(1), scalar(2)));
          assertThat(interval(1, 2)).isEqualTo(interval(scalar(1), scalar(2)));
        });
      });

      describe("string representation", () -> {
        it("is the math notation for intervals",()->{
            assertThat(context().interval().toString()).isEqualTo("[1.0; 2.0]");
        });   
      });


      describe("endpoints", () -> {
        it("has a lowest",()->{
          assertThat(context().interval().lowestEndpoint()).isEqualTo(1);
        });

        it("has a highest",()->{
          assertThat(context().interval().highestEndpoint()).isEqualTo(2);
        });
        
        it("are defined by their value, not their position",()->{
          assertThat(interval(2, 1).lowestEndpoint()).isEqualTo(1);
          assertThat(interval(2, 1).highestEndpoint()).isEqualTo(2);
        });   
      });


      describe("equality", () -> {
        it("is true if bounds are equal",()->{
          assertThat(interval(1, 2)).isEqualTo(interval(1, 2));
        });
        it("is false if bounds are different",()->{
          assertThat(interval(1, 2)).isNotEqualTo(interval(3, 4));
        });   
      });

      describe("contains", () -> {
        describe("for an inclusive interval", () -> {
          it("is true if scalar is equal to endpoints or in between",()->{
            assertThat(context().interval().contains(scalar(1))).isTrue();
            assertThat(context().interval().contains(scalar(2))).isTrue();
            assertThat(context().interval().contains(1.5)).isTrue();
          });
          it("false if the scalar is lower or higher than the bounds",()->{
            assertThat(context().interval().contains(0.9999999999)).isFalse();
            assertThat(context().interval().contains(2.0000000001)).isFalse();
          });   

        });

      });


    });

  }

}