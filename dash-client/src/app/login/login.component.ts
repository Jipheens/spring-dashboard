import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/userservice';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private router: Router, private userService: UserService) {}

  login() {
    this.userService.authenticate(this.email, this.password).subscribe(
      (response) => {
        console.log('Login successful:', response);
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        console.error('Error during login:', error);
      }
    );
  }
}
