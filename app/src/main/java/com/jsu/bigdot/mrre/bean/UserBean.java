package com.jsu.bigdot.mrre.bean;

import java.io.Serializable;

/**
 * Created by Bigdot on 2017/7/5.
 */

public class UserBean implements Serializable{
        private String username;
        private String password;
        private String userimg;
        private String userphone;

        public UserBean() {
            // TODO Auto-generated constructor stub
        }



        public String getUserimg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public String getUserphone() {
            return userphone;
        }

        public void setUserphone(String userphone) {
            this.userphone = userphone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }




    }

