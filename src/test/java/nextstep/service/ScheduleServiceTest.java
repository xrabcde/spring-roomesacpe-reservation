package nextstep.service;

import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ScheduleFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nextstep.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ScheduleServiceTest extends ServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        initScheduleTable();
    }

    @Test
    @DisplayName("스케줄을 생성한다.")
    void createSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);

        // when
        Long scheduleId = scheduleService.createSchedule(request);

        // then
        assertThat(scheduleId).isNotNull();
    }

    @Test
    @DisplayName("특정 테마와 날짜에 해당하는 스케줄 목록을 조회한다.")
    void findAllSchedules() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);
        scheduleService.createSchedule(request);

        // when
        ScheduleFindAllResponse schedules = scheduleService.findAllSchedules(THEME_ID, DATE_STRING);

        // then
        assertThat(schedules.getSchedules()).hasSize(1);
    }

    @Test
    @DisplayName("ID에 해당하는 스케줄을 삭제한다.")
    void deleteSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);
        Long scheduleId = scheduleService.createSchedule(request);

        // when, then
        assertDoesNotThrow(() -> scheduleService.deleteSchedule(scheduleId));
    }
}
