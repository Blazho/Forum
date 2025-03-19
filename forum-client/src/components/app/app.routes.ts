import { Routes } from '@angular/router';
import {HomeComponent} from '../body-components/home/home.component';
import { RegisterComponent } from '../body-components/register/register.component';
import { LogInComponent } from '../body-components/log-in/log-in.component';

export const routes: Routes = [
  {
    path: "home",
    component: HomeComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "login",
    component: LogInComponent
  },
  {
    path: "",
    component: HomeComponent
  }
];
