package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.request.UserRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.UserServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin
@Tag(name = "User API", description = "API for Users")
public class UserController {
    @Autowired
    private UserServicesImp userServicesImp;
    @Operation(
            summary = "Get All Users",
            description = "Get list of user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get list successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = "{\n  \"code\": 200,\n  \"message\": \"Get List User successful\",\n  \"data\": {\n    \"id\": 1,\n    \"email\": \"user@example.com\",\n    \"password\": \"hashed_password\",\n    \"full_name\": \"John Doe\",\n    \"id_role\": 2\n  }\n}"
                                    )
                            )
                    ),


            }

    )
    @GetMapping
    public ResponseEntity<?> findAll(){
        BaseResponse response=new BaseResponse();
        response.setMessage("");
        response.setData(userServicesImp.getListUser());
        response.setCode(200);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get User Info",
            description = "Send token to get own info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get list successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = "{\n  \"code\": 200,\n  \"message\": \"Get List User successful\",\n  \"data\": {\n    \"id\": 1,\n    \"email\": \"user@example.com\",\n    \"password\": \"hashed_password\",\n    \"full_name\": \"John Doe\",\n    \"id_role\": 2\n  }\n}"
                                    )
                            )
                    ),


            }

    )
    @GetMapping("/my-info")
    public ResponseEntity<?> getMyInfo(){
        BaseResponse response=new BaseResponse();
        response.setMessage("");
        response.setData(userServicesImp.getMyInfo());
        response.setCode(200);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create a new User",
            description = "User can create a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Parameter(description = "User details", required = true)
            @RequestBody UserRequest userRequest) {
        BaseResponse response=new BaseResponse();
        if(!userServicesImp.createUser(userRequest)) throw new AppException(ErrorCode.REGISTER_FAILED);
        response.setMessage("Create user successfully");
        // Giả sử bạn đã xử lý logic để tạo user
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<?> updateUser(
            @PathVariable int id,
            @Parameter(description = "User name", required = false, example = "John Doe")
            @RequestParam(required = false) String name,
            @Parameter(description = "User email", required = false, example = "john@example.com")
            @RequestParam(required = false) String email,
            @Parameter(description = "User password", required = false, example = "new password")

            @RequestParam(required = false) String password) {
      BaseResponse response = new BaseResponse();
      userServicesImp.updateUser(id, name, email, password);
      response.setCode(200);
      response.setMessage("Updated user successfully");
      return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete User",
            description = "MEMBER or ADMIN can delete account by providing the ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        BaseResponse response = new BaseResponse();

        if(userServicesImp.deleteUserById(id)){
            response.setCode(200);
            response.setMessage("Deleted successfully");
            response.setData("");
        }
        return ResponseEntity.ok(response);
    }
}
