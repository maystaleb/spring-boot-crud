package com.example.user_account_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 20)
    private String FirstName;
    @NonNull
    @Size(max = 20)
    private String SurName;
    @NonNull
    @Size(max = 20)
    private String UserName;
    @NonNull
    @Size(min = 6, max = 20)
    private String Password;











}
