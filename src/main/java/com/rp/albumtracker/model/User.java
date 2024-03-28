package com.rp.albumtracker.model;

import java.util.Set;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User /*implements UserDetails */{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;
  @NotEmpty
  String username;

  @NotEmpty
  @Column(unique = true)
  String email;

  @NotEmpty
  String password;
  String picture;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
    joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = {
    @JoinColumn(name = "role_id") })
  private Set<Role> authorities;

  private User() {
  }

  public User(@NotEmpty String username, @NotEmpty String email, @NotEmpty String password, String picture,
      Set<Role> authorities) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.picture = picture;
    this.authorities = authorities;
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getPicture() {
    return picture;
  }

  public void setAuthorities(Set<Role> authorities) {
    this.authorities = authorities;
  }

  /* @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
 */
}
