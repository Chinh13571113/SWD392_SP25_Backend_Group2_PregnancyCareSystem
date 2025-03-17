package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.BlogCommentDTO;
import com.swd.pregnancycare.dto.BlogDTO;
import com.swd.pregnancycare.dto.GroupDTO;
import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.*;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.repository.*;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.BlogResponse;
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImp implements BlogServices {
  @Autowired
  private BlogRepo blogRepo;
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private GroupRepo groupRepo;
  @Autowired
  private BlogCategoryRepo blogCategoryRepo;

  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void saveBlog(BlogRequest blogRequest) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> user = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity userEntity = user.get();

    GroupEntity groupEntity = groupRepo.findByIdAndDeletedFalse(blogRequest.getGroupId()).orElseThrow(()-> new AppException(ErrorCode.GROUP_NOT_EXIST));

    Optional<BlogCategoryEntity> blogCategory = blogCategoryRepo.findByIdAndDeletedFalse(blogRequest.getBlogCategoryId());
    BlogCategoryEntity blogCategoryEntity = blogCategory.get();

    BlogEntity newBlog = new BlogEntity();

    newBlog.setTitle(blogRequest.getTitle());
    newBlog.setDescription(blogRequest.getDescription());
    newBlog.setStatus(false);
    newBlog.setDatePublish(LocalDateTime.now());
    newBlog.setDeleted(false);
    newBlog.setGroup(groupEntity);
    newBlog.setUser(userEntity);
    newBlog.setBlogCategory(blogCategoryEntity);
    blogRepo.save(newBlog);
  }



  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'EXPERT')")
  public List<BlogDTO> getAllBlogsByMember() {
    UserResponse userResponse = userServicesImp.getMyInfo();

    // Lấy danh sách groupId mà user đã tham gia (chỉ lấy những group chưa bị xóa)
    List<Integer> joinedGroupIds = groupRepo.findAll().stream()
            .filter(group -> Boolean.FALSE.equals(group.getDeleted()))
            .filter(group -> group.getUsers() != null &&
                    group.getUsers().stream()
                            .anyMatch(userGroup -> userGroup.getUser().getId() == userResponse.getId()))
            .map(group -> group.getId())
            .collect(Collectors.toList());

    // Lấy blog với điều kiện:
    // - Blog chưa bị xóa
    // - Chủ blog có role là MEMBER
    // - Blog thuộc về 1 group mà member đã tham gia
    return blogRepo.findAll().stream()
            .filter(blog -> Boolean.FALSE.equals(blog.getDeleted()))
            .filter(blog -> "MEMBER".equals(blog.getUser().getRole().getName()))
            .filter(blog -> blog.getGroup() != null &&
                    joinedGroupIds.contains(blog.getGroup().getId()))
            .filter(blog -> Boolean.FALSE.equals(blog.getGroup().getDeleted()))
            .map(blog -> {
              BlogDTO blogDTO = new BlogDTO();
              blogDTO.setId(blog.getId());
              blogDTO.setTitle(blog.getTitle());
              blogDTO.setDescription(blog.getDescription());
              blogDTO.setDatePublish(blog.getDatePublish());
              blogDTO.setStatus(blog.getStatus());
              blogDTO.setDeleted(blog.getDeleted());

              UserDTO userDTO = new UserDTO();
              userDTO.setId(blog.getUser().getId());
              userDTO.setEmail(blog.getUser().getEmail());
              userDTO.setFullName(blog.getUser().getFullName());
              userDTO.setRoles(blog.getUser().getRole().getName());
              blogDTO.setUser(userDTO);

              GroupDTO groupDTO = new GroupDTO();
              groupDTO.setId(blog.getGroup().getId());
              groupDTO.setName(blog.getGroup().getName());
              groupDTO.setDescription(blog.getGroup().getDescription());
              groupDTO.setDate(blog.getGroup().getDate());
              groupDTO.setDeleted(blog.getGroup().getDeleted());
              blogDTO.setGroup(groupDTO);

              if (blog.getBlogComments() != null) {
                List<BlogCommentDTO> blogCommentDTOS = blog.getBlogComments().stream()
                        .map(comment -> {
                          BlogCommentDTO commentDTO = new BlogCommentDTO();
                          commentDTO.setId(comment.getId());
                          commentDTO.setDescription(comment.getDescription());
                          commentDTO.setDatePublish(comment.getDatePublish());

                          UserDTO userCommentDTO = new UserDTO();
                          userCommentDTO.setId(comment.getUser().getId());
                          userCommentDTO.setEmail(comment.getUser().getEmail());
                          userCommentDTO.setFullName(comment.getUser().getFullName());
                          userCommentDTO.setRoles(comment.getUser().getRole().getName());
                          userCommentDTO.setStatus(comment.getUser().isStatus());
                          commentDTO.setUser(userCommentDTO);

                          return commentDTO;
                        })
                        .collect(Collectors.toList());
                blogDTO.setBlogComments(blogCommentDTOS);
              }
              return blogDTO;
            })
            .collect(Collectors.toList());
  }




  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'MEMBER')")
  public List<BlogDTO> getAllBlogsByExpert() {
    return getBlogsByRole("EXPERT");
  }



  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void deleteBlog(int id) {
    Optional<BlogEntity> blog = blogRepo.findByIdAndDeletedFalse(id);
    if (blog.isEmpty()) throw new AppException(ErrorCode.BLOG_NOT_EXIST);
    BlogEntity blogEntity = blog.get();
    blogEntity.setStatus(true);
    blogRepo.save(blogEntity);
  }



  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void updateBlog(BlogRequest blogRequest, int id) {
    BlogEntity blogEntity = blogRepo.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));

    blogEntity.setTitle(blogRequest.getTitle());
    blogEntity.setDescription(blogRequest.getDescription());
    blogEntity.setDatePublish(LocalDateTime.now());

    blogRepo.save(blogEntity);
  }




  private List<BlogDTO> getBlogsByRole(String roleName) {
    return blogRepo.findAll().stream()
            .filter(blog -> Boolean.FALSE.equals(blog.getDeleted())) // Lọc những blog chưa bị xóa
            .filter(blog -> blog.getUser().getRole().getName().equals(roleName)) // Lọc theo roleName
            .map(blog -> {
              BlogDTO blogDTO = new BlogDTO();
              blogDTO.setId(blog.getId());
              blogDTO.setTitle(blog.getTitle());
              blogDTO.setDescription(blog.getDescription());
              blogDTO.setDatePublish(blog.getDatePublish());
              blogDTO.setStatus(blog.getStatus());
              blogDTO.setDeleted(blog.getDeleted());

              UserDTO userDTO = new UserDTO();
              userDTO.setId(blog.getUser().getId());
              userDTO.setEmail(blog.getUser().getEmail());
              userDTO.setFullName(blog.getUser().getFullName());
              userDTO.setRoles(blog.getUser().getRole().getName());
              blogDTO.setUser(userDTO);

              if (blog.getBlogComments() != null) {
                List<BlogCommentDTO> blogCommentDTOS = blog.getBlogComments().stream()
                        .map(comment -> {
                          BlogCommentDTO commentDTO = new BlogCommentDTO();
                          commentDTO.setId(comment.getId());
                          commentDTO.setDescription(comment.getDescription());
                          commentDTO.setDatePublish(comment.getDatePublish());

                          UserDTO userCommentDTO = new UserDTO();
                          userCommentDTO.setId(comment.getUser().getId());
                          userCommentDTO.setEmail(comment.getUser().getEmail());
                          userCommentDTO.setFullName(comment.getUser().getFullName());
                          userCommentDTO.setRoles(comment.getUser().getRole().getName());
                          userCommentDTO.setStatus(comment.getUser().isStatus());
                          commentDTO.setUser(userCommentDTO);

                          return commentDTO;
                        })
                        .collect(Collectors.toList());
                blogDTO.setBlogComments(blogCommentDTOS);
              }

              return blogDTO;
            })
            .collect(Collectors.toList());
  }


  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
  public List<BlogDTO> getMyBlogs() {
    UserResponse userResponse = userServicesImp.getMyInfo();
    return blogRepo.findAll().stream()
            .filter(blog -> Boolean.FALSE.equals(blog.getDeleted()))
            .filter(blog -> blog.getUser().getId() == userResponse.getId()) // Chỉ lấy blog do user tạo
            .map(blog -> {
              BlogDTO blogDTO = new BlogDTO();
              blogDTO.setId(blog.getId());
              blogDTO.setTitle(blog.getTitle());
              blogDTO.setDescription(blog.getDescription());
              blogDTO.setDatePublish(blog.getDatePublish());
              blogDTO.setStatus(blog.getStatus());
              blogDTO.setDeleted(blog.getDeleted());

              UserDTO userDTO = new UserDTO();
              userDTO.setId(blog.getUser().getId());
              userDTO.setEmail(blog.getUser().getEmail());
              userDTO.setFullName(blog.getUser().getFullName());
              userDTO.setRoles(blog.getUser().getRole().getName());
              blogDTO.setUser(userDTO);

              // Nếu blog có thông tin group
              if (blog.getGroup() != null) {
                GroupDTO groupDTO = new GroupDTO();
                groupDTO.setId(blog.getGroup().getId());
                groupDTO.setName(blog.getGroup().getName());
                groupDTO.setDescription(blog.getGroup().getDescription());
                groupDTO.setDate(blog.getGroup().getDate());
                groupDTO.setDeleted(blog.getGroup().getDeleted());
                blogDTO.setGroup(groupDTO);
              }

              if (blog.getBlogComments() != null) {
                List<BlogCommentDTO> blogCommentDTOS = blog.getBlogComments().stream()
                        .map(comment -> {
                          BlogCommentDTO commentDTO = new BlogCommentDTO();
                          commentDTO.setId(comment.getId());
                          commentDTO.setDescription(comment.getDescription());
                          commentDTO.setDatePublish(comment.getDatePublish());

                          UserDTO userCommentDTO = new UserDTO();
                          userCommentDTO.setId(comment.getUser().getId());
                          userCommentDTO.setEmail(comment.getUser().getEmail());
                          userCommentDTO.setFullName(comment.getUser().getFullName());
                          userCommentDTO.setRoles(comment.getUser().getRole().getName());
                          userCommentDTO.setStatus(comment.getUser().isStatus());
                          commentDTO.setUser(userCommentDTO);

                          return commentDTO;
                        })
                        .collect(Collectors.toList());
                blogDTO.setBlogComments(blogCommentDTOS);
              }
              return blogDTO;
            })
            .collect(Collectors.toList());
  }


  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public BlogResponse getPostDetail(int blogId) {
    BlogEntity blogEntity = blogRepo.findByIdAndDeletedFalse(blogId)
            .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));
    BlogResponse blogResponse = new BlogResponse();
    blogResponse.setId(blogEntity.getId());
    blogResponse.setTitle(blogEntity.getTitle());
    blogResponse.setDescription(blogEntity.getDescription());
    blogResponse.setDatePublish(blogEntity.getDatePublish());
    blogResponse.setStatus(blogEntity.getStatus());

    // Map user của blog
    UserDTO userDTO = new UserDTO();
    userDTO.setId(blogEntity.getUser().getId());
    userDTO.setFullName(blogEntity.getUser().getFullName());
    userDTO.setEmail(blogEntity.getUser().getEmail());
    userDTO.setRoles(blogEntity.getUser().getRole().getName());
    userDTO.setStatus(blogEntity.getUser().isStatus());
    blogResponse.setUser(userDTO);

    // Map danh sách comment
    List<BlogCommentDTO> blogCommentDTOs = blogEntity.getBlogComments().stream()
            .map(comment -> {
              BlogCommentDTO blogCommentDTO = new BlogCommentDTO();
              blogCommentDTO.setId(comment.getId());
              blogCommentDTO.setDescription(comment.getDescription());
              blogCommentDTO.setDatePublish(comment.getDatePublish());

              UserDTO commentUser = new UserDTO();
              commentUser.setId(comment.getUser().getId());
              commentUser.setFullName(comment.getUser().getFullName());
              commentUser.setEmail(comment.getUser().getEmail());
              commentUser.setRoles(comment.getUser().getRole().getName());
              commentUser.setStatus(comment.getUser().isStatus());
              blogCommentDTO.setUser(commentUser);

              return blogCommentDTO;
            })
            .collect(Collectors.toList());

    blogResponse.setBlogComments(blogCommentDTOs);

    return blogResponse;
  }


}
