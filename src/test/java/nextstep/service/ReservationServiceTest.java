package nextstep.service;

import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nextstep.Constants.*;
import static nextstep.service.ReservationService.DUPLICATE_RESERVATION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationServiceTest extends ServiceTest {
    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        initReservationTable();
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(DATE_STRING, TIME_STRING, NAME);

        // when
        Long reservationId = reservationService.createReservation(request);

        // then
        assertThat(reservationId).isNotNull();
    }

    @Test
    @DisplayName("동시간대에 예약이 존재할 경우, 예외를 반환한다.")
    void failToCreateReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(DATE_STRING, TIME_STRING, NAME);
        reservationService.createReservation(request);

        // when, then
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DUPLICATE_RESERVATION_MESSAGE);
    }

    @Test
    @DisplayName("특정 날짜에 해당하는 예약 목록을 조회한다.")
    void findAllReservations() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(DATE_STRING, TIME_STRING, NAME);
        reservationService.createReservation(request);

        // when
        ReservationFindAllResponse reservations = reservationService.findAllReservations(DATE_STRING);

        // then
        assertThat(reservations.getReservations()).hasSize(1);
    }

    @Test
    @DisplayName("특정 날짜와 시간에 해당하는 예약을 삭제한다.")
    void deleteReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(DATE_STRING, TIME_STRING, NAME);
        reservationService.createReservation(request);

        // when, then
        assertDoesNotThrow(() -> reservationService.deleteReservation(DATE_STRING, TIME_STRING));
    }
}
