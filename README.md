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
  * **MakePath:**
  * **P2PServer:**
  * **P2PThread:**
  
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
[Report of The Assignment](https://tinyurl.com/CNAssignmentOne)

## What We Need To Improve:
>* Need an online server for easier accessing the application.
>* The time of transferring file still has a delay.
>* Has small bug in the display Online/Offline Friend in the Friend List.

## In the future:
>* More functionals such as: Chat Group
>* Fix bugs and improve the performance.

# Contributors:

|Name            | Work                                                                                           |
|:---------------|------------------------------------------------------------------------------------------------|
|**Hoàng Thịnh** | Back-End, Rearrange Code, Final Quality Control on Documentation (Report)                      |
|**Hạ Mỹ**       | Front-End, Final Quality Control on Design GUI, Documentation (Report and Git)         |
|**Hoàng Trí**   | Front-End, Documentation (Report)                                                              |

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

