package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.LoginServices;
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

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin
@Tag(name = "Login API", description = "API for user authentication")

public class LoginController {
    @Autowired
    private LoginServices loginServices;
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
                                            value = "{\n  \"code\": 200,\n  \"message\": \"Login successful\",\n  \"data\": {\n    \"id\": 1,\n    \"email\": \"user@example.com\",\n    \"password\": \"hashed_password\",\n    \"full_name\": \"John Doe\",\n    \"id_role\": 2\n  }\n}"
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
            summary = "User Login",
            description = "Authenticate user with email and password to receive a JWT token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "login successful",
                            content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = BaseResponse.class),
                                examples = @ExampleObject(
                                        name = "Success Response",
                                        value = "{\n  \"code\": 200,\n  \"message\": \"Login successful\",\n  \"data\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"\n}"
                                )
                            )
                    ),


            }

    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "User email", required = true, example = "mem@gmail.com")
            @RequestParam String email,
            @Parameter(description = "User password", required = true, example = "1234")
            @RequestParam String password){
        String token = loginServices.login(email, password);
        BaseResponse response =new BaseResponse();
        if(!Objects.equals(token, "")) {
            response.setData(token);
            response.setMessage("Login successful");
            response.setCode(200);
        }else{
            response.setData(token);
            response.setMessage("Login failed");
            response.setCode(403);
        }

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
            @Parameter(description = "User name", required = true, example = "John Doe")
            @RequestParam String name,
            @Parameter(description = "User email", required = true, example = "john@example.com")
            @RequestParam String email,
            @Parameter(description = "User password", required = true, example = "password123")
            @RequestParam String password) {


        return ResponseEntity.ok("Create user successfully");
    }

    // Update User API
    @Operation(
            summary = "Update User information",
            description = "User can update their information by providing the user ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated successfully",
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
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Parameter(description = "User name", required = false, example = "John Doe")
            @RequestParam(required = false) String name,
            @Parameter(description = "User email", required = false, example = "john@example.com")
            @RequestParam(required = false) String email,
            @Parameter(description = "User password", required = false, example = "newpassword123")
            @RequestParam(required = false) String password) {


        return ResponseEntity.ok("Registered user");
    }

    // Delete User API
    @Operation(
            summary = "Delete User",
            description = "User can delete their account by providing the user ID",
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
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        return ResponseEntity.ok("deleted");
    }
}
