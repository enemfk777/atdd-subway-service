package nextstep.subway.path.domain.fee;

import nextstep.subway.line.domain.vo.Amount;
import nextstep.subway.path.domain.Path;
import nextstep.subway.path.domain.fee.discount.AgeDiscount;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static nextstep.subway.station.domain.Station.stationStaticFactoryForTestCode;
import static org.assertj.core.api.Assertions.assertThat;

class RequireCalculatedFeeTest {

  @DisplayName("거리에 맞는 요금을 반환한다.")
  @CsvSource(value = {"0:0", "10:1250", "11:1350", "20:1450", "50:2050", "51:2150", "60:2250", "100:2750"}, delimiter = ':')
  @ParameterizedTest
  void requireFeeTest(double givenDistance, long expectFee) {
    //given
    Station givenStation = stationStaticFactoryForTestCode(1L, "교대역");
    Path givenPath = new Path(Arrays.asList(givenStation), givenDistance, Amount.ZERO_AMOUNT);

    //when & then
    assertThat(RequireFee.getRequireFee(givenPath)).isEqualTo(expectFee);
  }

  @DisplayName("추가 요금을 더한 만큼의 요금이 반환된다.")
  @CsvSource(value = {"400:1650", "900:2150", "0:1250"}, delimiter = ':')
  @ParameterizedTest
  void additionalFeeTest(long givenAdditionalFee, long expectFee) {
    //given
    Station givenStation = stationStaticFactoryForTestCode(1L, "교대역");
    Path givenPath = new Path(Arrays.asList(givenStation), 1D, Amount.from(givenAdditionalFee));

    //when & then
    assertThat(RequireFee.getRequireFee(givenPath)).isEqualTo(expectFee);
  }

  @DisplayName("연령대 별로 할인된 만큼의 요금이 반환된다.")
  @CsvSource(value = {"TODDLER:0", "CHILDREN:450", "TEENAGER:720", "ADULT:1250"}, delimiter = ':')
  @ParameterizedTest
  void additionalFeeTest(AgeDiscount givenAgeDiscount, long expectFee) {
    //given
    Station givenStation = stationStaticFactoryForTestCode(1L, "교대역");
    Path givenPath = new Path(Arrays.asList(givenStation), 1D, Amount.ZERO_AMOUNT);

    //when & then
    assertThat(RequireFee.getRequireFeeWithDiscount(givenPath, givenAgeDiscount)).isEqualTo(expectFee);
  }

}
