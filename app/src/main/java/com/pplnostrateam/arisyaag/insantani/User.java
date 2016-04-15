package com.pplnostrateam.arisyaag.insantani;

/**
 * Created by arisyaag on 4/10/16.
 */

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    public class User {

        // ------------------------
        // PRIVATE FIELDS
        // ------------------------

        private long id;

        private String email;

        private String name;

        private String password;

        // ------------------------
        // PUBLIC METHODS
        // ------------------------

        public User() { }

        public User(long id) {
            this.id = id;
        }

        public User(String email, String name, String password) {
            this.email = email;
            this.name = name;
            this.password = password;
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

}

