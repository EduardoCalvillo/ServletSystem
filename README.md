# ServletSystem
This is the latest version of ChatSystem, that implements a centralized architecture using a server to manage the network notifications.

However, we as a team consider this version is not what was asked for. We ask you to refer to our previous version [Version 1.02](https://www.github.com/Kuro10/ChatSystem.git) for our better system.


ServletSystem, also known as ChatSystem V1.02, uses the same system but changes it's internal structure to allow a centralization, this has as a consequence 2 things:

  1. There is a severe reduction on the number of broadcast messages sent through the network.
  
  2. Almost all communication is dependant on the server's well-being, so if the server crashes, the system cannot operate functionally.
# How it works
ChatSystem V1.02 uses a server to manage all notifications sent through the network as UDP packets. Once an user connects to the system, he sends a notification to the server, subscribing to him; the server, on the other hand, accepts the new user and adds him into the list of actual subscribers. The server then notifies everyone in the subscriptors list about this change.

The server is able to notify to all of its subscribers:
  * Connections.
  * Disconnections
  * Name Changes
  
The communication between users stayed the same as in version 1.02, so we won't go into detail about it here. If you need more information, feel free to visit the previous Github repository hyperlink at the start of the document.
# Download the program
You can download ChatSystem v1.01 and view some extra information by clicking the "Releases" button on the bar alongside commits and contributors, or you can follow this [link](https://www.github.com/EduardoCalvillo/ServletSystem/releases) 
