package com.springbatch.project.springbatch.service;

import com.springbatch.project.springbatch.repository.statistics.AggregatedStatistics;
import com.springbatch.project.springbatch.repository.statistics.StatisticsRepository;
import com.springbatch.project.springbatch.service.statistics.ChartData;
import com.springbatch.project.springbatch.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {
    @Mock
    private final StatisticsRepository statisticsRepository;

    @InjectMocks
    private final StatisticsService statisticsService;

    @Test
    public void makeChartDataTest() throws Exception{
        //given
        final LocalDateTime to = LocalDateTime.of(2023, 11, 29, 0 ,9);

        List<AggregatedStatistics> statisticsList = List.of(
                new AggregatedStatistics(to.minusDays(1), 15, 10, 5),
                new AggregatedStatistics(to, 10, 8, 2)
        );

        //when
        when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(ArgumentMatchers.eq(to.minusDays(10)), eq(to))).thenReturn(statisticsList);

        ChartData chartData = statisticsService.makChartData(to);

        //then
        //then(statisticsRepository.findByStatisticsAtBetweenAndGroupBy());
        verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to));

        assertThat(chartData).isNotNull();
        assertThat(chartData.getLabels()).isEqualTo(new ArrayList<>(List.of("09-09", "09-10")));
        assertThat(chartData.getAttendedCounts()).isEqualTo(new ArrayList<>(List.of(10L, 8L)));
        assertThat(chartData.getCancelledCounts()).isEqualTo(new ArrayList<>(List.of(5L, 2L)));
    }
}
