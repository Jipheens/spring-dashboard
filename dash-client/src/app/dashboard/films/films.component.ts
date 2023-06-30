import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-films',
  templateUrl: './films.component.html',
  styleUrls: ['./films.component.css'],
})
export class FilmsComponent implements OnInit {
  films: any[] = [];

  ngOnInit() {
    this.fetchFilms();
  }

  async fetchFilms() {
    try {
      const response = await axios.get('https://swapi.dev/api/films');
      this.films = response.data.results.slice(0, 12).map((film: any) => {
        return {
          title: film.title,
          director: film.director,
          releaseDate: film.release_date,
          openingCrawl: film.opening_crawl,
          image: this.getRandomFilmImage(),
        };
      });
    } catch (error) {
      console.error(error);
    }
  }

  getRandomFilmImage() {
    const imageNumber = Math.floor(Math.random() * 6) + 1;
    return `https://starwars-visualguide.com/assets/img/films/${imageNumber}.jpg`;
  }
}
