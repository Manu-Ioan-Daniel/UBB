package org.example.utils;



public class ServiceFactory {

     private static ServiceFactory instance;

     private ServiceFactory() {

     }

     public static ServiceFactory getInstance() {
          if (instance == null) {
               instance = new ServiceFactory();
          }
          return instance;
     }


}
