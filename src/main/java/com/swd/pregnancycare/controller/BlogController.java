package com.swd.pregnancycare.controller;

import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.request.ArticleRequest;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.ArticleResponse;
import com.swd.pregnancycare.response.BaseResponse;
import com.swd.pregnancycare.response.BlogResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                  schema = @Schema(implementation = BaseResponse.class)

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
          summary = "Get all blogs (Posts and Articles) ",
          description = "ADMIN can get all blogs",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Got all blogs successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BlogResponse.class)
                          )
                  ),
          }

  )
  @GetMapping()
  public ResponseEntity<?> getAllBlogs() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Got all blogs successfully");
    response.setData(blogServiceImp.getAllBlogs());
    return ResponseEntity.ok(response);
  }


  @Operation(
          summary = "Delete many blogs (Posts or Articles) ",
          description = "ADMIN can delete many blogs",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted many blogs successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }

  )
  @DeleteMapping()
  public ResponseEntity<?> deleteManyBlogs(@RequestBody List<Integer> idList) {
    blogServiceImp.deleteManyBlogs(idList);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Deleted many blogs successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Approve a blog (Posts or Articles) ",
          description = "ADMIN can approve a blog",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Approved a blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }

  )
  @PutMapping("/status/{id}")
  public ResponseEntity<?> approveBlog(@PathVariable int id) {
    blogServiceImp.approveBlog(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Approved a blog successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Get post list",
          description = "All users can get all posts",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got post list successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BlogResponse.class)
                          )
                  )
          }
  )

  @GetMapping("/posts")
  public ResponseEntity<?> getAllBlogsByMember() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setData(blogServiceImp.getAllBlogsByMember());
    response.setMessage("Got  post list successfully");
    return ResponseEntity.ok(response);
  }


  @Operation(
          summary = "Get article list",
          description = "All users can get article list",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got article list successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BlogResponse.class)
                          )
                  )
          }
  )
  @GetMapping("/articles")
  public ResponseEntity<?> getAllBlogsByExpert() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setData(blogServiceImp.getAllBlogsByExpert());
    response.setMessage("Got article list successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Move a Blog to trash",
          description = "MEMBER or EXPERT can move a blog to trash",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }

  )
  @DeleteMapping("/trash/{id}")
  public  ResponseEntity<?> moveBlogToTrash(@PathVariable int id) {
    blogServiceImp.moveBlogToTrash(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Deleted blog successfully");
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Delete a Blog permanently",
          description = "ADMIN can delete a blog permanently",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Deleted blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
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
          summary = "Restore a Blog ",
          description = "ADMIN can restore a blog",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Restored blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }

  )
  @PutMapping("/restoration/{id}")
  public  ResponseEntity<?> restoreBlog(@PathVariable int id) {
    blogServiceImp.restoreBlog(id);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Restored blog successfully");
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




  @Operation(
          summary = "Get my blogs",
          description = "MEMBER can get blogs of member",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got my blog successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BlogDTO.class)
                          )
                  ),
          }

  )
  @GetMapping("/my-posts")
  public ResponseEntity<?> getMyBlogs() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got my blogs successfully");
    response.setData(blogServiceImp.getMyBlogs());
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Get blog detail",
          description = "MEMBER can get a blog detail",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got blog detail successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BlogResponse.class)
                          )
                  ),
          }

  )
  @GetMapping("/post-detail/{blogId}")
  public ResponseEntity<?> getBlogDetail(@PathVariable int blogId) {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got blog detail successfully");
    response.setData(blogServiceImp.getPostDetail(blogId));
    return ResponseEntity.ok(response);
  }



  @Operation(
          summary = "Get article detail",
          description = "All users can get a article detail",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "got article detail successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = ArticleResponse.class)
                          )
                  ),
          }

  )
  @GetMapping("/article-detail/{blogId}")
  public ResponseEntity<?> getArticleDetail(@PathVariable int blogId) {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("got article detail successfully");
    response.setData(blogServiceImp.getArticleDetail(blogId));
    return ResponseEntity.ok(response);
  }


  @Operation(
          summary = "Create a new article ",
          description = "EXPERT can create a new article",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created a article successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = ArticleResponse.class)
                          )
                  ),
          }

  )
  @PostMapping("/articles")
  public ResponseEntity<?> createArticle(@RequestBody ArticleRequest articleRequest) {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created article successfully");
    response.setData(blogServiceImp.saveArticle(articleRequest));
    return ResponseEntity.ok(response);
  }
}
