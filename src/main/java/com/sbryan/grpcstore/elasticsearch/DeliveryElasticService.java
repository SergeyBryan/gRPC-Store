package com.sbryan.grpcstore.elasticsearch;

import com.sbryan.grpcstore.elasticsearch.model.DeliveryEntry;
import com.sbryan.grpcstore.elasticsearch.repository.DeliveryEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.stereotype.Service;

/**
 * Service responsible for saving delivery entries into an Elasticsearch repository.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryElasticService {

    private final DeliveryEntryRepository deliveryEntryRepository;

    /**
     * Saves a {@link DeliveryEntry} into the Elasticsearch repository with an immediate refresh policy.
     *
     * @param deliveryEntry the delivery entry to save
     * @return the ID of the saved delivery entry
     */
    public String saveDeliveryEntry(DeliveryEntry deliveryEntry) {
        log.info("Put delivery into DeliveryIndex: {}", deliveryEntry);
        deliveryEntryRepository.save(deliveryEntry, RefreshPolicy.IMMEDIATE);
        return deliveryEntry.getDeliveryId();
    }

}
