package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/blog")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService ;

    @Autowired
    public BlogController(BlogService blogService){
        this.blogService = blogService ;
    }

    @Operation(
            summary = "Get Blog by id",
            description = "Allow to get blog by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Got blog info successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class),
                                    examples = @ExampleObject(
                                            name = "Success Response",
                                            value = "{\n  \"code\": 200,\n  \"message\": \"Got all blogs successfully\",\n  \"data\": \"[{}, {}]\"\n}"
                                    )
                            )
                    ),
            }

    )
    @GetMapping("{id}")
    public ResponseEntity getBlogById (@PathVariable int id ){
        var blog = blogService.getbyId(id);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Got all blogs successfully");
        response.setData(blog);

        return ResponseEntity.ok(blog);
    }


}
