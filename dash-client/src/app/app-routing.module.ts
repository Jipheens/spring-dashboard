import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { FilmsComponent } from './dashboard/films/films.component';
import { PlanetsComponent } from './dashboard/planets/planets.component';
import { ActorsComponent } from './dashboard/actors/actors.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: 'films', component: FilmsComponent },
      { path: 'planets', component: PlanetsComponent },
      { path: 'actors', component: ActorsComponent },
      { path: '', redirectTo: 'films', pathMatch: 'full' },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
