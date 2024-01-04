package com.example.ContactList.service;

import com.example.ContactList.model.Contact;
import com.example.ContactList.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService
{
    private final ContactRepository repository;

    public List<Contact> findAll() {
        return repository.findAll();
    }

    public Contact findById(Long id) {
        return repository.findById(id);
    }

    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    public Contact update(Contact contact) {
        return repository.update(contact);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
