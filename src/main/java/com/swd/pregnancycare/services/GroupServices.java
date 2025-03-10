package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.GroupDTO;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;

import java.util.List;


public interface GroupServices {
  void saveGroup(GroupRequest group);
  List<GroupDTO> getAllGroups();
  void deleteGroup(int id);
  void updateGroup(GroupRequest groupRequest, int id);
}
