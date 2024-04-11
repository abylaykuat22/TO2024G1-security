package bitlab.to2024g1security.entity;

import bitlab.to2024g1security.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity implements GrantedAuthority {

  @Column(name = "NAME")
  private String name;

  @Override
  public String getAuthority() {
    return name;
  }
}
