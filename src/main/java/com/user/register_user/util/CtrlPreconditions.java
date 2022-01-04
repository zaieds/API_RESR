package com.user.register_user.util;

import com.user.register_user.exception.RessourceNotFoundException;

public final class CtrlPreconditions {

    public static <T> T checkFound(T object){
        if (object == null){
            throw new RessourceNotFoundException();
        }
        return object;
    }
}
