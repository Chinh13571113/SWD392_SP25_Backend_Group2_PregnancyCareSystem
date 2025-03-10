package com.swd.pregnancycare.services;

import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.request.GroupRequest;
import com.swd.pregnancycare.response.BaseResponse;


public interface GroupServices {
  BaseResponse saveGroup(GroupRequest group);
  BaseResponse getAllGroups();
  BaseResponse deleteGroup(int id);
}
