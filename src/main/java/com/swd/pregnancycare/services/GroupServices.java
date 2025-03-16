package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.GroupDTO;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.response.GroupResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface GroupServices {
  void saveGroup(GroupRequest group);
  List<GroupDTO> getAllGroups();

  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  List<GroupDTO> getAllMyGroups();

  void deleteGroup(int id);
  void updateGroup(String name, String description, int id);
  void addMemberToGroup(int groupId);
  GroupResponse getAllBlogsOfGroup(int groupId);
}
