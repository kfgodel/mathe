package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.mathe.api.BidiInterval;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of a bidimensional interval
 * Created by ikari on 16/01/2016.
 */
@RunWith(JavaSpecRunner.class)
public class BidiIntervalTest extends JavaSpec<MatheTestContext> {
  @Override
  public void define() {
    describe("a bi-dimensional interval", () -> {
      context().bidiInterval(()-> BidiInterval.from(interval(1, 2), interval(3,4)));

      describe("equality", () -> {
        it("is true if both have equivalent intervals",()->{
          assertThat(context().bidiInterval()).isEqualTo(BidiInterval.from(intervalInclusiveInclusive(1,2), intervalInclusiveInclusive(3,4)));
        }); 
        it("is false if interval vary in values or restriction types",()->{
          assertThat(context().bidiInterval()).isNotEqualTo(BidiInterval.from(intervalInclusiveInclusive(1,3), intervalInclusiveInclusive(3,4)));
          assertThat(context().bidiInterval()).isNotEqualTo(BidiInterval.from(intervalInclusiveInclusive(1,2), intervalInclusiveInclusive(2,4)));
          assertThat(context().bidiInterval()).isNotEqualTo(BidiInterval.from(intervalInclusiveExclusive(1,2), intervalInclusiveInclusive(3,4)));
          assertThat(context().bidiInterval()).isNotEqualTo(BidiInterval.from(intervalInclusiveInclusive(1,2), intervalExclusiveExclusive(3,4)));

        });   
      });

      describe("intervals", () -> {
        it("can be the first component interval",()->{
            assertThat(context().bidiInterval().firstInterval()).isEqualTo(intervalInclusiveInclusive(1,2));
        });
        
        it("can be the second component interval",()->{
          assertThat(context().bidiInterval().secondInterval()).isEqualTo(intervalInclusiveInclusive(3,4));
        });   
      });


      describe("string representation", () -> {
        it("is a semicolon separated interval pair",()->{
            assertThat(context().bidiInterval().toString()).isEqualTo("[1.0; 2.0]; [3.0; 4.0]");
        });   
      });


      describe("contains", () -> {
        it("is true if the vector is inside the bidi interval",()->{
            assertThat(context().bidiInterval().contains(vector(1, 3))).isTrue();
            assertThat(context().bidiInterval().contains(vector(2, 4))).isTrue();
            assertThat(context().bidiInterval().contains(vector(1.5, 3.5))).isTrue();
        });
        
        it("is false if any of the vector components is outside the interval",()->{
          assertThat(context().bidiInterval().contains(vector(1, 2))).isFalse();
          assertThat(context().bidiInterval().contains(vector(3, 4))).isFalse();
          BidiInterval openInterval = BidiInterval.from(intervalExclusiveExclusive(1,2), intervalExclusiveExclusive(3,4));
          assertThat(openInterval.contains(vector(1, 3))).isFalse();
          assertThat(openInterval.contains(vector(2, 4))).isFalse();
        });

      });

    });

  }
}