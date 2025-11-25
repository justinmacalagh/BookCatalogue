# BookCatalogue
This is my book catalogue
1. Introduction
1.1. Welcome
Welcome to the Book Catalogue System! This application is designed to manage your book management like storing, updating and deleting a book. This manual will guide you through installing, accessing, and using all the features of the system.
1.2. System Overview
The Book Catalogue System is a web-based application built with a modern micro services architecture. It allows you to:
    • View a complete list of all your catalogued books.
    • Add new books to your collection.
    • Edit the details of existing books.
    • Delete books from your catalogue.
The system consists of two main parts:
    • Web Service (UI): The user-friendly interface you interact with in your web browser.
    • Management Service (API): The backend service that handles all data operations.
   
3. System Requirements & Installation
2.1. Prerequisites
Before you begin, ensure your computer meets the following requirements:
    • Operating System: Windows 10/11, macOS, or Linux.
    • Java: Java 8 or later must be installed. To check, open a Command Prompt (Windows) or Terminal (Mac/Linux) and type Java -version.
2.2. Installation Steps
    1. Step 1: Obtain the Application Package  
Download the BookCatalogue.zip file and extract it to a folder of your choice (e.g., C:\BookCatalogue\).
    2. Step 2: Run the Application
You have two options to start the system:
        ◦ Option A: Using the Automated Scripts (Recommended)
            ▪ For Windows: Navigate to the scripts folder and double-click the run-services.bat file.
            ▪ For Linux/macOS:
                1. Open a terminal.
                2. Navigate to the extracted scripts folder.
                3. Run the command: chmod +x run-services.sh
                4. Run the command: ./run-services.sh
                   
        ◦ Option B: Manual Startup
          Open a Command Prompt/Terminal and start the Management Service first:
          cd management-service
          java -jar target/management-service-1.0.0.jar
          Open a second Command Prompt/Terminal and start the Web Service:
              cd web-service
            java -jar target/web-service-1.0.0.jar
        ◦ 
          What the scripts do:
            ▪ Automatically build the projects (if needed).
            ▪ Start the Management Service on port 8081.
            ▪ Wait a few seconds, then start the Web Service on port 8080.
4. Accessing the System
Once both services are running successfully:
    •  Open your preferred web browser (e.g., Google Chrome, Mozilla Firefox, Microsoft Edge).
    •  In the address bar, type: http://localhost:8080/books
    •  You should see the main Book Catalogue interface.

5. Using the Book Catalogue
4.1. The Main Screen
When you first access the system, you will see the main screen divided into two sections:
    • Book Entry Form: Located at the top, used for adding new books or editing existing ones.
    • Book List Table: Located below the form, displaying all books in your collection. The table has the following columns: ID, Name, ISBN, Publish Date, Price (ZAR), Type, and Actions.
4.2. How to Add a New Book
Ensure the form header says "Add New Book". Fill in all the required information:
    • Name: The title of the book (e.g., "Dune").
    • ISBN Number: The unique International Standard Book Number (e.g., "9780441172719").
    • Publish Date: Use the date picker or enter the date in yyyy/MM/dd format.
    • Price (ZAR): The cost of the book in South African Rand (e.g., "350.00").
    • Book Type: Select the appropriate format from the dropdown menu (Hard Cover, Soft Cover, eBook, Audiobook).
Click the "Add Book" button.
Result: The new book will immediately appear in the book list table below. Below are the Step to add a book:
 Example via UI Step 1: Fill in the information about the book.
Step 2: Press Add Book. Once the add button is click the book will show in the book-list below
Example Via API (POSTMAN)
Step1 Add the book information in the body as JSON format like below. Use the http://localhost:8081/api/books link and POST option to add a book.
{
 "name": "The Great Gatsby",
  "isbnNumber": "9780743273565",
  "publishDate": "1925-04-10",
  "price": 10.99,
  "bookType": "Classic"
}

<img width="1500" height="837" alt="image" src="https://github.com/user-attachments/assets/a699022a-9194-46a8-be2e-73a729b0aadf" />


4.3. How to Edit an Existing Book
In the Book List table, find the book you wish to modify. Click the "Edit" button in the 'Actions' column for that book. The form will change to "Edit Book" mode, and all the existing information for that book will be loaded into the form fields. Make the necessary changes to the data. Click the "Update Book" button to save your changes. If you change your mind, click the "Cancel" button to return to the "Add New Book" form without saving. Below are the step to 
Step1: Click on the book you would like to edit. Then the information will load in the fields and then you can edit/update the book how you like to.

<img width="1357" height="741" alt="image" src="https://github.com/user-attachments/assets/78f58483-3eea-4040-baf7-837a5ed217f7" />

Below the book updates correctly 

<img width="1275" height="711" alt="image" src="https://github.com/user-attachments/assets/5c5948bb-7b68-497f-b130-dcf6fd31f22a" />



4.4. How to Delete a Book
Warning: This action is permanent and cannot be undone. In the Book List table, locate the book you want to remove. Click the "Delete" button in the 'Actions' column. A confirmation dialog may appear; confirm that you want to delete the book.
Result: The book will be immediately removed from the list.
Step1: Click on the book you would like to delete. 
<img width="1282" height="665" alt="image" src="https://github.com/user-attachments/assets/836840f7-c670-4a25-9c49-3ace9d6d0916" />



5. Troubleshooting
Problem	Possible Cause	Solution
"Cannot reach this page" when accessing http://localhost:8080/books	The services are not running.	Follow the installation steps in Section 2 to start both services.
Error when adding a book (e.g., "Book with ISBN... already exists")	The ISBN number you entered is already used by another book in your catalogue.	ISBN numbers must be unique. Check your entry or use a different ISBN.
Book details are not saved after clicking "Add Book"	A required field is empty or has invalid data.	Ensure all fields are filled out correctly. The 'Price' must be a number, and the 'ISBN' must be unique.
All my books are gone after restarting the application.	This is expected behavior. The system uses an in-memory database for simplicity.	The database resets every time the services are stopped and restarted. This is not suitable for a permanent collection without modification.
"Failed to create/update/delete book"	The Web Service cannot communicate with the Management Service.	1. Ensure the Management Service is running on port 8081.
2. Restart both services in the correct order.
6. Important Notes
Data Persistence: This version of the application uses an in-memory database. This means all your book data will be lost whenever you stop the application servers. It is designed as a demonstration of the technology stack.
Port Conflicts: If ports 8080 or 8081 are already in use by other applications on your computer, the services will fail to start. You may need to stop other applications using these ports or configure the services to use different ones (see technical configuration).
7. Technical Appendix
7.1. Service URLs
    • User Interface: http://localhost:8080/books
    • REST API: http://localhost:8081/api/books
    • H2 Database Console: http://localhost:8081/h2-console (JDBC URL: jdbc:h2:mem:bookdb, Username: sa, Password: [leave blank])
API Endpoints:
      GET /api/books - List all books
    • POST /api/books - Create a new book
    • PUT /api/books/{id} - Update a book
    • DELETE /api/books/{id} - Delete a book
7.2. Manual Build Commands (for developers)
# Build Management Service
      cd management-service
      mvn clean package

# Build Web Service
      cd web-service
      mvn clean package
