package de.neuefische.backend.security.repo;

import de.neuefische.backend.security.model.UserCredentials;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<UserCredentials, String> {
}