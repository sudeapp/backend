package com.sudeca.mock;

import com.sudeca.model.Usuario;

import java.util.Date;

public class SimulationData {

    public static long ID_USER = 1552;
    public static String VOID = "";
    public static Integer OK = 200;
    public static String statusCard = "NORM";

    public static Usuario createUser() {
            Usuario user = new Usuario();
                    //user.setName("Francisco Hernandez");
                    //user.setEmail("fhernandez204@gmail.com");
                    //user.setDateCreate((String.valueOf(new Date())));
                    //user.setDateModified(String.valueOf(new Date()));
                    //user.setDateLastLogin((String.valueOf(new Date())));
                    //user.setActive(true);
                    //user.setPassword("1234567");
                    //user.setPhones(createPhones());
                    //user.getPhones().get(0).setUser(user);
        //String token=createJWT(user);
        //user.setToken(token);
        return user;
    }

    /*public static List<Phone> createPhones() {
        List<Phone> listPhones=new ArrayList<Phone>();
        Phone phone=new Phone();
        phone.setNumber("11111");
        phone.setContrycode("04444");
        phone.setCitycode("5555555");
        listPhones.add(phone);
        return listPhones;
    }*/

    /*public static String createJWT(User user){
        return new CreateJWTImpl().createJWT(
                user.getPassword(), // claim = jti
                user.getEmail(),   // claim = iss
                user.getName(),     // claim = sub
                JW_TIME_TO_LIVE
        );
    }*/

}
