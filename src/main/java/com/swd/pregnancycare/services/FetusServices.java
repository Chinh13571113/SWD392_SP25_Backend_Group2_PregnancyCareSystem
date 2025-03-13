package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.dto.FetusRecodDTO;
import com.swd.pregnancycare.request.FetusRequest;

import java.util.List;

public interface FetusServices {
    List<FetusDTO> getAllFetus();
    FetusDTO saveFetus(FetusRequest fetusRequest);
    void deleteFetus(int id);
    void updateFetus(FetusRequest fetusRequest, int id);
    List<FetusDTO> getMyFetus();
    List<FetusRecodDTO> getFetusRecordById(int id);
    void saveFetusRecord(int id, FetusRecodDTO fetusRecodDTO);
    void deleteFetusRecord(int id);
    int getFetusWeek(int id);


}
