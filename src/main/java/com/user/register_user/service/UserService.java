package com.user.register_user.service;

import com.user.register_user.exception.RessourceAlreadyExistsException;
import com.user.register_user.exception.RessourceNotFoundException;
import com.user.register_user.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
  public String create(User user) throws RessourceAlreadyExistsException;

  public List findAll() throws RessourceNotFoundException;

 public User findById(String id) throws RessourceNotFoundException;

   public String update(String id, User user) throws RessourceNotFoundException;

    public String PartialUpdate(String id, Map<String, Object> updates, User user) throws RessourceNotFoundException;


    public String delete(String id) throws RessourceNotFoundException;
}
