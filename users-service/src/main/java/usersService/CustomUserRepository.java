package usersService;

import org.springframework.data.jpa.repository.JpaRepository;

import usersService.model.CustomUser;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

}
