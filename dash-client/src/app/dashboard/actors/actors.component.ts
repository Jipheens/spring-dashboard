import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Actor {
  name: string;
  gender: string;
  birthYear: string;
  height: string;
  image: string;
}

@Component({
  selector: 'app-actors',
  templateUrl: './actors.component.html',
  styleUrls: ['./actors.component.css'],
})
export class ActorsComponent implements OnInit {
  actors: Actor[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getActors();
  }

  async getActors() {
    try {
      const response = await this.http
        .get<any>('https://swapi.dev/api/people')
        .toPromise();
      this.actors = response.results.slice(0, 12).map((actor: any) => {
        return {
          name: actor.name,
          gender: actor.gender,
          birthYear: actor.birth_year,
          height: actor.height,
          image: this.getRandomActorImage(),
        };
      });
    } catch (error) {
      console.error(error);
    }
  }

  getRandomActorImage() {
    const imageNumber = Math.floor(Math.random() * 10) + 1;
    return `https://starwars-visualguide.com/assets/img/characters/${imageNumber}.jpg`;
  }
}
