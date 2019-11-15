# Monolithic-Messaging-With-Redis
Illustration of how redis can be used as a messaging broker in a monolithic app

When user post a blog, all his/her followers are notifified via emails about the new blog.

This is an simulated implementation of how Redis can be used to solve such scenario assuming we have 1000s of blogers and millions of subscribers.

Posting and actual emails sending omitted for brevity.

# How to run
1. Install redis
2. Run App as spring boot
3. Visit http://localhost:8080/simulate-post-blog and check IDE consoles for messages.
