package com.ytu.sad.security.auth;

import com.ytu.sad.security.user.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserRoleEnum role;
    private Integer favTeamId;
}
