package com.narlock.kaizenprofileapi.repository;

import com.narlock.kaizenprofileapi.model.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaizenProfileRepository extends JpaRepository<Profile, Integer> {
  Profile save(Profile entry);

  void deleteById(Integer id);

  Optional<Profile> findById(Integer id);
}
