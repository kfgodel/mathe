package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.mathe.api.Mathe;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.interval;
import static ar.com.kfgodel.mathe.api.Mathe.scalar;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the expected behavior of an interval
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
          assertThat(context().interval().lowestEndpoint().referenceScalar()).isEqualTo(1);
        });

        it("has a highest",()->{
          assertThat(context().interval().highestEndpoint().referenceScalar()).isEqualTo(2);
        });
        
        it("are defined by their value, regardless of their position",()->{
          assertThat(interval(2, 1).lowestEndpoint().referenceScalar()).isEqualTo(1);
          assertThat(interval(2, 1).highestEndpoint().referenceScalar()).isEqualTo(2);
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
        describe("for an [;] interval", () -> {
          context().interval(()-> Mathe.intervalInclusiveInclusive(1, 2));

          it("is true if scalar is equal to endpoints or in between",()->{
            assertThat(context().interval().contains(scalar(1))).isTrue();
            assertThat(context().interval().contains(1.5)).isTrue();
            assertThat(context().interval().contains(scalar(2))).isTrue();
          });
          it("false if the scalar is lower or higher than the bounds",()->{
            assertThat(context().interval().contains(0.9999999999)).isFalse();
            assertThat(context().interval().contains(2.0000000001)).isFalse();
          });   
        });

        describe("for an [;) interval", () -> {
          context().interval(()-> Mathe.intervalInclusiveExclusive(1, 2));

          it("is true if scalar is equal to lowest endpoint or in between endpoints",()->{
            assertThat(context().interval().contains(1)).isTrue();
            assertThat(context().interval().contains(1.5)).isTrue();
          });
          it("is false if scalar is less than lowest, equal or greater than highest",()->{
            assertThat(context().interval().contains(0.9999999999)).isFalse();
            assertThat(context().interval().contains(2)).isFalse();
            assertThat(context().interval().contains(2.0000000001)).isFalse();
          });   
        });

        describe("for an (;] interval", () -> {
          context().interval(()-> Mathe.intervalExclusiveInclusive(1, 2));

          it("is true if scalar is equal to highest endpoint or in between",()->{
            assertThat(context().interval().contains(1.5)).isTrue();
            assertThat(context().interval().contains(2)).isTrue();
          });
          it("is false if scalar is less or equal to lowest or greater than highest",()->{
            assertThat(context().interval().contains(0.9999999999)).isFalse();
            assertThat(context().interval().contains(1)).isFalse();
            assertThat(context().interval().contains(2.0000000001)).isFalse();
          });
        });

        describe("for an (;) interval", () -> {
          context().interval(()-> Mathe.intervalExclusiveExclusive(1, 2));

          it("is true if scalar is between endpoints",()->{
            assertThat(context().interval().contains(1.5)).isTrue();
          });
          it("is false if scalar is less or equal to lowest or greater or equal to highest",()->{
            assertThat(context().interval().contains(0.9999999999)).isFalse();
            assertThat(context().interval().contains(1)).isFalse();
            assertThat(context().interval().contains(2)).isFalse();
            assertThat(context().interval().contains(2.0000000001)).isFalse();
          });
        });

      });


    });

  }

}