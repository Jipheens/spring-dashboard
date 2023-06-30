import { Component } from '@angular/core';
import { UserService } from '../services/userservice';
import { User } from '../models/user';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent {
  showProfile: boolean = false;
  userProfile: User | null = null;
  userEmail: string = '';

  constructor(private userService: UserService) {}

  toggleProfile() {
    this.showProfile = !this.showProfile;
    if (this.showProfile) {
      this.fetchUserProfile();
    }
  }

  fetchUserProfile() {
    this.userService.getUserProfile(this.userEmail).subscribe(
      (user: User) => {
        this.userProfile = user;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  logout() {}
}
