package com.swd.pregnancycare.services;

import com.swd.pregnancycare.request.PackageRequest;
import com.swd.pregnancycare.response.PackageResponse;

import java.util.List;

public interface PackageServices {
  PackageResponse createPackage(PackageRequest request);
  List<PackageResponse> getAllPackages();
  PackageResponse getPackageById(int id);
  PackageResponse updatePackage(int id, PackageRequest request);
  void deletePackage(int id);
}
