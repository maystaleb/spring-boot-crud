package com.example.user_account_service.repository;
import com.example.user_account_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>
{



}
