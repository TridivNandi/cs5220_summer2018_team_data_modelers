# cs5220_summer2018_team_data_modelers
This repository contains the codebase for cs5200 project - 'InstaSongs'. The backend of the project is a Java SpringBoot Application  whereas the front-end is an Android Application. MySQL is the relational database that has been used for persistence. The entire application has been deployed in AWS.

[Link to Youtube demo for android app](https://youtu.be/wBnI2rZGlRk)

[Link to Android github repo](https://github.com/vaibhavs4424/cs5200-InstaSongs-Android)

# Project Description

## Problem Statement:
In modern times when everyone resorts to online music player applications, finding a consolidated collection of songs, albums and artists is a big challenge. Moreover, as mobile and desktops have limited storage capacities, online music libraries provide an efficient solution to listening songs at any point in time. We have tried to solve this problem by making a one stop destination for all music lovers --- InstaSongs.

## Proposed Solution:
In an attempt to solve the above mentioned problem, our application provides a online music library where users can stream music and don't need to download songs in their devices. We also provide the opportunity to search songs in a particular album and also by specific artists. Users can also create their own playlists, follow other artists. Critics can review and rate particular songs and albums and users can follow critics to see all their ratings and reviews. Artists can login, authorize themselves and see reviews and ratings for their songs by critics.

## Domain Objects:
The domain objects relevant to our application are as follows:

* Songs : Songs are the integral part of our application. They represent the tracks which all the users can listen to.
* Albums : Albums are collection of songs which are released by an artist or a band. It can contain one or many songs.
* Playlists : Playlists are a list of songs that are generally created by users. Users can add songs of their own choice to form a playlist.
* Reviews : Reviews are provided by the critics. Reviews describe the opinion of the critics about a particular song or album.

## Human Users:
The human users relevant to our application are as follows:

* Users : Users are the one who uses our application to listen to music.
* Artists : Artists are generally musicians or other music professionals.
* Critics : Critics are reviewers for songs and albums.
* Admin: Administrator of our system are the ones having all the rights to manage our application.

## Goals for human users:

User:

1. Users can login to our application.
2. Users can listen to music.
3. Users can follow artists.
4. Users can like songs.
5. Users can create and manage playlists.
6. Users can see reviews by critics.

Artist:

1. Artists can see followers.
2. Artists can follow other artists.
3. Artists can see reviews and ratings.
4. Artists can authorize themselves as legitimate artists

Critics:

1. Critics can add reviews to songs.
2. Critics can follow artists.
3. Critics can rate songs.
4. Critics can listen to songs.

Admin:

1. Admin can perform CRUD operations on users.
2. Admins can perform CRUD operations on artists.
3. Admins can perform CRUD operations on critics.
4. Admins can delete reviews.







