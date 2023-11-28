package com.springbatch.project.springbatch.repository.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Integer> {
    @Query(value =  "select new com.springbatch.project.springbatch.repository.statistics.AggregatedStatistics(s.statisticsAt, SUM(s.allCount), SUM(s.attendedCount), SUM(s.cancelledCount)) " +
                    " from StatisticsEntity s " +
                    " where s.statisticsAt between :from AND :to " +
                    " GROUP BY s.statisticsAt")
    List<AggregatedStatistics> findByStatisticsAtBetweenAndGroupBy(@Param("from")LocalDateTime from, @Param("to") LocalDateTime to);
}
