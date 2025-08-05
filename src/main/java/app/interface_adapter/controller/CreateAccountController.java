package app.interface_adapter.controller;

import app.usecase.create_account.CreateAccountInputBoundary;

public class CreateAccountController {
    private final CreateAccountInputBoundary interactor;

    public CreateAccountController(CreateAccountInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createAccount() {
        interactor.create();
    }
}
