package com.user.register_user.service;

import com.user.register_user.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
  public String create(User user);

  public List<User> findAll();

 public User findById(String id);

   public void update(String id, User user);

    public void PartialUpdate(String id, Map<String, Object> updates);


    public String delete(String id);
}
