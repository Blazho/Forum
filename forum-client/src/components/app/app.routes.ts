import { Routes } from '@angular/router';
import {HomeComponent} from '../body-components/home/home.component';
import { RegisterComponent } from '../body-components/register/register.component';
import { LogInComponent } from '../body-components/log-in/log-in.component';
import { PostBodyComponent } from '../body-components/post-body/post-body.component';
import { ThreadBodyComponent } from '../body-components/thread-body/thread-body.component';
import { ThreadCreationBodyComponent } from '../body-components/thread-creation-body/thread-creation-body.component';

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
    path: "threads",
    children: [
      { path: '', component: ThreadBodyComponent},
      { path: 'create', component: ThreadCreationBodyComponent},
      { path: ':threadId', component: ThreadBodyComponent},
    ]
  },
  {
    path: "threads/posts/:threadId",
    component: PostBodyComponent
  },
  {
    path: "",
    component: HomeComponent
  }
];
