package com.iljungitjung.domain.schedule.dto.reservation;

import com.iljungitjung.domain.schedule.entity.Schedule;
import com.iljungitjung.domain.schedule.entity.Type;
import com.iljungitjung.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationBlockRequestDto {

    private String title;
    private String contents;

    @Size(min=8, max=8, message = "date 형식을 맞춰주세요 (ex.20221017)")
    @Pattern(regexp = "^[0-9]+$", message = "date는 숫자만 입력가능합니다.")
    private String date;

    @Size(min=4, max=4, message = "startTime 형식을 맞춰주세요 (ex.1500)")
    @Pattern(regexp = "^[0-9]+$", message = "startTime은 숫자만 입력가능합니다.")
    private String startTime;

    @Size(min=4, max=4, message = "endTime 형식을 맞춰주세요 (ex.1500)")
    @Pattern(regexp = "^[0-9]+$", message = "endTime은 숫자만 입력가능합니다.")
    private String endTime;

    public Schedule toEntity(Date startDate, Date endDate) {
        return Schedule.builder()
                .categoryName(this.title)
                .contents(this.contents)
                .startDate(startDate)
                .endDate(endDate)
                .type(Type.BLOCK)
                .build();
    }

}
