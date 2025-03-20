import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../services/auth.service';
import { RegisterRequest } from '../../../api-interfaces/requests/register.request';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule, MatProgressSpinnerModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  readonly authService = inject(AuthService);
  readonly route = inject(Router)
  
  registerForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    rePassword: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    email: new FormControl('' ,[Validators.required, Validators.email]),
  })

  errorMessage = ''
  loaderActive = false;

  processRegistration(){
    this.loaderActive = true;
    if(this.registerForm.value.password != this.registerForm.value.rePassword){
      this.errorMessage = "Passwords do not match"
      return;
    }
    const requestObject: RegisterRequest = {
      username: this.registerForm.value.username || "",
      password: this.registerForm.value.password || "",
      firstName: this.registerForm.value.name || "",
      lastName: this.registerForm.value.surname || "",
      email: this.registerForm.value.email || "",
      role: null
  }

    this.authService.register(requestObject).subscribe({
      next: registerResponse => {
        this.route.navigate(["/login"])
        this.loaderActive = false;
      },
      error: error => {
        console.log(error)
        this.loaderActive = false;
      }
    })
  }



}
