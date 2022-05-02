package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 기본 함수가 JpaRespository에 있다.
//@Repository라는 어노테이션이 없어도 bean등ㄹ고이 됨
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUserId(String userId);

}
