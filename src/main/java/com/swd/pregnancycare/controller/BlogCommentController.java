package com.swd.pregnancycare.controller;


import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.services.BlogCommentServiceImpl;
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
@RequestMapping(value = "/api/blog-comments")
@CrossOrigin
@Tag(name = "Blog Comment API", description = "API for Blog Comment")
public class BlogCommentController {
  @Autowired
  private BlogCommentServiceImpl blogCommentServiceImpl;



  @Operation(
          summary = "Create a new blog comment",
          description = "MEMBER can create a new blog comment",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created blog comment successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Invalid input provided"
                  )
          }
  )
  @PostMapping()
  public ResponseEntity<?> createBlogComment(@RequestParam int blogId,
                                        @RequestParam String description) {
    blogCommentServiceImpl.saveBlogComment(blogId, description);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created blog comment successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Delete comment",
          description = "MEMBER or ADMIN can delete comment",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted comment successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Deleted comment successfully"
}
"""
                                  )
                          )
                  )
          }
  )

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBlogComment(@PathVariable int id){
    blogCommentServiceImpl.deleteBlogComment(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Deleted comment successfully");
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Update comment",
          description = "MEMBER can update comment",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Updated comment successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = """
{
  "code": 200,
  "message": "Updated comment successfully"
}
"""
                                  )
                          )
                  )
          }
  )

  @PutMapping("/{id}")
  public ResponseEntity<?> updateBlogComment(@PathVariable int id, @RequestParam String description){
    blogCommentServiceImpl.updateBlogComment(id, description);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Updated comment successfully");
    return ResponseEntity.ok(response);
  }


}
