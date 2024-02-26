package com.library.bookhub.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 사용자 엔티티
 * @Author : 이준혁
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String userName;
    private String password;
    private String name;
    private int gender;
    private String phone;
    private String email;
    private String role;
    private int point;
    private String zip;
    private String addr1;
    private String addr2;
    private LocalDateTime wDate;
    private LocalDateTime rDate;
    
}
