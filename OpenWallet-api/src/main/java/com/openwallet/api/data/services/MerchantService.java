package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Merchant;
import com.openwallet.api.data.repositories.MerchantRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantService extends CRUDService<Merchant, MerchantRepository> {
    public Merchant findOrCreateByCategoryCode(Integer categoryCode, String merchantName) {
        return repository.findByIsoCategoryCode(categoryCode)
                .orElseGet(() -> repository.save(new Merchant(categoryCode, merchantName)));
    }

    public Optional<Merchant> findByCategoryCode(Integer categoryCode) {
        return repository.findByIsoCategoryCode(categoryCode);
    }

    public Merchant findOrCreateByYapilyMerchant(yapily.sdk.Merchant yapilyMerchant) {
        Integer catCode = Integer.parseInt(yapilyMerchant.getMerchantCategoryCode());

        return this.findOrCreateByCategoryCode(catCode, yapilyMerchant.getMerchantName());
    }
}
