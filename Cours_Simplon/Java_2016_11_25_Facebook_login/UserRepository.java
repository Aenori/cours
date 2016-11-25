package co.simplon.reserve.repository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.simplon.reserve.model.User;

@Resource
public interface UserRepository extends JpaRepository<User, Integer> {

    // return list of users with particular email
    @Query("select u from User u where u.email = ?1")
    public List<User> findByEmail(String email);

    @Query("select u from User u where u.facebookId = ?1")
    public List<User> findByFacebookId(Long facebookId);
}
