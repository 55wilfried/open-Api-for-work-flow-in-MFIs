package com.microfinance.users_services.userRepository;

import com.microfinance.users_services.models.CollectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCollectRepository extends JpaRepository<CollectUser, String> {
    @Query("SELECT u FROM CollectUser u WHERE LOWER(u.username) =LOWER(:username)")
    CollectUser findByUserName(@Param("username") String userName);

    @Query("Select u From CollectUser u where LOWER(u.codage) =Lower(:codage)")
    List<CollectUser> findAllByCodage(@Param("codage") String codage);

}
