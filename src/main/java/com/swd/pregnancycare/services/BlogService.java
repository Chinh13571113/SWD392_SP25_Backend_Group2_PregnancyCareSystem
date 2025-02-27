package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.BlogEntity;
import com.swd.pregnancycare.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo ;

    public BlogService(BlogRepo blogRepo){
        this.blogRepo= blogRepo ;
    }
    public List<BlogEntity> getAll(){
        return blogRepo.findAll();
    }
    public Optional<BlogEntity> getbyId(int id){
        return blogRepo.findById(id);

    }
    public void addNew(BlogEntity blogEntity){
        blogRepo.save(blogEntity);
    }
    public void update (BlogEntity blogEntity){
        blogRepo.save(blogEntity);
    }
    public void deleteById (int id){
        blogRepo.deleteById(id);
    }

}
