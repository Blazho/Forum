import { Component } from '@angular/core';
import {appImports} from './component-imports';

@Component({
  selector: 'app-root',
  imports: appImports,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'forum-client';
}
