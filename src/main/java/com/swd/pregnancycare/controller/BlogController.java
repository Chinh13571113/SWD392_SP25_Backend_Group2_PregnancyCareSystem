package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.BlogServiceImp;
import com.swd.pregnancycare.services.BlogServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/blogs")
@CrossOrigin
@Tag(name = "Blog API", description = "API for Blog")
public class BlogController {
  @Autowired
  private BlogServiceImp blogServiceImp;


  @Operation(
          summary = "Create a new Blog",
          description = "MEMBER or EXPERT can create a blog",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Created blog successfully\",\n  \"data\": \"null\"\n}"
                                  )
                          )
                  ),
          }

  )
  @PostMapping
  public ResponseEntity<?> saveBlog(@RequestBody BlogRequest blog) {
    blogServiceImp.saveBlog(blog);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created blog successfully");
    return ResponseEntity.ok(response);
  }


  @Operation(
          summary = "Get all List Blogs",
          description = "MEMBER or EXPERT or ADMIN can get all blogs",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got list blogs successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "got list blogs successfully",
  "data": [
    {
      "id": 1,
      "title": "Baby A",
      "description": "5",
      "datePublish": "2025-07-10",
      "status": false,
      "user": {
        "id": 1,
        "email": "user@example.com",
        "fullName": "user",
        "roles": "MEMBER",
        "status": true
      }
    },
    {
      "id": 2,
      "title": "Baby B",
      "description": "6",
      "datePublish": "2025-07-10",
      "status": false,
      "user": {
        "id": 2,
        "email": "expert@example.com",
        "fullName": "expert",
        "roles": "EXPERT",
        "status": false
      }
    }
  ]
}
"""
                                  )
                          )
                  )
          }
  )

  @GetMapping
  public ResponseEntity<?> getAllBlogs() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setData(blogServiceImp.getAllBlogs());
    response.setMessage("Got all blogs successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Delete a Blog",
          description = "MEMBER or EXPERT can delete a blog",
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
    blogServiceImp.deleteBlog(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Deleted blog successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Update a blog",
          description = "MEMBER or EXPERT update a blog",
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
    blogServiceImp.updateBlog(blogRequest, id);

    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Updated blog successfully");
    return ResponseEntity.ok(response);
  }
}
