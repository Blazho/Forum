import { Routes } from '@angular/router';
import {HomeComponent} from '../body-components/home/home.component';
import { RegisterComponent } from '../body-components/register/register.component';

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
    path: "",
    component: HomeComponent
  }
];
