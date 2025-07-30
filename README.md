List of Team Members and Respective GitHub Usernames: 

Joonie - orasaff

Mikael - bgkillas

Jamie - JamieXW

Ruhani - ruhaniwalia14

Clementine - Clemi0

src/
├── main/
│   └── java/
│       └── app/
│           ├── entities/                  # core business logic, indep of external dependencies (we have exceptions tho)
│           ├── usecase/                   # application-specific business rules
│           │   ├── login/                 # these are individual use cases. organized based of features.
│           │   ├── matching/
│           │   ├── friend/
│           │   ├── post/
│           │   └── profile/
│           ├── interface_adapter/         # transform data between the use cases and the U
│           │   ├── controller/
│           │   ├── presenter/
│           │   └── viewmodel/
│           ├── frameworks_and_drivers/    # all external systems such as UI components, data access layers, and third-party APIs.
│           │   ├── view/                  # Java Swing GUI components
│           │   ├── data_access/          # in-memory and persistent data access implementations
│           │   └── external/
│           │       └── spotify/          # integration with Spotify API
│           └── Main.java                 # application entry point
│
├── test/
│   └── java/
│       └── app/
│           ├── entity_tests/              # Unit tests for entity classes
│           ├── usecase_tests/             # Unit tests for use case interactors
│           ├── controller_tests/          # Unit tests for controllers
│           ├── presenter_tests/           # Unit tests for presenters
│           └── integration_tests/         # Optional: tests spanning multiple components