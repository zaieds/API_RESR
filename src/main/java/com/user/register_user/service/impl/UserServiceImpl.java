package com.user.register_user.service.impl;

import com.user.register_user.dao.UserRepository;
import com.user.register_user.exception.RessourceNotFoundException;
import com.user.register_user.models.DateHelpers;
import com.user.register_user.models.Gender;
import com.user.register_user.models.User;
import com.user.register_user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Override
    public String create(User user) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(user.getBirthday());

        int user_year_birthday = calendar.get(Calendar.YEAR);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if ((year - user_year_birthday) >= 18 && user.getCountry().equals("France")) {
            System.out.println(user.getBirthday());
            userRepository.save(user).getId();
            logger.debug("**** inside create() method ****");
            log.info("***** user is created *****");
            return "user is created";
        }
        log.warn("Only adult French residents are allowed to create an account");
        return "Only adult French residents are allowed to create an account";
    }


    @Override
    public List findAll() {
        logger.debug("**** inside findAll() method ****");
        log.info("***** Get list of users *****");

        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) throws RessourceNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RessourceNotFoundException("User not found for this id :: " + userId));
        logger.debug("**** inside findById() method ****");
        log.info("***** Get user with id :: " +userId+" *****");
        return userRepository.findById(userId).get();
        }

    @Override
    public String update(Long userId, User userDetails) throws RessourceNotFoundException{

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RessourceNotFoundException("User not found for this id :: " + userId));

        String reponse = "";
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(userDetails.getBirthday());
            int user_year_birthday = calendar.get(Calendar.YEAR);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if ((year - user_year_birthday) >= 18 && userDetails.getCountry().equals("France")) {
                userDetails.setId(userId);
                logger.debug("**** inside update() method ****");
                log.info("***** user is updated *****");
                userRepository.save(userDetails);
                reponse = "user is updated ";
            }else
            log.warn("You can't update user. Only adult French residents are allowed to create an account");
            reponse = "You can't update user. Only adult French residents are allowed to create an account";
        {
            return reponse;
        }
    }


    @Override
    public String PartialUpdate(Long userId, Map<String, Object> updates) throws RessourceNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RessourceNotFoundException("User not found for this id :: " + userId));

        User userToUpdate = userRepository.findById(userId).get();
            Map.Entry<String, Object> entry = updates.entrySet().iterator().next();
            Object key = entry.getKey();
            String message = "";
            if (key == "userName") {
                userToUpdate.setUserName((String) updates.get(key));
                message = "update userName";
                log.info("update userName");
            } else if (key == "birthday") {
                userToUpdate.setBirthday((Date) updates.get(key));
                message = "update birthday";
                log.info("update birthday");
            } else if (key == "phoneNumber") {
                userToUpdate.setPhoneNumber((String) updates.get(key));
                message = "update phoneNumber";
                log.info("phoneNumber");
            } else if (key == "country") {
                userToUpdate.setCountry((String) updates.get(key));
                message = "update country";
                log.info("update country");
            } else if (key == "gender") {
                userToUpdate.setGender((Gender) updates.get(key));
                message = "update gender";
                log.info("update gender");
            }
            logger.debug("**** inside partialUpdate() method ****");
            log.info("***** user is updated *****");
            userRepository.save(userToUpdate);
            return message;
        }




    @Override
    public Map<String, Boolean> delete(Long userId) throws RessourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RessourceNotFoundException("User not found for this id :: " + userId));

            User userDelete = userRepository.findById(userId).get();
            logger.debug("**** inside delete() method ****");
            log.info("**** user is deleted ****");
            userRepository.delete(userDelete);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;

    }


}
