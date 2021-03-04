# ImdbApi
An open-source Java Rest API for IMDb that lets you search and query movies, shows, episodes and all other titles from IMDb.

IMDb has no public API and only provides their data in a few big files. This is a free solution that lets you search and get info about IMDb titles (Movies, TV Shows, etc.).

Currently, this doesn't support searching people, but it might in the future.

If you end up using this, please let me know either through discord (https://discordapp.com/users/217353364076232705) or through email (yoavwolfdw@gmail.com) and feel free to also let me know if you have any issues/bugs.

# Usage
 
This API can be used through HTTP requests with the endpoints provided in the documentation, or directly by dumping the source code in your java project.

# Setup
The entire source code is available in this repo and you can clone it and compile/run it locally.

Alternatively, you can use the jar provided in the releases tab: https://github.com/WolfDWyc/ImdbApi/releases/

The API is not currently hosted anywhere publicly, but if there's any need for it to let me know in the issues tab and I'll consider it.
 
# Documentation
 Full documentation for usage of all endpoints with examples is available here: https://app.gitbook.com/@yoavwolfdw/s/imdbapi/
 
 # Disclaimers
 
 - For the time being, this doesn't sync with IMDb automatically. IMDb updates their files every day, and in the future there might be an "auto-update" mode, but for now, it will only have data from the time you started running it at.
 - For performance reasons, the data is kept in memory and not in any file. For that reason, the jar can take a lot of memory in your system while running. (~4-5GB). If I ever add support for searching people, or the dataset gets bigger for some reason - I might change this. 
 
 

 
 
