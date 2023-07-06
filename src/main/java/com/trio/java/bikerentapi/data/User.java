package com.trio.java.bikerentapi.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "users")
public class User {

  @Id
  private int id;
  private String firstName;
  private String lastName;
  @Email
  private String email;
  private String phoneNumber;

}
