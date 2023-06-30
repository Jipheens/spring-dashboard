import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';
import { UserService } from '../services/userservice';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  user: User = new User();
  confirmPassword: string = '';

  constructor(private router: Router, private userService: UserService) {}

  signup() {
    if (this.user.password !== this.confirmPassword) {
      alert("Passwords don't match");
      return;
    }

    this.userService.createUser(this.user).subscribe(
      (createdUser: User) => {
        console.log('User created successfully:', createdUser);
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        console.error('Error creating user:', error);
      }
    );
  }
}
