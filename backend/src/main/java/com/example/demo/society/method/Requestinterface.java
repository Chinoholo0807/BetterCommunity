package com.example.demo.society.method;

import com.example.demo.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

public interface Requestinterface extends JpaRepository<Request, Integer> {
    @Transactional
    @Query(value = "select p.id,p.user_id,p.type,p.title,p.description,p.headcount,p.create_time,p.update_time,p.end_time,p.commission_fee,p.state " +
            "from requests p " +
            "where p.type= ?1", nativeQuery = true)
    public List<Object[]> findInfoByType(Integer type);

    @Transactional
    @Query(value = "select p.id,p.name,p.username," +
            "p.type,q.reg_community,p.title," +
            "p.description,p.headcount,p.create_time," +
            "p.update_time,p.end_time,p.commission_fee,p.state " +
            "from requests p JOIN users q ON p.username=q.username " +
            "where p.type=?1", nativeQuery = true)
    public List<Object[]> findQueryByType(Integer type);

    @Transactional
    @Query(value = "select p.id,p.name,p.username," +
            "p.type,q.reg_community,p.title," +
            "p.description,p.headcount,p.create_time," +
            "p.update_time,p.end_time,p.commission_fee,p.state " +
            "from requests p JOIN users q ON p.username=q.username ", nativeQuery = true)
    public List<Object[]> findQuery();

    @Transactional
    @Query(value = "select requests.id,requests.name,requests.username," +
            "requests.type,users.reg_community,requests.title," +
            "requests.description,requests.headcount,requests.create_time," +
            "requests.update_time,requests.end_time,requests.commission_fee,requests.state " +
            "from requests INNER JOIN users USING(username) " +
            "where requests.type= ?1 AND requests.title LIKE ?2", nativeQuery = true)
    public List<Object[]> findQueryByTypeTitle(Integer type, String title);

    @Transactional
    @Query(value = "select requests.id,requests.name,requests.username," +
            "requests.type,users.reg_community,requests.title," +
            "requests.description,requests.headcount,requests.create_time," +
            "requests.update_time,requests.end_time,requests.commission_fee,requests.state " +
            "from requests INNER JOIN users USING(username) " +
            "where requests.title LIKE ?1", nativeQuery = true)
    public List<Object[]> findQueryByTitle(String title);

    @Transactional
    @Query(value = "select requests.id,requests.name,requests.username," +
            "requests.type,users.reg_community,requests.title," +
            "requests.description,requests.headcount,requests.create_time," +
            "requests.update_time,requests.end_time,requests.commission_fee,requests.state " +
            "from requests INNER JOIN users USING(username) " +
            "where requests.type= ?1 AND users.reg_community LIKE ?2", nativeQuery = true)
    public List<Object[]> findQueryByTypeCommunity(Integer type, String community);

    @Transactional
    @Query(value = "select requests.id,requests.name,requests.username," +
            "requests.type,users.reg_community,requests.title," +
            "requests.description,requests.headcount,requests.create_time," +
            "requests.update_time,requests.end_time,requests.commission_fee,requests.state " +
            "from requests INNER JOIN users USING(username) " +
            "where users.reg_community LIKE ?1", nativeQuery = true)
    public List<Object[]> findQueryByCommunity(String community);

    @Transactional
    @Query(value = "select requests.id,requests.name,requests.username," +
            "requests.type,users.reg_community,requests.title," +
            "requests.description,requests.headcount,requests.create_time," +
            "requests.update_time,requests.end_time,requests.commission_fee,requests.state " +
            "from requests INNER JOIN users USING(username) " +
            "where requests.type= ?1 AND requests.title LIKE ?2 AND users.reg_community LIKE ?3", nativeQuery = true)
    public List<Object[]> findQueryByTypeTitleCommunity(Integer type, String title, String community);

    @Transactional
    @Query(value = "select requests.id,requests.name,requests.username," +
            "requests.type,users.reg_community,requests.title," +
            "requests.description,requests.headcount,requests.create_time," +
            "requests.update_time,requests.end_time,requests.commission_fee,requests.state " +
            "from requests INNER JOIN users USING(username) " +
            "where requests.title LIKE ?1 AND users.reg_community LIKE ?2", nativeQuery = true)
    public List<Object[]> findQueryByTitleCommunity(String title, String community);

    @Transactional
    @Query(value = "select * " +
            "from requests p " +
            "where p.id= ?1", nativeQuery = true)
    public Request findAllById(Integer id);

    @Transactional
    @Query(value = "select p.state " +
            "from requests p " +
            "where p.id= ?1", nativeQuery = true)
    public Integer findStateById(Integer id);

    @Transactional
    @Query(value = "select * " +
            "from requests p " +
            "where p.type= ?1", nativeQuery = true)
    public List<Request> findAllByType(Integer type);

    @Transactional
    @Query(value = "select id,name,username,type,title,description,headcount,create_time,update_time,end_time,commission_fee,state " +
            "from requests p " +
            "where p.username= ?1", nativeQuery = true)
    public List<Object[]> findMineByUsername(String username);

    @Transactional
    @Query(value = "select username " +
            "from requests p " +
            "where p.id= ?1", nativeQuery = true)
    public String findUsernameById(Integer id);

    @Transactional
    @Query(value = "select id " +
            "from requests p " +
            "where p.create_time= ?1", nativeQuery = true)
    public Integer findIdByCreateTime(Timestamp create_time);

    @Transactional
    @Modifying
    @Query(value = "insert into " +
            "requests(id, user_id, username," +
            "       name, type, title," +
            "       description, headcount, create_time," +
            "       update_time, end_time," +
            "       state, commission_fee) " +
            "values(null, ?1, ?2, " +
            "       ?3, ?4, ?5," +
            "       ?6, ?7, ?8," +
            "       ?9, ?10," +
            "       ?11, ?12)",nativeQuery = true)
    public int Add(Integer user_id, String username,
                   String name, Integer type, String title,
                   String description, Integer headcount, String create_time,
                   String update_time, String end_time,
                   //Blob image,
                   Integer state, Double commission_fee);

    @Transactional
    @Modifying
    @Query(value = "insert into " +
            "requests(id, user_id, username," +
            "       name, type, title," +
            "       description, headcount, create_time," +
            "       update_time, end_time," +
            "       state, commission_fee) " +
            "values(null, ?1, ?2, " +
            "       ?3, ?4, ?5," +
            "       ?6, ?7, ?8," +
            "       ?9, ?10," +
            "       ?11," +
            "       ?12, ?13)",nativeQuery = true)
    public int Add(Integer user_id, String username,
                   String name, Integer type, String title,
                   String description, Integer headcount, String create_time,
                   String update_time, String end_time,
                   Blob image,
                   Integer state, Double commission_fee);

    @Transactional
    @Modifying
    @Query(value = "update requests " +
            "set type=?2,title=?3,description=?4,headcount=?5,end_time=?6,commission_fee=?7,update_time=?8 " +
            "where id=?1",nativeQuery = true)
    public int modify(Integer id, Integer type, String title,
                      String description, Integer headcount, String endTime,
                      Double commissionFee, String updateTime);

    @Transactional
    @Modifying
    @Query(value = "update requests " +
            "set state=?2 " +
            "where id=?1",nativeQuery = true)
    public int modifyStateById(Integer id, Integer state);

}
