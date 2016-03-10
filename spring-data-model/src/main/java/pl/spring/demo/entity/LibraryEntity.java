package pl.spring.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "LIBRARY")
public class LibraryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "LIBRARY_ID", nullable = false, updatable = false)
    private Set<BookEntity> books = new HashSet<>();
}
