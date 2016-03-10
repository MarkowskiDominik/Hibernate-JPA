package pl.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.spring.demo.entity.LibraryEntity;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
	
}
