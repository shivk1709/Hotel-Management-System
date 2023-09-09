package com.service.user.Daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.user.models.User;

public interface UserDao extends JpaRepository<User, Long> {

}
