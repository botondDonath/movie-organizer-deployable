# Movie Organizer

[Deployed version on Heroku](https://movie-organizer-app.herokuapp.com/)

## About

Movie Organizer is a school project that was created by two students in an effort to practice  
and deepen their knowledge about widely used frameworks and technologies.

## The app

The basic idea was to create a simple application, to which users can upload movies with some  
information about them: _title_, _release year_, _category_ tags, _characters_, _actors_, _short description_  
and/or _plot_. Either because they would like to watch them later, or because they  
have already watched it, and would't want to forget about them.

### v0.0.2 (current)

In this version, only logged-in users can upload new movies or access, edit and delete  
uploaded movies. However, they can do so without any respect to who uploaded it, so all movies  
are treated "globally".

## Technologies used

### Frontend

- [React.js](https://reactjs.org/)
- [Ant Design](https://ant.design/): provides UI components for React

### Backend

- Spring Boot
- Spring Data JPA
  - [PostgreSQL](https://www.postgresql.org/) database
- Spring Security
- [Java JWT](https://github.com/jwtk/jjwt)
  - using HttpOnly cookies
  
## Other notes

In this deployable version, the frontend code is inside the `static` folder of this Spring Boot app  
in unintelligible version, however, it can be found in its original, pre-build format here:
[Link to repo](https://github.com/zoltanJakubecz/movie-organizer-frontend)

## Creators

- [botondDonath](https://github.com/botondDonath)
- [zoltanJakubecz](https://github.com/zoltanJakubecz)
