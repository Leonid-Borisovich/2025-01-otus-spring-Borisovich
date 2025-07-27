package ru.otus.hw.models.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

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
