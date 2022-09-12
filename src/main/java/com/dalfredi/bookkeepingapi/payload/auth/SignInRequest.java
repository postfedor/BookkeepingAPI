package com.dalfredi.bookkeepingapi.payload.auth;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
