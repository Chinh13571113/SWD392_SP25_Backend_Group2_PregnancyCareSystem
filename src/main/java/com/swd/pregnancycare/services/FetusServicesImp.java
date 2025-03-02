package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.FetusDTO;
import com.swd.pregnancycare.repository.FetusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FetusServicesImp implements FetusServices{
    @Autowired
    private FetusRepo fetusRepo;
    @Override
    public List<FetusDTO> getAllFetus() {
        return fetusRepo.findAll().stream().map(data->{
            FetusDTO fetusDTO = new FetusDTO();
            fetusDTO.setIdFetus(data.getId());
            fetusDTO.setNameFetus(data.getName());
            fetusDTO.setDateFetus(data.getDueDate());
            fetusDTO.setGenderFetus(data.getGender());
            return fetusDTO;
        }).toList();
    }
}
