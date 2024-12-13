package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.response.AmountOfPromosDTO;

public interface IProductService {
    AmountOfPromosDTO getAmountOfPromosByUser(Long user_id);
}
