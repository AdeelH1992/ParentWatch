package com.example.lenovossd.parentwatch.Model;

public class User {




        private String email,name,password,phone,avatarUrl,key;
        public User() {
        }

    public User(String email, String name, String password, String phone, String avatarUrl, String key) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }


