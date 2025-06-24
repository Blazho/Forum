import { Routes } from '@angular/router';
import {HomeComponent} from '../body-components/home/home.component';
import { RegisterComponent } from '../body-components/register/register.component';
import { LogInComponent } from '../body-components/log-in/log-in.component';
import { PostBodyComponent } from '../body-components/post-body/post-body.component';
import { ThreadBodyComponent } from '../body-components/thread-body/thread-body.component';
import { ThreadCreationBodyComponent } from '../body-components/thread-creation-body/thread-creation-body.component';
import { PostCreationBodyComponent } from '../body-components/post-creation-body/post-creation-body.component';

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
      { path: 'edit/:threadId', component: ThreadCreationBodyComponent},
      { path: 'create', component: ThreadCreationBodyComponent},
      { path: ':threadId', component: ThreadBodyComponent},
      { path: '', component: ThreadBodyComponent},
    ]
  },
  {
    path: "threads/posts",
    children: [
      { path: ':threadId/edit/:postId', component: PostCreationBodyComponent },
      { path: ':threadId/add', component: PostCreationBodyComponent },
      { path: ':threadId', component: PostBodyComponent }
    ]
  },
  {
    path: "",
    component: HomeComponent
  }
];
