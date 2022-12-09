package by.intexsoft.producer.repository;

import by.intexsoft.producer.entity.PublishEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PublishEventRepository extends JpaRepository<PublishEventEntity, UUID> {
}
