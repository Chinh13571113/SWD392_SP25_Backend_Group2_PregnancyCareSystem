package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.request.FetusRequest;
import com.swd.pregnancycare.request.GroupRequest;

import java.util.List;

public interface FetusServices {
    List<FetusDTO> getAllFetus();
    void saveFetus(FetusRequest fetusRequest);
    void deleteFetus(int id);
    void updateFetus(FetusRequest fetusRequest, int id);

}
