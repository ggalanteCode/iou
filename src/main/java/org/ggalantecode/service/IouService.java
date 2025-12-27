package org.ggalantecode.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.exceptions.*;
import org.ggalantecode.model.CreateIouRequest;
import org.ggalantecode.repository.UserRepository;
import org.ggalantecode.validator.IouValidator;

import java.util.*;

@ApplicationScoped
public class IouService {

    @Inject
    UserRepository userRepo;

    @Inject
    IouValidator iouValidator;

    public List<UserEntity> getAllUsers() {
        return userRepo.listAllUsers();
    }

    public List<UserEntity> getUsersByName(List<String> users) {
        return userRepo.listUsersByNames(users);
    }

    public UserEntity createUser(UserEntity user) {
        setUserWithDefaultValues(user);
        userRepo.persist(user);
        return user;
    }

    private void setUserWithDefaultValues(UserEntity user) {
        Map<String, Double> owes = new HashMap<>(), owedBy = new HashMap<>();
        user.setOwes(owes);
        user.setOwed_by(owedBy);
        user.setBalance(0.0);
    }

    public List<UserEntity> createIou(CreateIouRequest iouRequest) {
        iouValidator.validateIouRequest(iouRequest);

        String lenderName = iouRequest.getLenderId();
        UserEntity lender = userRepo.find("name", lenderName).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + lenderName + "\" not found")
        );

        String borrowerName = iouRequest.getBorrowerId();
        UserEntity borrower = userRepo.find("name", borrowerName).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + borrowerName + "\" not found")
        );

        Double amount = iouRequest.getAmount();
        updateLender(lender, borrowerName, amount);
        updateBorrower(borrower, lenderName, amount);

        userRepo.update(lender);
        userRepo.update(borrower);

        return List.of(lender, borrower);
    }

    private void updateLender(UserEntity lender, String nameOfBorrower, Double amount) {
        lender.getOwed_by().put(nameOfBorrower, amount);
        updateUserBalance(lender);
    }

    private void updateBorrower(UserEntity borrower, String nameOfLender, Double amount) {
        borrower.getOwes().put(nameOfLender, amount);
        updateUserBalance(borrower);
    }

    private void updateUserBalance(UserEntity user) {
        Double totalOwedBy = user.getOwed_by().values().stream().reduce(0.0, Double::sum);
        Double totalOwes = user.getOwes().values().stream().reduce(0.0, Double::sum);
        user.setBalance(totalOwedBy - totalOwes);
    }

}
