import { Component, inject, OnInit } from '@angular/core';
import { PermissionService } from '../../../services/permission.service';
import { Pageable } from '../../../api-interfaces/dtos/pageable.dts';
import { PermissionDTO } from '../../../api-interfaces/dtos/permission.dto';
import { MatTableModule } from '@angular/material/table';
import { _VisuallyHiddenLoader } from "@angular/cdk/private";
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-permission-body',
  imports: [MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    MatCardModule,
    MatToolbarModule,
    MatTooltipModule,
    RouterLink
  ],
  templateUrl: './permission-body.component.html',
  styleUrl: './permission-body.component.css'
})
export class PermissionBodyComponent implements OnInit{

  private readonly permissionService = inject(PermissionService)
  private readonly router = inject(Router)
  private readonly activateRoute = inject(ActivatedRoute)

  isLoading = false
  permissionList: PermissionDTO[] = []
  pageable: Pageable = {
      pageNumber: 0,
      pageSize: 10,
      totalElements: 0
    }
  displayedColumns: string[] = ["Id", "Title", "Description", "Date_created", "Last_date_modified", "Created_by"]
    
  ngOnInit(): void {
    this.loadPermissions()
  }

  loadPermissions() {
    this.isLoading = true

    this.permissionService.listPermission(this.pageable.pageSize, this.pageable.pageNumber)
    .subscribe({
      next: pageResponse => {
        this.permissionList = pageResponse.content
        this.pageable.pageNumber = pageResponse.pageable.pageNumber
        this.pageable.pageSize = pageResponse.pageable.pageSize
        this.pageable.totalElements = pageResponse.totalElements

        this.isLoading = false
      },
      error: error => {
          console.log(error);
          this.isLoading = false;
      }
    })
  }

  changePage($event: PageEvent) {
    this.pageable.pageNumber = $event.pageIndex
    this.pageable.pageSize = $event.pageSize
    this.loadPermissions()
  }

  goToPermission(permissionId: number) {
    this.router.navigate(['edit', permissionId], {
      relativeTo: this.activateRoute
    })
  }
}
