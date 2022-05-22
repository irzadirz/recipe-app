package com.recipee.utils;

import com.recipee.activity.dto.RecipeDocumentDTO;
import com.recipee.activity.model.Ingredient;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Utils {


    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) return true;
        return false;
    }

    public static long getTime() {
        return new Date().getTime();
    }
    public static String getDateString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
        return sdf.format(new Date());
    }
    public static boolean isNull(Object value) {
        if (value == null) return true;
        return false;
    }

    public static boolean isNullOrEmptyCollection(Collection value) {
        if (value == null || value.isEmpty()) return true;
        return false;
    }
}
