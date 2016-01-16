package ar.com.kfgodel.mathe;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.mathe.api.Endpoint;
import ar.com.kfgodel.mathe.impl.interval.EndpointType;
import org.junit.runner.RunWith;

import static ar.com.kfgodel.mathe.api.Mathe.scalar;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the expected behavior for an endpoint
 * Created by ikari on 14/01/2016.
 */
@RunWith(JavaSpecRunner.class)
public class EndpointTest extends JavaSpec<MatheTestContext> {
  @Override
  public void define() {
    describe("an endpoint", () -> {
      context().endpoint(()-> Endpoint.lowestIncluding(1));

      it("has a reference scalar",()->{
        assertThat(context().endpoint().referenceScalar()).isEqualTo(scalar(1));
      });
      
      it("has a type",()->{
        assertThat(context().endpoint().type()).isEqualTo(EndpointType.LOWEST_INCLUSIVE);
      });


      describe("equality", () -> {
        it("is true if scalar and type are equals",()->{
          Endpoint otherEndpoint = Endpoint.lowestIncluding(1);
          assertThat(context().endpoint()).isEqualTo(otherEndpoint);
        });
        it("is false is type is different",()->{
          Endpoint otherEndpoint = Endpoint.lowestExcluding(1);
          assertThat(context().endpoint()).isNotEqualTo(otherEndpoint);
        });
        it("is false if scalar is different",()->{
          Endpoint otherEndpoint = Endpoint.lowestIncluding(1.0001);
          assertThat(context().endpoint()).isNotEqualTo(otherEndpoint);
        });
      });


      describe("inclusion", () -> {
        context().inclusionResult(()-> context().endpoint().includes(context().scalar()));
        describe("when lowest", ()->{
          describe("and inclusive", () -> {
            context().endpoint(()-> Endpoint.lowestIncluding(1));

            it("accepts higher scalars",()->{
              context().scalar(scalar(2));
              assertThat(context().inclusionResult()).isTrue();
            });
            it("rejects lower scalars",()->{
              context().scalar(scalar(0));
              assertThat(context().inclusionResult()).isFalse();
            });
            it("accepts the reference scalar",()->{
              context().scalar(scalar(1));
              assertThat(context().inclusionResult()).isTrue();
            });
          });
          describe("and exclusive", () -> {
            context().endpoint(()-> Endpoint.lowestExcluding(1));
            it("accepts higher scalars",()->{
              context().scalar(scalar(2));
              assertThat(context().inclusionResult()).isTrue();
            });
            it("rejects lower scalars",()->{
              context().scalar(scalar(0));
              assertThat(context().inclusionResult()).isFalse();
            });
            it("rejects the reference scalar",()->{
              context().scalar(scalar(1));
              assertThat(context().inclusionResult()).isFalse();
            });
          });
        });

        describe("when highest", ()->{
          describe("and inclusive", () -> {
            context().endpoint(()-> Endpoint.highestIncluding(1));
            it("accepts lower scalars",()->{
              context().scalar(scalar(0));
              assertThat(context().inclusionResult()).isTrue();
            });
            it("rejects higher scalars",()->{
              context().scalar(scalar(2));
              assertThat(context().inclusionResult()).isFalse();
            });
            it("accepts the reference scalar",()->{
              context().scalar(scalar(1));
              assertThat(context().inclusionResult()).isTrue();
            });
          });
          describe("and exclusive", () -> {
            context().endpoint(()-> Endpoint.highestExcluding(1));
            it("accepts lower scalars",()->{
              context().scalar(scalar(0));
              assertThat(context().inclusionResult()).isTrue();
            });
            it("rejects higher scalars",()->{
              context().scalar(scalar(2));
              assertThat(context().inclusionResult()).isFalse();
            });
            it("rejects the reference scalar",()->{
              context().scalar(scalar(1));
              assertThat(context().inclusionResult()).isFalse();
            });
          });
        });
      });

      describe("string representation", () -> {
        it("is the values and teh symbol for the type of inclusion",()->{
            assertThat(Endpoint.lowestIncluding(1).toString()).isEqualTo("[1.0");
            assertThat(Endpoint.lowestExcluding(1).toString()).isEqualTo("(1.0");
            assertThat(Endpoint.highestIncluding(1).toString()).isEqualTo("1.0]");
            assertThat(Endpoint.highestExcluding(1).toString()).isEqualTo("1.0)");
        });
      });


    });


  }
}