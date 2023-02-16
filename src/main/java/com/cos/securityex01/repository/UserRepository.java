package com.cos.securityex01.repository;

import com.cos.securityex01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를JpaRepository가 들고 있음.
//@Repository라는 어노테이션이 없어도 Ioc되요. 이유는 JpaRepository를 상속했기 때문에..
public interface UserRepository extends JpaRepository<User, Integer> {
    //findBy 규칙 -> username 문법
    //select * from user where username=1?
    public User findByUsername(String username);

    //select * from user where email=1?
//    public User findFyEmail(String email): //Jpa Query method 검색


}
