package com.example.mvcdemo2.model;

import jakarta.persistence.*;


    @Entity
    @Table(name = "GPA")
    public class GPA {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name = "username")
        private String username;
        @Column(name = "D1S")
        private String D1S;
        @Column(name = "D1X")
        private String D1X;
        @Column(name = "D2S")
        private String D2S;
        @Column(name = "D2X")
        private String D2X;
        @Column(name = "D3S")
        private String D3S;
        @Column(name = "D3X")
        private String D3X;
        @Column(name = "D4S")
        private String D4S;
        @Column(name = "D4X")
        private String D4X;

        public String getD1S() {
            return D1S;
        }

        public void setD1S(String d1S) {
            D1S = d1S;
        }

        public String getD1X() {
            return D1X;
        }

        public void setD1X(String d1X) {
            D1X = d1X;
        }

        public String getD2S() {
            return D2S;
        }

        public void setD2S(String d2S) {
            D2S = d2S;
        }

        public String getD2X() {
            return D2X;
        }

        public void setD2X(String d2X) {
            D2X = d2X;
        }

        public String getD3S() {
            return D3S;
        }

        public void setD3S(String d3S) {
            D3S = d3S;
        }

        public String getD3X() {
            return D3X;
        }

        public void setD3X(String d3X) {
            D3X = d3X;
        }

        public String getD4S() {
            return D4S;
        }

        public void setD4S(String d4S) {
            D4S = d4S;
        }

        public String getD4X() {
            return D4X;
        }

        public void setD4X(String d4X) {
            D4X = d4X;
        }

        public GPA() {

        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return username;
        }


        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.username = name;
        }


        public GPA(Integer id, String username, String password) {
            this.id = id;
            this.username = username;
            //this.password = password;
        }

        @Override
        public String toString() {
            return "GPA{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", D1S='" + D1S + '\'' +
                    ", D1X='" + D1X + '\'' +
                    ", D2S='" + D2S + '\'' +
                    ", D2X='" + D2X + '\'' +
                    ", D3S='" + D3S + '\'' +
                    ", D3X='" + D3X + '\'' +
                    ", D4S='" + D4S + '\'' +
                    ", D4X='" + D4X + '\'' +
                    '}';
        }
    }
