package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.mathe.api.ScalarMutabilityType;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.ScalarMutabilityType.IMMUTABLE;
import static ar.com.kfgodel.mathe.api.ScalarMutabilityType.MUTABLE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of mutability types
 * Created by tenpines on 04/01/16.
 */
@RunWith(JavaSpecRunner.class)
public class MutabilityTypeTest extends JavaSpec<MatheTestContext> {

  @Override
  public void define() {
    describe("a mutability type", ()->{
      it("is mutable or immutable", ()->{
        assertThat(ScalarMutabilityType.values())
          .containsExactly(IMMUTABLE, MUTABLE);
      });
      describe("a mutable type", ()->{
        it("keeps mutable after a combination with any other type", ()->{
          assertThat(MUTABLE.combinedWith(MUTABLE)).isEqualTo(MUTABLE);
          assertThat(MUTABLE.combinedWith(IMMUTABLE)).isEqualTo(MUTABLE);
        });
      });
      describe("an immutable type", ()->{
        it("stays immutable if combined with an immutable type", ()->{
          assertThat(IMMUTABLE.combinedWith(IMMUTABLE)).isEqualTo(IMMUTABLE);
        });
        it("becomes mutable if combined with a mutable type", ()->{
          assertThat(IMMUTABLE.combinedWith(MUTABLE)).isEqualTo(MUTABLE);
        });
      });
    });
  }
}
