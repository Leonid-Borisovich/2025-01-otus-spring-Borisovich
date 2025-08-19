package ru.otus.hw.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Authority implements GrantedAuthority {

        @Id
        @Column(name="role_id")
        private Long id;

        @Column(unique=true)
        private String role;

        @Override
        public String getAuthority(){
                return role;
        }

}
