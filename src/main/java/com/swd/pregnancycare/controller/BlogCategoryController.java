package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.BlogCategoryServiceImpl;
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
@RequestMapping(value = "/api/blog-categories")
@CrossOrigin
@Tag(name = "Blog category API", description = "API for Blog category")
public class BlogCategoryController {
  @Autowired
  private BlogCategoryServiceImpl blogCategoryServiceImpl;


  @Operation(
          summary = "Create a new blog category",
          description = "ADMIN can Create a new blog category",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created blog category successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Created blog category successfully"
}
"""
                                  )
                          )
                  )
          }
  )
  @PostMapping()
  public ResponseEntity<?> createBlogCategory(@RequestParam String name,
                                              @RequestParam String description) {
    blogCategoryServiceImpl.saveBlogCategory(name, description);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created blog category successfully");
    return ResponseEntity.ok(response);
  }


  @Operation(
          summary = "Get all blog categories",
          description = "MEMBER or EXPERT or ADMIN can get all blog categories",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got blog categories list successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "got blog categories list successfully",
  "data": [
    {
      "id": 1,
      "name": "Something",
      "slug": "something",
      "description": "Something",
      "datePublish": "2025-03-13T10:15:30",
      "blogs": [
         {
            "id": 2,
            "title": "Baby B",
            "description": "Something",
            "datePublish": "2025-03-13T11:00:00",
            "status": false,
            "user": "mem@gmail.com"
         },
         {
            "id": 3,
            "title": "Baby C",
            "description": "Something else",
            "datePublish": "2025-03-13T11:30:00",
            "status": false,
            "user": "mem@gmail.com"
         }
      ]
    }
  ]
}
"""
                                  )
                          )
                  )
          }
  )
  @GetMapping()
  public ResponseEntity<?> getAllBlogCategories(){
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got blog category list successfully");
    response.setData(blogCategoryServiceImpl.getAllBlogCategories());
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Delete blog category",
          description = "ADMIN can delete blog category",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted blog category successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Deleted blog category successfully"
}
"""
                                  )
                          )
                  )
          }
  )

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBlogCategory(@PathVariable int id){
    BaseResponse response = new BaseResponse();
    blogCategoryServiceImpl.deleteBlogCategory(id);
    response.setCode(200);
    response.setMessage("Deleted blog category successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Update blog category",
          description = "ADMIN update blog category",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Updated blog category successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Updated blog category successfully"
}
"""
                                  )
                          )
                  )
          }
  )

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAdvice(@PathVariable int id, @RequestParam String name, @RequestParam String description){
    BaseResponse response = new BaseResponse();
    blogCategoryServiceImpl.updateBlogCategory(id, name, description);
    response.setCode(200);
    response.setMessage("Updated blog category successfully");
    return ResponseEntity.ok(response);
  }
}
