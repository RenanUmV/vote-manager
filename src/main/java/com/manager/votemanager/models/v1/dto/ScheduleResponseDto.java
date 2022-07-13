package com.manager.votemanager.models.v1.dto;

import com.manager.votemanager.models.v1.entity.Schedule;
import com.manager.votemanager.models.v1.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto implements Serializable {

    private Long id;

    private String name;

    private String description;

    private StatusEnum status;

    private String winner;

    private Integer qtdVotes = 0;

    private Integer qtdYes = 0;

    private Integer qtdNo = 0;

    private Double yesPercent = 0.00;

    private Double noPercent = 0.00;

    public static ScheduleResponseDto convertToDto(Schedule schedule){

        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .description(schedule.getDescription())
                .status(schedule.getStatus())
                .winner(schedule.getWinner())
                .qtdVotes(schedule.getQtdVotes())
                .qtdYes(schedule.getQtdYes())
                .qtdNo(schedule.getQtdNo())
                .yesPercent(schedule.getYesPercent())
                .noPercent(schedule.getNoPercent()).build();
    }
}
