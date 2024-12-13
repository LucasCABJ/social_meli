package com.bootcamp.social_meli.dto.response;

import com.bootcamp.social_meli.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostResponse {
    private String message;
    private PostDTO postDTO;
}
