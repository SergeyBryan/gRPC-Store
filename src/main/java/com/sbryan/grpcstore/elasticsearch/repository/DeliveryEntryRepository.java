package com.sbryan.grpcstore.elasticsearch.repository;

import com.sbryan.grpcstore.elasticsearch.model.DeliveryEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DeliveryEntryRepository extends ElasticsearchRepository<DeliveryEntry, String> {
}
