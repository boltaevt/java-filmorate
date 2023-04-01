DROP TABLE IF EXISTS filmsNew, films, UserTable, Friendship,
 Likes, Film, MPA, Genre, Film_genre CASCADE;


CREATE TABLE UserTable (
        id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        email varchar (100) NOT NULL,
        login varchar (70) NOT NULL,
        name varchar (50) NOT NULL,
        birthday date
);


CREATE TABLE Genre
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE MPA (
        mpa INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name varchar(200) NOT NULL
);

CREATE TABLE Film (
        id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        customName varchar (50) NOT NULL,
        description varchar (200) NOT NULL,
        releaseDate date,
        duration integer,
        mpaFilm integer references MPA (mpa)
);


CREATE TABLE film_genre
(
    filmId INTEGER NOT NULL REFERENCES film (id),
    genreId INTEGER NOT NULL REFERENCES genre (id),
    PRIMARY KEY (filmId, genreId)
);


CREATE TABLE Friendship (
        id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        userId integer references UserTable (id),
        userFriendId integer references UserTable (id),
        status boolean default false
);


CREATE TABLE likes
(
    filmId INTEGER NOT NULL REFERENCES film (id),
    userId INTEGER NOT NULL REFERENCES UserTable (id),
    PRIMARY KEY (filmId, userId)
);



