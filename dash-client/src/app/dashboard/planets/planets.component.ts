import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Planet {
  name: string;
  climate: string;
  terrain: string;
  population: string;
  image: string;
}

@Component({
  selector: 'app-planets',
  templateUrl: './planets.component.html',
  styleUrls: ['./planets.component.css'],
})
export class PlanetsComponent implements OnInit {
  planets: Planet[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getPlanets();
  }

  async getPlanets() {
    try {
      const response = await this.http
        .get<any>('https://swapi.dev/api/planets')
        .toPromise();
      this.planets = response.results.slice(0, 12).map((planet: any) => {
        return {
          name: planet.name,
          climate: planet.climate,
          terrain: planet.terrain,
          population: planet.population,
          image: this.getRandomPlanetImage(),
        };
      });
    } catch (error) {
      console.error(error);
    }
  }

  getRandomPlanetImage() {
    const imageNumber = Math.floor(Math.random() * 10) + 1;
    return `https://starwars-visualguide.com/assets/img/planets/${imageNumber}.jpg`;
  }
}
