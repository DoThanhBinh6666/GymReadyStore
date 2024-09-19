package com.project.shopapp_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
   @JsonProperty("full_name")
   private String fullName;
   @JsonProperty("phone_number")
   @NotBlank(message = "so dien thoai khong duoc de trong")
   private String phoneNumber;

   private String address;

   @NotBlank(message = "khong duoc de trong password ")
   private String password;

   @JsonProperty("retype_password")
   private String retypePassword;

   @JsonProperty("date_of_brith")
   private Date dateOfBrith;

   @JsonProperty("facebook_account_id")
   private int facebookAccountId;

   @JsonProperty("google_account_id")
   private int googleAccountId;

   @JsonProperty("role_id")
   @NotNull(message = "role id is repuired")
   private Long roleId;
}
