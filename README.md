lab-linked-lists
================

Name: Candice Lu
Date: April 17th, 2024
Github: https://github.com/lucandic/lab-linked-lists.git
Description: A circular, doubly-linked list which is designed with an invisible dummy node to simplify implementation by eliminating edge cases, with implementation of class Node2 and SimpleDLL, as well as experiments and tests.
Resources: Mini project 8 reading, linked-list reading
Acknowledgement: The original author of this lab is Samuel A. Rebelsky
Peer support: Elene Sturua, Alyssa Trapp, Julian Kim, Shibam Mukhopadhyay

**How does a dummy node change the implementation?**

It simplifies implementation eliminates the edge cases of empty lists, adding in front of the list, removing at the end of the list, etc. As long as we ensure that we stop once we encounter the dummy node using hasNext() or hasPrevious(), the dummy node is invisible to the user but makes implementation easier. Other than that, overall, the implementation of circular, doubly-linked list is very similar to that of simple doubly-linked list.
