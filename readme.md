IBA Tech Academy final project - HANDY by Ayshan Rzayeva || Shafa Mammadova || Leman Javadova || Emin Israfilzadeh

Deployed link:
https://handy-iba.herokuapp.com/

Minimum data to be able to run project:

Role table: USER
Category table: Any category
Post table: Post with ID 0, CategoryId 1, status false, other fields can be left empty.

All credentials are taken from environment variables. Please contact authors for credentials.


Short information about project.
Handy is intended to connect people who want to share their stuff they don't need. Users can share these stuff and other users can contact them if they want to get those items.

The most important featuers and pages:

Registration    - only email and password is required.

Fill info       - Full name, city, phone number, profile picture is required.

Login           - Email and password required / Wrong input Exceptions have been handled.

Dashboard       - Users can see posts (10 posts in each page) by ALL users and can add them to their wishlist or contact author for getting them.

Manage posts    - Users can add new post, edit or delete (soft delete - posts are not deleted, just deactivated) previous posts.

Wishlist        - Users can see posts which they added into their wishlist and remove them if they need to.

Messages        - Users can send messages to each others if they want. Each message page fetches last 10 messages only. If users want to see all messages, they should click "See all messages" button.

Update profile  - Users can change their basic information. Due to security issues, Email or password can not be changed. If user want to change password, "Forgot password" feature can be used

Send feedback   - Users can send feedback about website or report any problem.

Forgot password - Users can restore their forgotten password via link sent to their mail.