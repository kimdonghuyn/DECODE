package com.example.mailauth_practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "certification")
@Table(name = "certification")
public class CertificationEntity {
    @Id
    private String userId;
    private String email;
    private String certificationNumber; // 0이 앞에 올 수 있기 때문에 String으로 사용
}
