package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.LoginServices;
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
@RequestMapping(value = "/api/login")
@CrossOrigin
@Tag(name = "Login API", description = "API for user authentication")

public class LoginController {
    @Autowired
    private LoginServices loginServices;
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
//                    @ApiResponse(
//                            responseCode = "400",
//                            description = "Invalid request",
//                            content = @Content(
//                                    examples = @ExampleObject(
//                                            name = "Invalid Request",
//                                            value = "{ \"code\": 400, \"message\": \"Email or password is missing\", \"data\": null }"
//                                    )
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "401",
//                            description = "Unauthorized - Invalid credentials",
//                            content = @Content(
//                                    examples = @ExampleObject(
//                                            name = "Unauthorized",
//                                            value = "{ \"code\": 401, \"message\": \"Invalid email or password\", \"data\": null }"
//                                    )
//                            )
//                    ),
//                    @ApiResponse(responseCode = "500", description = "Internal server error")

            }

    )
    @PostMapping
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
}
