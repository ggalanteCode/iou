package org.ggalantecode.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.ggalantecode.entity.UserEntity;
import org.ggalantecode.exceptions.NotValidInputException;
import org.ggalantecode.exceptions.UserNotFoundException;
import org.ggalantecode.repository.UserRepository;

import java.util.*;

@ApplicationScoped
public class IouService {

    @Inject
    UserRepository userRepo;

    public List<UserEntity> getAllUsers() {
        return userRepo.listAll(Sort.by("name"));
    }

    public List<UserEntity> getUsersByName(List<String> users) {
        return userRepo.list("{'name':{$in: [?1]}}", Sort.by("name"), users);
    }

    public UserEntity createUser(UserEntity user) {
        Map<String, Double> owes = new HashMap<>(), owedBy = new HashMap<>();
        user.setOwes(owes);
        user.setOwed_by(owedBy);
        user.setBalance(0.0);
        userRepo.persist(user);
        return user;
    }

    public List<UserEntity> createIou(String lender, String borrower, Double amount) {
        if(lender.equals(borrower)) {
            throw new NotValidInputException("lender and borrower cannot be the same person...");
        }
        if(amount.equals(0.0)) {
            throw new NotValidInputException("the amount cannot be 0.0");
        }
        UserEntity lenderEntity = userRepo.find("name", lender).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + lender + "\" not found")
        );
        UserEntity borrowerEntity = userRepo.find("name", borrower).firstResultOptional().orElseThrow(
                () -> new UserNotFoundException("user \"" + borrower + "\" not found")
        );
        updateLender(lenderEntity, borrower, amount);
        updateBorrower(borrowerEntity, lender, amount);
        userRepo.update(lenderEntity);
        userRepo.update(borrowerEntity);
        return List.of(lenderEntity, borrowerEntity);
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
