package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.BlogServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/blog")
@CrossOrigin
@Tag(name = "Blog API", description = "API for Blog")
@SecurityRequirement(name = "bearerAuth")
public class BlogController {
  @Autowired
  private BlogServices blogServices;

  @Operation(
          summary = "Save Blog",
          description = "Allow members to save blogs",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Save blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Saved blog successfully\",\n  \"data\": \"null\"\n}"
                                  )
                          )
                  ),
          }

  )
  @PostMapping("/saveBlog")
  public ResponseEntity<?> saveBlog(@RequestBody BlogDTO blog) {
    blogServices.saveBlog(blog);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Saved blog successfully");
    return ResponseEntity.ok(response);
  }
}
