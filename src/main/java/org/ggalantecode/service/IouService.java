package org.ggalantecode.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.exceptions.*;
import org.ggalantecode.model.CreateIouRequest;
import org.ggalantecode.repository.UserRepository;

import java.util.*;

@ApplicationScoped
public class IouService {

    @Inject
    UserRepository userRepo;

    public List<UserEntity> getAllUsers() {
        return userRepo.listAllUsers();
    }

    public List<UserEntity> getUsersByName(List<String> users) {
        return userRepo.listUsersByNames(users);
    }

    public UserEntity createUser(UserEntity user) {
        Map<String, Double> owes = new HashMap<>(), owedBy = new HashMap<>();
        user.setOwes(owes);
        user.setOwed_by(owedBy);
        user.setBalance(0.0);
        userRepo.persist(user);
        return user;
    }

    public List<UserEntity> createIou(CreateIouRequest iouRequest) {
        if(iouRequest.getLenderId().equals(iouRequest.getBorrowerId())) {
            throw new NotValidInputException("lender and borrower cannot be the same person...");
        }
        if(iouRequest.getAmount().equals(0.0)) {
            throw new NotValidInputException("the amount cannot be 0.0");
        }
        UserEntity lender = userRepo.find("name", iouRequest.getLenderId()).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + iouRequest.getLenderId() + "\" not found")
        );
        UserEntity borrower = userRepo.find("name", iouRequest.getBorrowerId()).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + iouRequest.getBorrowerId() + "\" not found")
        );
        updateLender(lender, iouRequest.getBorrowerId(), iouRequest.getAmount());
        updateBorrower(borrower, iouRequest.getLenderId(), iouRequest.getAmount());
        userRepo.update(lender);
        userRepo.update(borrower);
        return List.of(lender, borrower);
    }

    private void updateLender(UserEntity lender, String nameOfBorrower, Double amount) {
        lender.getOwed_by().put(nameOfBorrower, amount);
        Double totalOwedBy = lender.getOwed_by().values().stream().reduce(0.0, Double::sum);
        Double totalOwes = lender.getOwes().values().stream().reduce(0.0, Double::sum);
        lender.setBalance(totalOwedBy - totalOwes);
    }

    private void updateBorrower(UserEntity borrower, String nameOfLender, Double amount) {
        borrower.getOwes().put(nameOfLender, amount);
        Double totalOwedBy = borrower.getOwed_by().values().stream().reduce(0.0, Double::sum);
        Double totalOwes = borrower.getOwes().values().stream().reduce(0.0, Double::sum);
        borrower.setBalance(totalOwedBy - totalOwes);
    }

}
