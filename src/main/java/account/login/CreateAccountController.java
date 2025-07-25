package account.login;

import usecase.team_story.CreateAccountInputBoundary;

public class CreateAccountController {
    private final CreateAccountInputBoundary interactor;

    public CreateAccountController(CreateAccountInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createAccount(String spotifyUsername) {
        interactor.create(spotifyUsername);
    }
}
