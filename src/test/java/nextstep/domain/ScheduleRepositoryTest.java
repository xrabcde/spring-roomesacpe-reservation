package nextstep.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static nextstep.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ScheduleRepositoryTest extends RepositoryTest {
    @Autowired
    private ScheduleRepository schedules;

    @BeforeEach
    void setUp() {
        initScheduleTable();
    }

    @Test
    @DisplayName("스케줄을 저장한다.")
    void save() {
        // given
        Schedule schedule = new Schedule(THEME_ID, DATE, TIME);

        // when
        Schedule savedSchedule = schedules.save(schedule);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(schedule);
    }

    @Test
    @DisplayName("테마ID와 날짜에 해당하는 스케줄내역을 조회한다.")
    void findAllByThemeIdAndDate() {
        // given
        Schedule schedule = schedules.save(new Schedule(THEME_ID, DATE, TIME));

        // when
        List<Schedule> findSchedules = schedules.findAllByThemeIdAndDate(THEME_ID, DATE);

        // then
        assertThat(findSchedules).hasSize(1);
        assertThat(findSchedules.get(0)).usingRecursiveComparison()
                .isEqualTo(schedule);
    }

    @Test
    @DisplayName("ID에 해당하는 스케줄을 삭제한다.")
    void deleteById() {
        // given
        Schedule schedule = schedules.save(new Schedule(THEME_ID, DATE, TIME));

        // when, then
        assertDoesNotThrow(() -> schedules.deleteById(schedule.getId()));
        assertThat(schedules.findAllByThemeIdAndDate(THEME_ID, DATE)).isEmpty();
    }
}
