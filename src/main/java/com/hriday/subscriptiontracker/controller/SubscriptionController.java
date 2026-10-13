
package com.hriday.subscriptiontracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hriday.subscriptiontracker.entity.Subscription;
import com.hriday.subscriptiontracker.repository.SubscriptionRepository;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionRepository repository;

    public SubscriptionController(SubscriptionRepository repository) {
        this.repository = repository;
    }

    // Get all subscriptions
    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return repository.findAll();
    }

    // Add a new subscription
    @PostMapping
    public Subscription addSubscription(
            @RequestBody Subscription subscription) {
        return repository.save(subscription);
    }

    // Update an existing subscription
    @PutMapping("/{id}")
    public Subscription updateSubscription(
            @PathVariable Long id,
            @RequestBody Subscription updatedSubscription) {

        Subscription existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Subscription not found with ID: " + id));

        existing.setName(updatedSubscription.getName());
        existing.setPrice(updatedSubscription.getPrice());
        existing.setCategory(updatedSubscription.getCategory());
        existing.setBillingCycle(updatedSubscription.getBillingCycle());
        existing.setNextPaymentDate(updatedSubscription.getNextPaymentDate());
        existing.setActive(updatedSubscription.isActive());

        return repository.save(existing);
    }

    // Delete a subscription
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Subscription not found with ID: " + id);
        }

        repository.deleteById(id);
    }
}
