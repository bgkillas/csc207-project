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
- [Contributing](#contributing)
- [Feedback](#feedback)
- [License](#license)

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
- **Automatic Profile Creation**: Connects to your Spotify account to automatically populate your music preferences
- **Top Tracks Analysis**: Analyzes your most-listened tracks, genres, and artists to understand your music taste

### **User Matching System**
- **Artist Matching**: Identifies shared favorite artists with potential matches
- **Compatibility Scoring**: Algorithm that calculates match percentages based on music preferences
- **Filtered Matching**: Set age ranges, location preferences, and other criteria
- **Profile Browsing**: View detailed profiles of potential matches
- **Match Discovery**: Swipe through potential matches with detailed compatibility information

### **Social Features**
- **Post Creation**: Share your thoughts about music, concerts, or new music discoveries
- **Comment System**: Engage with other users' posts
- **Friend Requests**: Send and manage friend requests
- **Blocking System**: Block users you don't want to interact with

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

4. **Run the Application**
- Run Main

### **Common Installation Issues**

// 

---

## **Usage Guide**
### **Getting Started**

1. **Launch the Application**
   - Run the application by running Main
   - The main window will appear with Login View
     
2. **Connect Spotify**
    - A browser window will open for Spotify authorization
    - Login to Spotify and grant permissions to access your listening data

3. **Create Your Account**
   - Sign up and login
   - Fill in your basic information (name, age, location)
   - Upload a profile picture to your account (Optional)

4. **Set Up Match Filters**
   - Configure your matching preferences (age range, location, etc.)
   - These filters will help find compatible matches

### **Using the Matching System**

1. **Access Matching Room**
   - Navigate to the "Matching" section
   - View potential matches based on your preferences
   - Connect or Skip potential matches
![Matching Flow Demo](https://raw.githubusercontent.com/bgkillas/csc207-project/main/assets/matching_demo.gif)

2. **Browse Profiles**
   - See compatibility scores with each potential match
   - View shared music interests and preferences

3. **Send Friend Requests**
   - Click the Connect button to send connection requests
   - Manage incoming and outgoing requests

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
### **Accessibility Report**
Universal Principle of Design
- Principle 1: Equitable Use
---

## **Contributing**

### **How to Contribute**

## **License**
This project is licensed under the [MIT License](./LICENSE).  
You are free to use, copy, modify, and distribute this code for academic and personal use, under the terms of the license.






