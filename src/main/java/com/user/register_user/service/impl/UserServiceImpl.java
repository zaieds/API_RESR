package com.user.register_user.service.impl;

import com.user.register_user.dao.UserRepository;
import com.user.register_user.models.User;
import com.user.register_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String create(User user) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(user.getBirthday());
        int user_year_birthday = calendar.get(Calendar.YEAR);
        int year = Calendar.getInstance().get(Calendar.YEAR);
         if ((year-user_year_birthday) >= 18 && user.getCountry().equals("France")) {
             userRepository.save(user).getId();
             return "Utilisateur créer";
        }
             return "Utilisateur non Créer";

    }

    @Override
    public List<User> findAll() {
List<User> liste = new ArrayList<User>();
userRepository.findAll().forEach(liste::add);
        return liste;
    }

    @Override
    public User findById(String id) {
        if (userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }else {
            return null;
        }
    }

    @Override
    public void update(String id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public void PartialUpdate(String id, Map<String, Object> updates) {
        User userToUpdate = userRepository.findById(id).get();
        for (String key : updates.keySet()) {

            switch (key) {
                case "userName": {
                    userToUpdate.setUserName((String) updates.get(key));
                    break;
                }
                case "birthday": {
                    userToUpdate.setBirthday((Date) updates.get(key));
                    break;
                }
                case "phoneNumber": {
                    userToUpdate.setPhoneNumber((int) updates.get(key));
                    break;
                }
                case "country": {
                    userToUpdate.setCountry((String) updates.get(key));
                    break;
                }
                case "gender": {
                    userToUpdate.setGender((String) updates.get(key));
                    break;
                }
            }
        }

    }

    @Override
    public String delete(String id) {
        User userDelete = userRepository.findById(id).get();
        userRepository.delete(userDelete);
        return "utilisateur supprimer";
    }


}
