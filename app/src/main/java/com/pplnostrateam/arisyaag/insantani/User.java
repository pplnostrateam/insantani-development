package com.pplnostrateam.arisyaag.insantani;

/**
 * Created by arisyaag on 4/10/16.
 */

    public class User {

        private long id;
        private String email;
        private String name;
        private String password;
        private String phone;


        public User() { }

        public User(String email, String name, String password) {
            this(email, name, password, "999999999");
        }

        public User(String email, String name, String password, String phone) {
            this.email = email;
            this.name = name;
            this.password = password;
            this.phone = phone;
        }

        public long getId() {
            return id;
        }

        public void setId(long value) {
            this.id = value;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String value) {
            this.email = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() { return phone; }

        public void setPhone(String phone) { this.phone = phone; }
}

