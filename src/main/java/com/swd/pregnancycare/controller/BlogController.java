package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.request.GroupRequest;
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

import java.util.List;

@RestController
@RequestMapping(value = "api/blogs")
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
  @PostMapping
  public ResponseEntity<?> saveBlog(@RequestBody BlogRequest blog) {
    blogServices.saveBlog(blog);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Saved blog successfully");
    response.setData("{}");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Get all Blogs",
          description = "Allow to get all blogs",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Got all blog successfully",
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
  @GetMapping
  public ResponseEntity<?> getAllBlogs() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setData(blogServices.getAllBlogs());
    response.setMessage("Got all blogs successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Delete a Blog",
          description = "Allow to delete a blog",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Deleted blog successfully\",\n  \"data\": \"null\"\n}"
                                  )
                          )
                  ),
          }

  )
  @DeleteMapping("/{id}")
  public  ResponseEntity<?> deleteBlog(@PathVariable int id) {
    blogServices.deleteBlog(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Deleted blog successfully");
    response.setData("{}");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Update a blog",
          description = "Allow to update a blog",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Updated blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Updated blog successfully\",\n  \"data\": \"null\"\n}"
                                  )
                          )
                  ),
          }

  )
  @PutMapping("/{id}")
  public ResponseEntity<?> updateBlog(@PathVariable int id,
                                       @RequestBody BlogRequest blogRequest) {
    blogServices.updateBlog(blogRequest, id);

    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Updated blog successfully");
    response.setData("{}");
    return ResponseEntity.ok(response);
  }
}
