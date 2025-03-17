import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../services/auth.service';
import { RegisterRequest } from '../../../api-interfaces/requests/register.request';

@Component({
  selector: 'app-register',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  readonly authService = inject(AuthService);
  
  registerForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    rePassword: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    email: new FormControl('' ,[Validators.required, Validators.email]),
  })

  errorMessage = ''

  processRegistration(){
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
        console.log(registerResponse);
      },
      error: error => {
        console.log(error)
      }
    })
  }



}
