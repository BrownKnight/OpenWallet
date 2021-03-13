package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Merchant;
import com.openwallet.api.data.repositories.MerchantRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantService extends CRUDService<Merchant, MerchantRepository> {
    public Merchant findOrCreateByCategoryCode(Integer categoryCode, String merchantName) {
        if (categoryCode == null || merchantName == null || merchantName.isBlank()) {
            Merchant defaultMerchant = Merchant.MiscMerchant;
            categoryCode = defaultMerchant.getIsoCategoryCode();
            merchantName = defaultMerchant.getMerchantName();
        }
        int finalCategoryCode = categoryCode;
        String finalMerchantName = merchantName;
        return repository.findByIsoCategoryCode(categoryCode)
                .orElseGet(() -> repository.save(new Merchant(finalCategoryCode, finalMerchantName)));
    }

    public Optional<Merchant> findByCategoryCode(Integer categoryCode) {
        return repository.findByIsoCategoryCode(categoryCode);
    }

    public Merchant findOrCreateByYapilyMerchant(yapily.sdk.Merchant yapilyMerchant) {
        String merchantName = yapilyMerchant.getMerchantName();
        int catCode;
        try {
            // Some transactions may not have a category code, so we use 0 to mean Misc
            catCode = Integer.parseInt(yapilyMerchant.getMerchantCategoryCode(), 10);
        } catch (NumberFormatException e) {
            Merchant defaultMerchant = Merchant.MiscMerchant;
            catCode = defaultMerchant.getIsoCategoryCode();
            merchantName = defaultMerchant.getMerchantName();
        }

        return this.findOrCreateByCategoryCode(catCode, merchantName);
    }
}
