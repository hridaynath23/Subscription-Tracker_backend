
package com.hriday.subscriptiontracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hriday.subscriptiontracker.entity.Subscription;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {
}

