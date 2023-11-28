package com.springbatch.project.springbatch.repository.statistics;

import com.springbatch.project.springbatch.repository.booking.BookingEntity;
import com.springbatch.project.springbatch.repository.booking.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "statistics")
public class StatisticsEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statisticsSeq;
    private LocalDateTime statisticsAt; // 일 단위

    private int allCount;
    private int attendedCount;
    private int cancelledCount;

    public static StatisticsEntity create(BookingEntity bookingEntity) {
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setStatisticsAt(bookingEntity.getStatisticsAt());
        statisticsEntity.setAllCount(1);

        if (bookingEntity.isAttended()) {
            statisticsEntity.setAttendedCount(1);
        }

        return statisticsEntity;
    }

    public void add(BookingEntity bookingEntity) {
        this.allCount++;

        if (bookingEntity.isAttended()) {
            this.attendedCount++;
        }
        if (BookingStatus.CANCELLED.equals(bookingEntity.getStatus())) {
            this.cancelledCount++;
        }
    }
}
