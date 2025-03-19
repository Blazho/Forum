import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router } from '@angular/router';

@Component({
  selector: 'navigation-menu',
  imports: [MatToolbarModule, MatButtonModule],
  templateUrl: './navigation-menu.component.html',
  styleUrl: './navigation-menu.component.css'
})
export class NavigationMenuComponent {

  private readonly router = inject(Router)

  logOut(){
    sessionStorage.clear()
    this.router.navigate(["/login"])
  }

  authenticated(): boolean {
    if(sessionStorage.getItem("authToken")){
      return true
    }else{
      return false
    }
  }

}
