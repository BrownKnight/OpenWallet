package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Merchant;

import java.util.Optional;

public interface MerchantRepository extends EntityRepository<Merchant> {
    Optional<Merchant> findByIsoCategoryCode(Integer categoryCode);
}
