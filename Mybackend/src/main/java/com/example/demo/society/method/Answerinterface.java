package com.example.demo.society.method;

import com.example.demo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

public interface Answerinterface extends JpaRepository<Answer, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into " +
            "responses(id, request_id, user_id," +
            "       description, create_time, update_time," +
            "       state) " +
            "values(null, ?1, ?2, " +
            "       ?3, ?4, ?4," +
            "       0)",nativeQuery = true)
    public Integer Add(Integer request_id, Integer user_id, String description, String create_time);

    @Transactional
    @Modifying
    @Query(value = "update responses " +
            "set description=?2,update_time=?3 " +
            "where id=?1",nativeQuery = true)
    public int modifyById(Integer id, String description, String  updatetime);

    @Transactional
    @Modifying
    @Query(value = "update responses " +
            "set state=?2 " +
            "where id=?1",nativeQuery = true)
    public int modifyStateById(Integer id, Integer state);

    @Transactional
    @Query(value = "select p.id,p.request_id,p.create_time,p.update_time,p.description,p.state,p.user_id,q.name,q.username " +
            "from responses p INNER JOIN users q ON p.user_id=q.id " +
            "where p.id=?1",nativeQuery = true)
    public List<Object[]> findQueryById(Integer id);

    @Transactional
    @Query(value = "select p.id,p.request_id,p.create_time,p.update_time,p.description,p.state,p.user_id,q.name,q.username " +
            "from responses p INNER JOIN users q ON p.user_id=q.id " +
            "where p.request_id=?1",nativeQuery = true)
    public List<Object[]> findQueryByRequestId(Integer requestId);

    @Transactional
    @Query(value = "select p.id,p.request_id,p.create_time,p.update_time,p.description,p.state,p.user_id,q.name,q.username " +
            "from responses p INNER JOIN users q ON p.user_id=q.id ",nativeQuery = true)
    public List<Object[]> findQuery();

    @Transactional
    @Query(value = "select p.id,p.request_id,p.create_time,p.update_time,p.description,p.state,q.name,q.username " +
            "from responses p INNER JOIN users q ON p.user_id=q.id " +
            "where q.username=?1",nativeQuery = true)
    public List<Object[]> findMineByUsername(String username);

    @Transactional
    @Query(value = "select state " +
            "from responses p " +
            "where p.id=?1",nativeQuery = true)
    public Integer findStateById(Integer id);

    @Transactional
    @Query(value = "select id " +
            "from responses p " +
            "where p.create_time= ?1", nativeQuery = true)
    public Integer findIdByCreateTime(Timestamp create_time);


    @Transactional
    @Query(value = "select id " +
            "from responses p " +
            "where request_id= ?1", nativeQuery = true)
    public List<Integer> findIdByRequestId(Integer id);

}
