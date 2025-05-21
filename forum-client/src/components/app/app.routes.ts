import { Routes } from '@angular/router';
import {HomeComponent} from '../body-components/home/home.component';
import { RegisterComponent } from '../body-components/register/register.component';
import { LogInComponent } from '../body-components/log-in/log-in.component';
import { PostBodyComponent } from '../body-components/post-body/post-body.component';

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
    path: "post", //todo change it to be part of thread/topic path
    component: PostBodyComponent
  },
  {
    path: "",
    component: HomeComponent
  }
];
