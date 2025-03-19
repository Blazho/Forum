import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../services/auth.service';
import { LogInRequest } from "../../../api-interfaces/requests/login.request";

@Component({
  selector: 'app-log-in',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.css'
})
export class LogInComponent {

  readonly authService = inject(AuthService)

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  })

  errorMessage = ''

  processLogin(){
  
    const logInCredentials : LogInRequest = {
      username: this.loginForm.value.username || "",
      password: this.loginForm.value.password || "",
    }

    this.authService.login(logInCredentials).subscribe(
      {
        next: token => {
          sessionStorage.setItem("authToken", token)
        },
        error: _ => {
          this.errorMessage = 'Invalid username or password'
        }
      }
    )
  }

  resetError() {
    this.errorMessage = ''
  }
}
1