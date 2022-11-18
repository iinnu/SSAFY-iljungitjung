package com.iljungitjung.domain.schedule.service;

import com.iljungitjung.domain.category.entity.Category;
import com.iljungitjung.domain.category.exception.NoExistCategoryException;
import com.iljungitjung.domain.category.repository.CategoryRepository;
import com.iljungitjung.domain.notification.service.NotificationService;
import com.iljungitjung.domain.schedule.dto.reservation.*;
import com.iljungitjung.domain.schedule.entity.Schedule;
import com.iljungitjung.domain.schedule.entity.Type;
import com.iljungitjung.domain.schedule.exception.*;
import com.iljungitjung.domain.schedule.repository.ScheduleRepository;
import com.iljungitjung.domain.user.entity.User;
import com.iljungitjung.domain.user.exception.NoExistUserException;
import com.iljungitjung.domain.user.repository.UserRepository;
import com.iljungitjung.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ScheduleRepository scheduleRepository;
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public ReservationIdResponseDto reservationRequest(ReservationRequestDto reservationRequestDto, HttpSession httpSession) {

        User user = userService.findUserBySessionId(httpSession);

        User userTo= userRepository.findUserByNickname(reservationRequestDto.getUserToNickname()).orElseThrow(() -> {
            throw new NoExistUserException();
        });

        Category category = categoryRepository.findByCategoryNameAndUser_Email(reservationRequestDto.getCategoryName(), userTo.getEmail()).orElseThrow(() -> {
            throw new NoExistCategoryException();
        });

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date startDate;
        String date = category.getTime();
        Calendar cal = Calendar.getInstance();

        try{
            startDate = formatter.parse(reservationRequestDto.getDate()+reservationRequestDto.getStartTime());
        }catch (Exception e){
            throw new DateFormatErrorException();
        }

        cal.setTime(startDate);
        cal.add(Calendar.MINUTE, Integer.parseInt(date.substring(2)));
        cal.add(Calendar.HOUR, Integer.parseInt(date.substring(0, 2)));

        Date endDate = cal.getTime();

        Schedule schedule = reservationRequestDto.toEntity(startDate, endDate, category.getColor());
        schedule.setScheduleRequestList(user);
        schedule.setScheduleResponseList(userTo);

        schedule = scheduleRepository.save(schedule);
        notificationService.autoReservationMessage(schedule);
        return new ReservationIdResponseDto(schedule.getId());
    }

    @Override
    @Transactional
    public ReservationIdResponseDto reservationManage(Long id, ReservationManageRequestDto reservationManageRequestDto, HttpSession httpSession) {

        User user = userService.findUserBySessionId(httpSession);

        Schedule schedule = scheduleRepository.findScheduleById(id).orElseThrow(()->{
            throw new NoExistScheduleDetailException();
        });

        String cancelFrom = "";

        if(checkSamePerson(user, schedule.getUserTo())){
            if(reservationManageRequestDto.isAccept()){
                schedule.accepted();
            }else{
                cancelFrom="제공자";
                schedule.canceled(cancelFrom, reservationManageRequestDto.getReason());
            }
        }else if(checkSamePerson(user, schedule.getUserFrom())){
            if(reservationManageRequestDto.isAccept()){
                throw new NoGrantAcceptScheduleException();
            }else{
                cancelFrom="사용자";
                schedule.canceled(cancelFrom, reservationManageRequestDto.getReason());
            }
        }else{
            throw new NoGrantAccessScheduleException();
        }
        notificationService.autoReservationMessage(schedule);
        return new ReservationIdResponseDto(schedule.getId());
    }

    @Override
    public void reservationDelete(Long id, String reason, HttpSession httpSession) {

        User user = userService.findUserBySessionId(httpSession);

        Schedule schedule = scheduleRepository.findScheduleById(id).orElseThrow(()->{
            throw new NoExistScheduleException();
        });

        checkUserSchedule(user, schedule);
        //notificasionService.autoReservationMessage(schedule);
    }

    private void checkUserSchedule(User user, Schedule schedule){
        if (schedule.isUserSchedule(user))
            return;
        throw new NoGrantDeleteScheduleException();
    }

    @Override
    @Transactional
    public ReservationBlockResponseDto reservationBlock(ReservationBlockListRequestDto reservationBlockListRequestDto, HttpSession httpSession) {
        User user = userService.findUserBySessionId(httpSession);

        updateBlockDays(user, reservationBlockListRequestDto);

        return makeReservationBlockResponseDto(user, reservationBlockListRequestDto);
    }

    @Override
    public ReservationViewResponseDto reservationView(String startDate, String endDate, HttpSession httpSession) {

        User user = userService.findUserBySessionId(httpSession);

        startDate += "0000";
        endDate += "2359";

        Date startDateFormat = makeDateFormat(startDate);
        Date endDateFormat = makeDateFormat(endDate);


        return makeReservationViewResponseDto(user, startDateFormat, endDateFormat);
    }
    private boolean checkDate(Schedule schedule, Date startDateFormat, Date endDateFormat){
        return schedule.getStartDate().before(startDateFormat)
                || schedule.getEndDate().before(startDateFormat)
                || schedule.getStartDate().after(endDateFormat)
                || schedule.getEndDate().after(endDateFormat);
    }

    private boolean checkSamePerson(User userFrom, User userTo){
        return userFrom.getId()==userTo.getId();
    }

    private Date makeDateFormat(String date){
        Date dateFormat;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");

        try{
            dateFormat = formatter.parse(date);
        }catch (Exception e){
            throw new DateFormatErrorException();
        }

        return dateFormat;
    }

        List<Schedule> scheduleList = scheduleRepository.findByUserFrom_IdIs(user.getId());

        List<ReservationViewDto> reservationViewDtoList = new ArrayList<>();

        for(Schedule schedule : scheduleList){
            if(checkDate(schedule, startDateFormat, endDateFormat)) continue;

            if(schedule.getType().equals(Type.BLOCK)) continue;
            if(schedule.getType().equals(Type.DELETE)) continue;

            reservationViewDtoList.add(new ReservationViewDto(schedule));

        }
        ReservationViewResponseDto responseDtos = new ReservationViewResponseDto(reservationViewDtoList);
        return new ReservationViewResponseDto(requestList, acceptList, cancelList);
    }

    private void updateBlockDays(User user, ReservationBlockListRequestDto reservationBlockListRequestDto){
        List<Schedule> scheduleList = scheduleRepository.findByUserTo_IdIs(user.getId());

        scheduleList.forEach(schedule -> {
            if(schedule.getType().equals(Type.BLOCK)) scheduleRepository.delete(schedule);
        });

        user.updateBlockDays(reservationBlockListRequestDto.getDays());
    }

    private ReservationBlockResponseDto makeReservationBlockResponseDto(User user, ReservationBlockListRequestDto reservationBlockListRequestDto){
        Long blockReservationCount=0L;

        for(ReservationBlockDto reservationBlockDto : reservationBlockListRequestDto.getBlockList()){
            saveBlockReservation(user, reservationBlockDto);
            blockReservationCount++;
        }

        return new ReservationBlockResponseDto(blockReservationCount);
    }
    private void saveBlockReservation(User user, ReservationBlockDto reservationBlockDto){
        Date startDateFormat = makeDateFormat(reservationBlockDto.getDate()+reservationBlockDto.getStartTime());
        Date endDateFormat = makeDateFormat(reservationBlockDto.getDate()+reservationBlockDto.getEndTime());

        Schedule schedule = reservationBlockDto.toEntity(startDateFormat, endDateFormat);
        schedule.setScheduleResponseList(user);
        scheduleRepository.save(schedule);
    }
}
