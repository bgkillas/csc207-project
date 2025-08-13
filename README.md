# **S-Buddify - Meet Your Musical Match**

## **Authors and Contributors**

**Team Members:**
- **Joonie** - [@orasaff](https://github.com/orasaff)
- **Mikael** - [@bgkillas](https://github.com/bgkillas) 
- **Jamie** - [@JamieXW](https://github.com/JamieXW)
- **Ruhani** - [@ruhaniwalia14](https://github.com/ruhaniwalia14)
- **Clementine** - [@Clemi0](https://github.com/Clemi0)

---

## **Table of Contents**

- [Project Summary](#project-summary)
- [Features](#features)
- [Installation Instructions](#installation-instructions)
- [Usage Guide](#usage-guide)
- [Architecture Overview](#architecture-overview)
- [License](#license)
<!--- [Contributing](#contributing)-->
<!--- [Feedback](#feedback)-->

---

## **Project Summary**

**S-Buddify** is a Java-based social music friend finder platform that connects users based on their musical preferences. The application integrates with Spotify's API to analyze users' listening habits and matches them with others who have similar music tastes.

### **What This Project Does**
S-Buddify provides a comprehensive social platform where users can:
- Connect their Spotify accounts to analyze their music taste
- Set up a unique and personalized profiles with information and a profile picture
- Get matched with other users based on shared musical interests
- Create and share posts about music
- Comment on other users posts
- Build a social network of music enthusiasts
- Filter potential matches based on age, location, and other preferences

### **Why This Project Was Made**
This project was developed to solve the challenge of finding like-minded music enthusiasts in a digital age. Many music lovers struggle to discover new people who share their musical tastes, and traditional social platforms don't focus on music compatibility. S-Buddify bridges this gap by using actual listening data to create meaningful connections.

### **Problem It Solves**
- **Music Discovery**: Helps users find people with similar music tastes
- **Social Connection**: Creates a community of like-minded music enthusiasts
- **Experience Sharing**: Enables users to share and discuss their musical experiences, concerts, and discoveries with friends
- **Data-Driven Matching**: Uses real Spotify listening data instead of self-reported preferences
- **Filtered Experience**: Allows users to set preferences for age, location, and other criteria

---

## **Features**

### **Spotify Integration**
- **Automatic Profile Creation**: Connects to your Spotify account to automatically populate your music preferences.
- **Top Tracks Analysis**: Analyzes your most-listened tracks, genres, and artists to understand your music taste.

**Example (programmatic usage & output)**
```java
// Example: fetch Spotify data used for matching (pseudo-usage)
SpotifyInterface spotify = session.getSpotify(); // Get the SpotifyInterface instance from the current user session
spotify.initSpotify(); // Start Spotify authentication process (opens browser for user login)
spotify.pullUserData(); // Retrieve the logged-in Spotify user's ID and display name
spotify.pullTopArtistsAndGenres(); // Retrieve the user's top artists and genres from Spotify
spotify.pullTopTracks(); // Retrieve the user's top tracks from Spotify

System.out.println("User: " + spotify.getUserName() + " (" + spotify.getUserId() + ")");
System.out.println("Top Artists: " + spotify.getTopArtists());
System.out.println("Top Tracks: " + spotify.getTopTracks());
System.out.println("Top Genres: " + spotify.getTopGenres());
```
**Output**
```text
User: Alice (user_12345)
Top Artists: [Taylor Swift, The Weeknd, Coldplay, Joji, Ed Sheeran]
Top Tracks: [Cardigan, Blinding Lights, Yellow, Glimpse of Us, Shivers]
Top Genres: [pop, indie pop, alt-pop, pop, pop rock, indie pop]
```

### **User Matching System**
- **Artist & Genre Matching**: Compares users’ favourite artists and genres.
- **Compatibility Scoring**: Computes a numeric score from overlap in artists and genres.
- **Filtered Matching**: Set preferred age range, gender, and location for potential matches.

**Example (programmatic usage & output)**
```java
// Assume we have a logged-in session
UserSession session = ...;

// Get current user and all registered users
User currentUser = session.getUser();
List<User> allUsers = session.getAllUsers();

// Set the current user's match filter
SetupMatchFilterInteractor setupFilter = new SetupMatchFilterInteractor(
        filter -> System.out.println("Filter saved: " + filter),
        session
);
setupFilter.setupFilter(20, 30, "female", "Toronto");

// Find candidates that pass both users' filters
MatchService matchService = new MatchServiceImpl(); // no-arg constructor
List<User> candidates = matchService.findMatches(currentUser, allUsers);

// Display compatibility scores (calculated separately)
MatchCalculatorImpl calculator = new MatchCalculatorImpl();
System.out.println("=== Match Candidates ===");
for (User u : candidates) {
    int score = calculator.calculateCompatibilityScore(currentUser, u);
    System.out.println(u.getName() + " — " + score + "%");
}
```
**Output**
```text
Filter saved: MatchFilter{minAge=20, maxAge=30, gender='female', location='Toronto'}
=== Match Candidates ===
Diana — 92%
Eric — 87%
Alice — 85%
```

### **Matching Interaction**
- **Profile Browsing** – View profiles of potential matches, including name, age, location, biography, and compatibility score in the matchingroom.  
- **Connect or Skip** – Choose to connect with or skip a potential match. Clicking **Connect** sends a friend request to the other user's mailbox. Clicking **Skip** removes the other user from your matching queue.  
- **Handle Incoming Requests** – Receive friend requests from other users in your mailbox. Click **Accept** to add them to your friend list, or **Decline** to remove them from your matching queue.

**Example (programmatic usage & output)**

```java
// Presenter stub for README/demo purposes
MatchInteractionOutputBoundary presenter = data ->
        System.out.println("[Presenter] " + data.getMessage());

// Construct the interactor with your app's dependencies
MatchInteractionInputBoundary matcher = new MatchInteractionInteractor(
        matchDataAccessObject,          // MatchDataAccessInterface
        handleFriendRequestInteractor,  // HandleFriendRequestInputBoundary
        addFriendListInteractor,        // AddFriendListInputBoundary
        presenter
);

// Assume we have a logged-in session and a candidate user
UserSession session = ...;
User matchedUser = ...;  // a user from your match candidates

// Connect flow (non‑mutual): sends a friend request to matchedUser
matcher.connect(session, matchedUser);

// Skip flow: removes the user from incoming/outgoing match queues
matcher.skip(session, matchedUser);

// Simulate that 'matchedUser' has already sent a request to 'currentUser'
User currentUser = session.getUser();
matchDataAccessObject.getOutgoingFriendRequest(matchedUser).add(currentUser);

// Now connect — should immediately become friends
matcher.connect(session, matchedUser);
```
**Output**
```text
[Presenter] Friend request sent to Diana
[Presenter] You are now friends with Diana
```


### **Social Features**
- **Post Creation**: Share your thoughts about music, concerts, or new music discoveries
- **Comment System**: Engage with other users' posts
- **Friend Requests**: Send and manage friend requests
- **Blocking System**: Block users you don't want to interact with
**Example (programmatic usage)**
```java
// Prepare a DataAccessObject 
PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();
CreatePost createPost = new CreatePostInteractor(postDataAccessObject);

// Build an optional image file (can be null)
File cover = new File("cover.jpg"); // will be loaded if exists

// Authoring user
User author = currentUser; // assume you already have a logged-in user

// Create a post
createPost.createPost(
        "Found a great indie band!",
        "Check out their latest single—warm guitars and dreamy vocals.",
        cover, 
        author
);
```

### **User Profiles**
- **Comprehensive Profiles**: Age, location, bio, and music preferences
- **Profile Pictures**: Upload and manage profile images
- **Music Statistics**: View your top artists, tracks, and genres
- **Privacy Controls**: Manage who can see your profile


### **Advanced Features**
- **Responsive UI**: Clean, intuitive Java Swing interface


---

## **Installation Instructions**

### **Prerequisites**

Before installing S-Buddify, ensure you have the following software installed:

- **Java Development Kit (JDK) 17** or higher
  - Download from: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
  - Verify installation: `java -version`
- **Maven 3.6+** 
  - Download from: [Apache Maven](https://maven.apache.org/download.cgi)
  - Verify installation: `mvn -version`
- **Spotify Account**
  - Required for music analysis features
  - Free account is sufficient

### **System Requirements**
- **Operating System**: Windows 10+, macOS 10.14+, or Linux
- **Internet Connection**: Required for Spotify API integration

### **Installation Steps**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/csc207-project.git
   cd csc207-project
   ```

2. **Verify Java Installation**
   ```bash
   java -version
   # Should show Java version 17 or higher
   ```

3. **Verify Maven Installation**
   ```bash
   mvn -version
   # Should show Maven version 3.6 or higher
   ```

4. **Build application**
   ```
   mvn package
   ```

4. **Run application**
   ```
   java -jar target/csc207-project-1.0-SNAPSHOT.jar 
   ```

<!--### **Common Installation Issues**

// -->

---

## **Usage Guide**
### **Getting Started**

1. **Launch the Application**
   - Run the application by running Main
   - The window will appear with Login View
     
2. **Connect Spotify**
    - Click "Continue With Spotify"
    - A browser window will open for Spotify authorization
    - Login to Spotify and grant permissions to access your listening data
![Login Demo](https://github.com/bgkillas/csc207-project/blob/main/assets/login.gif)

4. **Setup Your Profile**
   - Sign up and login
   - Fill in your basic information (name, age, location)
   - Upload a profile picture to your account (Optional)
![Set Up Profile Demo](https://github.com/bgkillas/csc207-project/blob/main/assets/setupprofile.gif)
5. **Set Up Match Filters**
   - Configure your matching preferences (age range, location, etc.)
   - These filters will help find compatible matches
![Set Up Match Filter](https://github.com/bgkillas/csc207-project/blob/main/assets/matchfilter.gif)

### **Using the Matching System**

1. **Access Matching Room**
   - Navigate to the "Matching" section
   - View potential matches based on your preferences
   - Connect or Skip potential matches
![Matching Flow Demo](https://github.com/bgkillas/csc207-project/blob/main/assets/matchingroom.gif)

2. **Browse Profiles**
   - See compatibility scores with each potential match
   - View shared music interests and preferences

3. **Send Friend Requests**
   - Click the Connect button to send connection requests
   - Manage incoming and outgoing requests
![Handle Connect Request Demo](https://github.com/bgkillas/csc207-project/blob/main/assets/connectrequestview.gif)

### **Creating and Sharing Posts**

1. **Create a Post**
   - Navigate to the "Share" section
   - Write about music, concerts, or discoveries
   - Add images if desired

2. **Engage with Posts**
   - Browse posts from other users
   - Add comments and interact with the community

### **Managing Your Profile**

1. **Update Profile**
   - Access your profile through "My Profile"
   - Update personal information and preferences

2. **Privacy Settings**
   - Control who can see your profile
   - Manage friend and block lists

### **Debug Features**

The application includes a comprehensive debug menu for developers:
- **User Management**: Create and manage test users
- **Session Testing**: Test various application states
- **API Testing**: Verify Spotify integration

---

## **Architecture Overview**

S-Buddify follows the **Clean Architecture** pattern with clear separation of concerns:

```
src/
├── main/
│   └── java/
│       └── app/
│           ├── entities/                  # Core business logic, independent of external dependencies
│           ├── usecase/                   # Application-specific business rules
│           │   ├── login/                 # Individual use cases organized by features
│           │   ├── matching/
│           │   ├── friend/
│           │   ├── post/
│           │   └── profile/
│           ├── interface_adapter/         # Transform data between use cases and UI
│           │   ├── controller/
│           │   ├── presenter/
│           │   └── viewmodel/
│           ├── frameworks_and_drivers/    # External systems (UI, data access, APIs)
│           │   ├── view/                  # Java Swing GUI components
│           │   ├── data_access/          # In-memory and persistent data access
│           │   └── external/
│           │       └── spotify/          # Spotify API integration
│           └── Main.java                 # Application entry point
│
├── test/
│   └── java/
│       └── app/
│           ├── entity_tests/              # Unit tests for entity classes
│           ├── usecase_tests/             # Unit tests for use case interactors
│           ├── controller_tests/          # Unit tests for controllers
│           ├── presenter_tests/           # Unit tests for presenters
│           └── integration_tests/         # Tests spanning multiple components
```

### **Key Technologies**
- **Java 17**: Programming language
- **Java Swing**: User interface framework
- **Maven**: Build and dependency management
- **JUnit 5**: Testing framework
- **Spotify Web API**: Music data integration
- **JSON**: Data handling

---

<!--## **Contributing**

### **How to Contribute**-->

## **License**
This project is licensed under the [MIT License](./LICENSE).  
You are free to use, copy, modify, and distribute this code for academic and personal use, under the terms of the license.


















