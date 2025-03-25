package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.ConceptionInputDTO;
import com.swd.pregnancycare.dto.IVFInputDTO;
import com.swd.pregnancycare.dto.LastPeriodInputDTO;
import com.swd.pregnancycare.dto.UltrasoundInputDTO;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class CalculatorServicesImp implements CalculatorServices {
    @Override
    public String getPeriodMethod(LastPeriodInputDTO periodInputDTO) {
        if (periodInputDTO == null || periodInputDTO.getLastPeriod() == null || periodInputDTO.getCycleLength() <= 0) {
            throw new AppException(ErrorCode.DATA_INVALID);
        }

        int standardCycleLength = 28;
        int daysToAdd = 280 + (periodInputDTO.getCycleLength() - standardCycleLength);

        return formatDate(periodInputDTO.getLastPeriod().plusDays(daysToAdd));

    }

    @Override
    public String getConceptionDate(ConceptionInputDTO conceptionInputDTO) {
        if (conceptionInputDTO == null || conceptionInputDTO.getConceptionDate() == null) {
            throw new AppException(ErrorCode.DATA_INVALID);
        }

        // Ngày dự sinh = ngày thụ thai + 266 ngày
        return formatDate(conceptionInputDTO.getConceptionDate().plusDays(266));
    }

    @Override
    public String getIVF(IVFInputDTO ivfInputDTO) {
        if (ivfInputDTO == null || ivfInputDTO.getConceptionDate() == null || (ivfInputDTO.getIvdDate() != 3 && ivfInputDTO.getIvdDate() != 5)) {
            throw new AppException(ErrorCode.DATA_INVALID);
        }

        // Tính ngày dự sinh dựa trên ngày chuyển phôi và số ngày của phôi
        int daysToAdd = 266 - ivfInputDTO.getIvdDate();
        return formatDate(ivfInputDTO.getConceptionDate().plusDays(daysToAdd));
    }

    @Override
    public String getUltraSound(UltrasoundInputDTO ultrasoundInputDTO) {
        if (ultrasoundInputDTO == null || ultrasoundInputDTO.getUltrasoundDate() == null
                || ultrasoundInputDTO.getGestationalWeeks() < 0 || ultrasoundInputDTO.getGestationalDays() < 0) {
            throw new AppException(ErrorCode.DATA_INVALID);
        }

        // Tính số ngày thai tính từ ngày siêu âm
        int totalGestationDays = (ultrasoundInputDTO.getGestationalWeeks() * 7) + ultrasoundInputDTO.getGestationalDays();
        int daysToAdd = 280 - totalGestationDays;

        // Tính ngày dự sinh
        LocalDate dueDate = ultrasoundInputDTO.getUltrasoundDate().plusDays(daysToAdd);

        // Trả về kết quả dưới dạng "December 03, 2024"
        return formatDate(dueDate);

    }
    public static String formatDate(LocalDate date) {
        if (date == null) {
            throw new AppException(ErrorCode.DATA_INVALID);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }
}
