package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.*;
import com.swd.pregnancycare.entity.*;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.repository.*;
import com.swd.pregnancycare.request.ArticleRequest;
import com.swd.pregnancycare.request.BlogRequest;
import com.swd.pregnancycare.response.ArticleResponse;
import com.swd.pregnancycare.response.BlogResponse;
import com.swd.pregnancycare.response.UserResponse;
import jakarta.transaction.Transactional;
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
  @Autowired
  private BlogCategoryServiceImpl blogCategoryServiceImpl;
  @Autowired
  private ArticleSectionRepo sectionRepo;

  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'EXPERT')")
  public void saveBlog(BlogRequest blogRequest) {
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> user = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity userEntity = user.get();

    GroupEntity groupEntity = null;
    if(blogRequest.getGroupId() != null) {
      groupRepo.findByIdAndDeletedFalse(blogRequest.getGroupId()).orElseThrow(()-> new AppException(ErrorCode.GROUP_NOT_EXIST));
    }


    Optional<BlogCategoryEntity> blogCategory = blogCategoryRepo.findByIdAndDeletedFalse(blogRequest.getBlogCategoryId());
    BlogCategoryEntity blogCategoryEntity = blogCategory.get();

    BlogEntity newBlog = new BlogEntity();

    newBlog.setTitle(blogRequest.getTitle());
    newBlog.setDescription(blogRequest.getDescription());
    newBlog.setStatus(false);
    newBlog.setDatePublish(LocalDateTime.now());
    newBlog.setDeleted(false);
    newBlog.setSlug(blogCategoryServiceImpl.generateSlug(blogRequest.getTitle()));
    newBlog.setGroup(groupEntity);
    newBlog.setUser(userEntity);
    newBlog.setBlogCategory(blogCategoryEntity);
    blogRepo.save(newBlog);
  }




  @Override
  @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'EXPERT')")
  public List<BlogDTO> getAllBlogsByMember() {UserResponse userResponse = userServicesImp.getMyInfo();

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
  public void moveBlogToTrash(int id) {
    Optional<BlogEntity> blog = blogRepo.findByIdAndDeletedFalse(id);
    if (blog.isEmpty()) throw new AppException(ErrorCode.BLOG_NOT_EXIST);
    BlogEntity blogEntity = blog.get();
    blogEntity.setDeleted(true);
    blogRepo.save(blogEntity);
  }


  @Transactional
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteBlog(int id) {
      // Kiểm tra blog có tồn tại hay không
      BlogEntity blog = blogRepo.findById(id)
              .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));

      // Kiểm tra trạng thái xóa của blog (nếu chưa xóa thì không cho xóa vĩnh viễn)
      if (!blog.getDeleted()) {
        throw new AppException(ErrorCode.BLOG_NOT_TRASHED); // BLOG_NOT_TRASHED: blog chưa được đưa vào trash
      }

    try {
      // Nếu blog đã được đánh dấu xóa (deleted = true) thì thực hiện xóa vĩnh viễn
      blogRepo.deleteByIdAndDeletedTrue(id);
    } catch (Exception e) {
      throw new AppException(ErrorCode.BLOG_DELETED_FAILED);
    }
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void restoreBlog(int id) {
    BlogEntity blog = blogRepo.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));

    // Kiểm tra trạng thái xóa của blog (nếu  xóa thì  cho phép restore)
    if (!blog.getDeleted()) {
      throw new AppException(ErrorCode.BLOG_NOT_DELETED); // BLOG_NOT_TRASHED: blog chưa được đưa vào trash
    }

    try {
      blog.setDeleted(false);
      blogRepo.save(blog);
    } catch (Exception e) {
      throw new AppException(ErrorCode.BLOG_SAVED_EXCEPTION);
    }
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
              blogDTO.setSlug(blog.getSlug());

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

              // Category
              BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
              blogCategoryDTO.setId(blog.getBlogCategory().getId());
              blogCategoryDTO.setName(blog.getBlogCategory().getName());
              blogCategoryDTO.setSlug(blog.getBlogCategory().getSlug());
              blogCategoryDTO.setDescription(blog.getBlogCategory().getDescription());
              blogCategoryDTO.setDatePublish(blog.getBlogCategory().getDatePublish());
              blogCategoryDTO.setDeleted(blog.getBlogCategory().getDeleted());
              blogDTO.setBlogCategory(blogCategoryDTO);

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
    blogResponse.setSlug(blogEntity.getSlug());

    // Map user của blog
    UserDTO userDTO = new UserDTO();
    userDTO.setId(blogEntity.getUser().getId());
    userDTO.setFullName(blogEntity.getUser().getFullName());
    userDTO.setEmail(blogEntity.getUser().getEmail());
    userDTO.setRoles(blogEntity.getUser().getRole().getName());
    userDTO.setStatus(blogEntity.getUser().isStatus());
    blogResponse.setUser(userDTO);

    // A category
    BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
    blogCategoryDTO.setId(blogEntity.getBlogCategory().getId());
    blogCategoryDTO.setName(blogEntity.getBlogCategory().getName());
    blogCategoryDTO.setDescription(blogEntity.getBlogCategory().getDescription());
    blogCategoryDTO.setDatePublish(blogEntity.getBlogCategory().getDatePublish());
    blogCategoryDTO.setSlug(blogEntity.getBlogCategory().getSlug());
    blogResponse.setBlogCategory(blogCategoryDTO);

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

  @Override
  @PreAuthorize("hasAnyRole('EXPERT', 'MEMBER')")
  public BlogResponse getArticleDetail(int articleId) {
    BlogEntity blogEntity = blogRepo.findByIdAndDeletedFalse(articleId)
            .orElseThrow(() -> new AppException(ErrorCode.ARTICLE_NOT_EXIST));
    BlogResponse blogResponse = new BlogResponse();
    blogResponse.setId(blogEntity.getId());
    blogResponse.setTitle(blogEntity.getTitle());
    blogResponse.setDescription(blogEntity.getDescription());
    blogResponse.setDatePublish(blogEntity.getDatePublish());
    blogResponse.setStatus(blogEntity.getStatus());
    blogResponse.setSlug(blogEntity.getSlug());

    // A category
    BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
    blogCategoryDTO.setId(blogEntity.getBlogCategory().getId());
    blogCategoryDTO.setName(blogEntity.getBlogCategory().getName());
    blogCategoryDTO.setDescription(blogEntity.getBlogCategory().getDescription());
    blogCategoryDTO.setDatePublish(blogEntity.getBlogCategory().getDatePublish());
    blogCategoryDTO.setSlug(blogEntity.getBlogCategory().getSlug());
    blogResponse.setBlogCategory(blogCategoryDTO);

    // Map expert của article
    UserDTO userDTO = new UserDTO();
    userDTO.setId(blogEntity.getUser().getId());
    userDTO.setFullName(blogEntity.getUser().getFullName());
    userDTO.setEmail(blogEntity.getUser().getEmail());
    userDTO.setRoles(blogEntity.getUser().getRole().getName());
    userDTO.setStatus(blogEntity.getUser().isStatus());
    blogResponse.setUser(userDTO);

    // Map article section
    List<ArticleSectionDTO> articleSectionDTOS = blogEntity.getArticleSections().stream()
            .map(articleSection -> {
              ArticleSectionDTO articleSectionDTO = new ArticleSectionDTO();
              articleSectionDTO.setId(articleSection.getId());
              articleSectionDTO.setSectionTitle(articleSection.getSectionTitle());
              articleSectionDTO.setDescription(articleSection.getDescription());
              articleSectionDTO.setAnchor(articleSection.getAnchor());
              articleSectionDTO.setDisplayOrder(articleSection.getDisplayOrder());
              return articleSectionDTO;
            })
            .toList();
    blogResponse.setArticleSections(articleSectionDTOS);

    return blogResponse;
  }



  @Override
  @PreAuthorize("hasRole('MEMBER')")
  public ArticleResponse saveArticle(ArticleRequest articleRequest) {
    BlogEntity article = new BlogEntity();
    article.setTitle(articleRequest.getTitle());
    article.setDescription(articleRequest.getDescription());
    article.setDatePublish(LocalDateTime.now());
    article.setSlug(blogCategoryServiceImpl.generateSlug(articleRequest.getTitle()));
    article.setStatus(false);
    article.setDeleted(false);

    // Category
    BlogCategoryEntity blogCategory = blogCategoryRepo.findByIdAndDeletedFalse(articleRequest.getBlogCategoryId()).orElseThrow(()-> new AppException(ErrorCode.BLOG_CATEGORY_NOT_EXIST));
    article.setBlogCategory(blogCategory);

    // Expert
    UserResponse userResponse = userServicesImp.getMyInfo();
    Optional<UserEntity> expert = userRepo.findByIdAndStatusTrue(userResponse.getId());
    UserEntity expertEntity = expert.get();
    article.setUser(expertEntity);

    // Lưu bài viết trước
    BlogEntity savedArticle = blogRepo.save(article);

    // Article Section
    List<ArticleSectionEntity> sections = articleRequest.getArticleSections().stream().map(sectionRequest -> {
      ArticleSectionEntity section = new ArticleSectionEntity();
      section.setSectionTitle(sectionRequest.getSectionTitle());
      section.setDescription(sectionRequest.getDescription());
      section.setDisplayOrder(sectionRequest.getDisplayOrder());
      section.setAnchor(blogCategoryServiceImpl.generateSlug(section.getSectionTitle()));
      section.setBlog(savedArticle);
      return section;
    }).toList();
    article.setArticleSections(sections);

    // Lưu danh sách sections sau khi đã có article ID
    sectionRepo.saveAll(sections);
    savedArticle.setArticleSections(sections);

    // Response

    ArticleResponse res = new ArticleResponse();
    res.setId(savedArticle.getId());
    res.setDatePublish(savedArticle.getDatePublish());
    res.setDescription(savedArticle.getDescription());
    res.setSlug(savedArticle.getSlug());
    res.setStatus(savedArticle.getStatus());
    res.setTitle(savedArticle.getTitle());

    // Expert
    UserDTO userDTO = new UserDTO();
    userDTO.setId(savedArticle.getUser().getId());
    userDTO.setEmail(savedArticle.getUser().getEmail());
    userDTO.setFullName(savedArticle.getUser().getFullName());
    userDTO.setRoles(savedArticle.getUser().getRole().getName());
    userDTO.setStatus(savedArticle.getUser().isStatus());
    res.setUser(userDTO);

    // Category
    BlogCategoryDTO categoryDTO = new BlogCategoryDTO();
    categoryDTO.setId(savedArticle.getBlogCategory().getId());
    categoryDTO.setName(savedArticle.getBlogCategory().getName());
    categoryDTO.setDatePublish(savedArticle.getBlogCategory().getDatePublish());
    categoryDTO.setDescription(savedArticle.getBlogCategory().getDescription());
    categoryDTO.setSlug(savedArticle.getBlogCategory().getSlug());
    res.setBlogCategory(categoryDTO);

    // Section
    List<ArticleSectionDTO> articleSectionDTOS = savedArticle.getArticleSections().stream()
                    .map(sectionEntity -> {
                      ArticleSectionDTO sectionDTO = new ArticleSectionDTO();
                      sectionDTO.setId(sectionEntity.getId());
                      sectionDTO.setSectionTitle(sectionEntity.getSectionTitle());
                      sectionDTO.setDescription(sectionEntity.getDescription());
                      sectionDTO.setAnchor(sectionEntity.getAnchor());
                      sectionDTO.setDisplayOrder(sectionEntity.getDisplayOrder());
                      return sectionDTO;
                    }).toList();
    res.setArticleSections(articleSectionDTOS);

    return res;
  }



  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public List<BlogResponse> getAllBlogs() {
    return blogRepo.findAll().stream()
            .map(blog -> {
              BlogResponse blogResponse = new BlogResponse();
              blogResponse.setId(blog.getId());
              blogResponse.setTitle(blog.getTitle());
              blogResponse.setDatePublish(blog.getDatePublish());
              blogResponse.setStatus(blog.getStatus());
              blogResponse.setDeleted(blog.getDeleted());

              // Author
              UserDTO author = new UserDTO();
              author.setId(blog.getUser().getId());
              author.setFullName(blog.getUser().getFullName());
              author.setEmail(blog.getUser().getEmail());
              author.setRoles(blog.getUser().getRole().getName());
              author.setStatus(blog.getUser().isStatus());
              blogResponse.setUser(author);

              return blogResponse;
            }).collect(Collectors.toList());
  }



  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteManyBlogs(List<Integer> idList){
    List<BlogEntity> blogsToUpdate = blogRepo.findAll().stream()
            .filter(blog -> idList.contains(blog.getId()))
            .peek(blog -> blog.setDeleted(true))
            .collect(Collectors.toList());
    blogRepo.saveAll(blogsToUpdate);
  }


  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void approveBlog(int id) {
    BlogEntity blogEntity = blogRepo.findByIdAndDeletedFalse(id).orElseThrow(()->new AppException(ErrorCode.BLOG_NOT_EXIST));
    blogEntity.setStatus(true);
    blogRepo.save(blogEntity);
  }

}
