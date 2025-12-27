package org.ggalantecode.validator;

import org.ggalantecode.exceptions.NotValidInputException;
import org.ggalantecode.model.CreateIouRequest;

public class IouValidatorImpl implements IouValidator {

    @Override
    public void validateIouRequest(CreateIouRequest iouRequest) {
        checkAmount(iouRequest.getAmount());
        compareLenderAndBorrower(iouRequest.getLenderId(), iouRequest.getBorrowerId());
    }

    private void compareLenderAndBorrower(String lenderId, String borrowerId) {
        if (areLenderAndBorrowerTheSamePerson(lenderId, borrowerId)) {
            throw new NotValidInputException("lender and borrower cannot be the same person...");
        }
    }

    private boolean areLenderAndBorrowerTheSamePerson(String lender, String borrower) {
        return lender.equals(borrower);
    }

    private void checkAmount(Double amount) {
        checkIfAmountIsZero(amount);
    }

    private void checkIfAmountIsZero(Double amount) {
        if (isZero(amount)) {
            throw new NotValidInputException("the amount cannot be 0.0");
        }
    }

    private boolean isZero(Double amount) {
        return amount == 0.0;
    }
}
