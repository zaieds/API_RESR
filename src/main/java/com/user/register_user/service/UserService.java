package com.user.register_user.service;

import com.user.register_user.exception.RessourceNotFoundException;
import com.user.register_user.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
  public String create(User user);

  public List findAll();

 public User findById(Long userId) throws RessourceNotFoundException;

   public String update(Long userId, User userDetails)throws RessourceNotFoundException;

    public String PartialUpdate(Long userId, Map<String, Object> updates) throws RessourceNotFoundException;

   public Map<String, Boolean> delete(Long userId);


    ;
}
