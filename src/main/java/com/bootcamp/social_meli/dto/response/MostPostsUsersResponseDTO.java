package com.bootcamp.social_meli.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostPostsUsersResponseDTO {
    List<SimpleUserWithPostsCountDTO> most_posts;
}
