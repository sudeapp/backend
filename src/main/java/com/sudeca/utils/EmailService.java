package com.sudeca.utils;

import java.util.Date;

/**
 * * Author: Francisco Hernandez
 **/
public interface EmailService {
    String sendSimpleMail(String email,String validateCode);
    Date SumarDias(Date fech, int dias);
}
