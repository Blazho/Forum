import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../services/auth.service';
import { LogInRequest } from "../../../api-interfaces/requests/login.request";
import { Router } from '@angular/router';

@Component({
  selector: 'app-log-in',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule, MatProgressSpinnerModule],
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.css'
})
export class LogInComponent{

  private readonly authService = inject(AuthService)
  private readonly router = inject(Router)

  isLoading = false;

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  })

  errorMessage = ''
  debounceTime = 0;


  processLogin(){
    this.isLoading = true;
    const logInCredentials : LogInRequest = {
      username: this.loginForm.value.username || "",
      password: this.loginForm.value.password || "",
    }

    this.authService.login(logInCredentials)
    .subscribe(
      {
        next: token => {
          this.isLoading = false;
          this.router.navigate(["/home"])
        },
        error: error => {
          this.errorMessage = error.error.errorMessage
          this.isLoading = false
        }
      }
    )
  }

  resetError() {
    this.errorMessage = ''
  }

}