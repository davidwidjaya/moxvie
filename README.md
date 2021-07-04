# Moxvie
Mobile application for showing trends and popular updates about movie / tv shows.


# App Features
- <b>List of Movies</b> - show all movies from API
- <b>List of Tv Shows</b> - show all tv shows from API
- <b>List of Favorited Movies</b>  - show all favorited movies which saved in local database like room.
- <b>List of Favorited Tv Shows</b> - show all favorited tv shows which saved in local database like room.

<br/>

# Screenshot
Splash Screen | Home Screen | Detail Screen
------------ | ------------- | -------------
![Splash Screen](https://github.com/davidwidjaya/moxvie/blob/main/screenshot/splash-screen.png) | ![Home Screen](https://github.com/davidwidjaya/moxvie/blob/main/screenshot/home-movie-screen.png) | ![Detail Screen](https://github.com/davidwidjaya/moxvie/blob/main/screenshot/detail-screen.png)

<br/>
<br/>


# Documentation of API Used
The documentation of open source API that used for this project :
https://developers.themoviedb.org 

## Base URL :
> https://api.themoviedb.org/3/

## Image URL :
> https://api.themoviedb.org/

## Generate API Key for Authentication :
> https://www.themoviedb.org/settings/api

## Endpoint Used
Method | Endpoint | Usage
------------ | ------------- | ------------
GET | `{image_url}/t/p/w500/{file_path}` | Get Image
GET | `{base_url}/movie/popular?api_key={your_api_key}` | Get Movie Popular
GET | `{base_url}/tv/popular?api_key={your_api_key}` | Get TV Popular
GET | `{base_url}/search/tv?api_key={your_api_key}&query={your_query}` | Search TV
GET | `{base_url}/search/movie?api_key={your_api_key}&query={your_query}` | Search Movie

### How to Use

> https://api.themoviedb.org/3/search/movie?api_key=kqjL17yufvn9OVL&query=Naruto

<br/>
<br/>

# Library Used
Library | Sources
------------ | -------------
Retrofit | https://square.github.io/retrofit/
Picasso | https://square.github.io/picasso/
Volley | https://developer.android.com/training/volley
Room | https://developer.android.com/training/data-storage/room
