package com.example.demo.society.method.runuse;

import com.example.demo.entity.User;
import com.example.demo.entity.runuse.Login_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Login_user_interface extends JpaRepository<Login_user, String> {
    @Transactional
    @Query(value = "select * from login_user p where p.username= ?1", nativeQuery = true)
    public Login_user findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "insert into  " +
            "login_user(username, state)  " +
            "value(?1,?2)",nativeQuery = true)
    public int Add(String username, Integer state);

    @Transactional
    @Modifying
    @Query(value = "insert into " +
            "login_user(username, state, token) " +
            "value(?1, ?2, ?3)",nativeQuery = true)
    public int Add(String username, Integer state, String token);

    @Transactional
    @Modifying
    @Query(value = "update login_user set state=?2 where username=?1",nativeQuery = true)
    public int modifyState(String username, Integer state);

    @Transactional
    @Modifying
    @Query(value = "update login_user set token=?2 where username=?1",nativeQuery = true)
    public int modifyToken(String username, String token);
}
