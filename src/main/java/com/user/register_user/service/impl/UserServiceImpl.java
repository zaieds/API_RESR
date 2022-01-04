package com.user.register_user.service.impl;

import com.user.register_user.dao.UserRepository;
import com.user.register_user.exception.RessourceAlreadyExistsException;
import com.user.register_user.exception.RessourceNotFoundException;
import com.user.register_user.models.User;
import com.user.register_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        if ((year - user_year_birthday) >= 18 && user.getCountry().equals("France")) {
            userRepository.save(user).getId();
            return "user is created";
        }
        return "Only adult French residents are allowed to create an account";
    }


    @Override
    public List findAll() {
        return (List) userRepository.findAll();
    }

    @Override
    public User findById(String id) throws RessourceNotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new RessourceNotFoundException();
        } else {
            return userRepository.findById(id).get();
        }
    }

    @Override
    public String update(String id, User user) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RessourceNotFoundException();
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(user.getBirthday());
            int user_year_birthday = calendar.get(Calendar.YEAR);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if ((year - user_year_birthday) >= 18 && user.getCountry().equals("France")) {
                user.setId(id);
                userRepository.save(user);
                return "user is updated";
            }
            return " You cant update user. Only adult French residents are allowed to create an account";
        }

    }


    @Override
    public String PartialUpdate(String id, Map<String, Object> updates, User user) {
        String message = "";
        if (userRepository.findById(id).isEmpty()) {
            throw new RessourceNotFoundException();
        } else {
            User userToUpdate = userRepository.findById(id).get();

            for (String key : updates.keySet()) {
                System.out.println("-----------------");
                System.out.println(updates);
                System.out.println("-----------------");
                System.out.println(key);
                System.out.println("-----------------");
                System.out.println(updates.get(key));
                System.out.println("-------------------------------------------------------------------------------------");
                    switch (key) {

                        case "userName": {

                            userToUpdate.setUserName((String) updates.get(key));
                            // message = "the name is updated";

                            break;
                        }
                        case "birthday": {

                            userToUpdate.setBirthday((Date) updates.get(key));
                            //  message = "the birthday is updated";
                            break;

                        }
                        case "phoneNumber": {
                            userToUpdate.setPhoneNumber((int) updates.get(key));
                            // message = "the phone number is updated";
                            break;
                        }
                        case "country": {

                            userToUpdate.setCountry((String) updates.get(key));
                            //message = "the country is updated";
                            break;
                        }
                        case "gender": {
                            userToUpdate.setGender((String) updates.get(key));
                            // message = "the gender is updated";
                            break;
                        }
                    }
                }

            return message ;
        }


    }

    @Override
    public String delete(String id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RessourceNotFoundException();
        } else {
            User userDelete = userRepository.findById(id).get();
            userRepository.delete(userDelete);
            return "user is deleted";
        }
    }


}
