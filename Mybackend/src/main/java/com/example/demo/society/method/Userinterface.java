package com.example.demo.society.method;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.List;

//用户信息接口类
@Repository
public interface Userinterface extends JpaRepository<User, Integer> {

    @Transactional
    @Query(value = "select * from users p where p.username= ?1", nativeQuery = true)
    public User findByUsername(String username);

    @Transactional
    @Query(value = "select * from users p where p.username= ?1", nativeQuery = true)
    public List<User> findsByUsername(String username);

    @Transactional
    @Query(value = "select id from users p where p.username= ?1", nativeQuery = true)
    public Integer findIdByUsername(String username);

    @Transactional
    @Query(value = "select reg_community from users p where p.username= ?1", nativeQuery = true)
    public String findRegCommunityByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "insert into " +
            "users(id, username, password," +
            "       type, name, certificate_type," +
            "       certificate_num, phone_num, user_level," +
            "       introduction, reg_city, reg_community," +
            "       reg_time, update_time) " +
            "values(null, ?1, ?2, " +
            "       0, ?3, ?4," +
            "       ?5, ?6, 0," +
            "       ?7, ?8, ?9," +
            "       ?10, ?11)",nativeQuery = true)
    public int Add(String username, String password, String name,
                   Integer certificateType, String certificateNum, String phoneNum,
                   String introduction, String regCity, String regCommunity,
                   String regTime, String updateTime);

    @Transactional
    @Modifying
    @Query(value = "update users set phone_num=?2,introduction=?3,password=?4,update_time=?5 where username=?1",nativeQuery = true)
    public int modify(String username, String phoneNum, String introduction, String password,String updateTime);

//    @Modifying
//    @Query(value = "delete from  users  p where p.username= ?1",nativeQuery = true)
//    public int deleteByName(@Param("username") String name);

}
