import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router, RouterLink } from '@angular/router';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { AuthService } from '../../../services/auth.service';
import { Location } from '@angular/common';
import { MatIcon, MatIconModule } from "@angular/material/icon";
import { MatTooltip } from "@angular/material/tooltip";

@Component({
  selector: 'navigation-menu',
  imports: [MatToolbarModule, MatButtonModule, RouterLink, MatIcon, MatIconModule, MatTooltip],
  templateUrl: './navigation-menu.component.html',
  styleUrl: './navigation-menu.component.css'
})
export class NavigationMenuComponent {

  private readonly router = inject(Router)
  private readonly dialog = inject(MatDialog)
  private readonly authService = inject(AuthService)
  private readonly location = inject(Location)

  logOut(){
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        dialogContent: "Do you want to log out?"
      }
    }).afterClosed().subscribe({
      next: result => {
        if(result == "Yes"){
          this.authService.clearUserData()
          this.router.navigate(["/login"])
        }
      }
    })
  }

  authenticated(): boolean {
    if(this.authService.getAuthToken()){
      return true
    }else{
      return false
    }
  }

  getUsername(): string | null {
    return this.authService.getUsername();
  }

  goBack(){
    this.location.back()
  }

}
