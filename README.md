# The Computer Networks - Assignment 1: Chat Application

>This Assignment's Requirements are: 
>* Building a chat application using the TCP/IP Protocol.
>* Can allow 2 users on 2 different machines to chat with each other.
>* The user can chat with different people at the same time.
>* The Chat Client is built on the hybrid model between client - server and P2P model:
>  * The system has a central server for user registration and online management.
>  * Client chat directly each other without the activation of the server.
>* User can view online/offline friend.
>* User can add or remove friend in the friend list.

# The Content of Code:
>**Here is the explaination for the code.**

## **1. Data:**

## **2. DataServer:**
This package is used for storing the information of all users including their information account and chat log between other users.
It also has 4 small packages inside.

   **Data Package:**
  
   **1.	Account.java:**
   
    This class used for setting up the user account including the friend list, friend request, username, password; ip, port of his/her own p2p server and status (online or offline).
    
   **2.	Chatlog.java:**
   
    This class will create the chat log of the user and others, each pair of them has a unique id to distinct with other users.
    
   **3.	LogDB.java:**
   
    This class will create and get the json file (with the ID) to store the chat log.
    
   **4.	UserDB.java:**
   
    This class will create the json and also get the json file for creating the user account information.

  **Server:**
  
  **1.	DataServer.java:**
  	
    This class used for operating the data server.
   
  **2.	DataThread.java:**
  	
    This class used for handling multi-users access to the data server and use the application.

  **Utils:**
  
  **1.	Checker.java:**
  	
    This class is used for checking the data from user satisfying certain condition to do something.
  	If something is wrong, it will return the error to Handling.
   
  **2.	Handling.java:**
  
    This class is used to categorizing the data from user, each type call a method from Checker.java to get the result.
  	If result is an error, it will return that error to the user.
   
  **3.	MakePath.java:**
  	
    Same as MakePath.java in P2PServer
   
  **4.	ServerUtils.java:**
  	
    This class converts some data of a kind to another kind.


## **3. GUI:**

This package contains 7 classes which are:
  * **ChooseChatMode:** *(Have 2 Chat Mode: First is Chat 1 - 1 and the second one is Chat Group).*
  * **FriendChat:** This is The Chat 1 - 1 UI.
  * **FriendList:** Display the Friend List UI after choosing Chat Mode.
  * **GroupChatGui** (optional class):
  *The future feature in the chat application. Coming soon.*
  * **Login:** Show the login UI when the user runs the application.
  * **Regis:** If the user doesn't have an account, provides the UI for the user to fill the information and create new account.
  * **Request:** Show the Friend Request UI.
  
  **Note:** All of them contains the GUI and the set up event actions for every interaction between the user and the chat application.
  
## **4. P2PServer:**

This package contains 4 classes which are:
  * **DataSocket:**
  
        This class has the constructor used for connecting with the server to get the data and the rest is the methods which used for interacting with the database in the server.

  * **MakePath:**
  
        This class will create the folder or file for storing data (in P2P side, storing files) 
  
  * **P2PServer:**
  
        This class is used for making the P2P server for the client to use the application.
  
        The rest is the methods which are used for modifying the User list in the server such as removeUser, addUser, findUser, also used for forwarding file and send the message.

  * **P2PThread:**
  
        This class used for multi thread programming to handle with many users use the server also the user can chat, send file, receive with other users at the same time.
  
## **5. Utils:**

This package contains all images used for setting up buttons, custom fonts and classes (override classes in JFrame) in the GUI, also utils classes which are:
* **ErrorCatching:** Pop up the JOptionPane to let the user know there is a problem occurs.
* **Converter.**
* **JTextFieldLimit.**

# Results:
## What We Can Do:
>* The user can chat directly with each other without the activation of the server.
>* 2 users can chat on 2 different machines with each other.
>* The user can chat with different people at the same time.
>* The system has a central server for user registration and online management.
>* Users can add or remove friend in the friend list.
>* Users can add or remove friend request in the request list.
>* The system can detect the duplicated account while the user create new account.
>* The system can store the chat log between users while the server is activated.
>* The system can check if you add yourself or not.
>* If you intentionally add an account twice, the system will announce you.

**More details:** 
[Report of The Assignment](https://tinyurl.com/CNAssOne)

## What We Need To Improve:
>* Need an online server for easier accessing the application.
>* The time of transferring file still has a delay.
>* Has small bug in the display Online/Offline Friend in the Friend List.

## In the future:
>* More functionals such as: Chat Group
>* Fix bugs and improve the performance.

# Contributors:

|Name            | Work                                                                                   |
|:---------------|----------------------------------------------------------------------------------------|
|**Hoàng Thịnh** | Back-End, Rearrange Code, Final Quality Control on Documentation (Report)              |
|**Hạ Mỹ**       | Front-End, Final Quality Control on Design GUI, Documentation (Report and Git)         |
|**Hoàng Trí**   | Front-End, Documentation (Report)                                                      |

# Disclaimer:
## The Custom Classes and Fonts:
Those custom classes which are:
* RoundedBorder (Peter Mortensen & Lalchand)
* RoundJTextField (Harry Joy)

Belong to the StackOverflow's users.

## The Custom Fonts:
Those fonts below belong to the owner itself.
* Caranda
* Gabriola


> All The Code **except the custom classes above** belong to The Lazy Team.
>
> Copyright &copy; 2019, Trần Hoàng Thịnh, Lai Nguyễn Hạ Mỹ, Hoàng Đức Trí.

